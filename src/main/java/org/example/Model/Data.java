package org.example.Model;

import com.google.gson.*;
import org.example.Model.BuildingGroups.Tree;
import org.example.Model.BuildingGroups.TreeType;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Data {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static boolean stayLoggedIn = false;
    private static int numberOfMessages;
    private static ChatRoom publicRoom;
    public static boolean flag = false;

    public static void initializePublicRoom() throws IOException {
        publicRoom = new ChatRoom(users, ChatType.PUBLIC, 0);
    }

    public static User getUserByName(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) return users.get(i);
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) return users.get(i);
        }
        return null;
    }

    public static ChatRoom getPublicRoom(){
        return publicRoom;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean stayLoggedIn) {
        Data.stayLoggedIn = stayLoggedIn;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static void sortUsersByHighScore() {
        Collections.sort(users);
    }

    public static int getUserRank(User user) {
        sortUsersByHighScore();
        for (int i = 1; i <= users.size(); i++) {
            if (users.get(users.size() - i) == user) return i;
        }
        return -1;
    }

    public static int getNumberOfMessages(){
        return numberOfMessages;
    }

    public static void updateNumberOfMessages(){
        numberOfMessages++;
    }

    /* saveDate:
    true: everything is ok
    false: error
    put address = "src/main/java/org/example/Model/Data.json" in the method */
    public static boolean saveData(String address) {
        JsonObject root = new JsonObject();
        root.addProperty("stayLoggedIn", stayLoggedIn);
        if (stayLoggedIn) root.addProperty("currentUsername", currentUser.getUsername());
        else root.addProperty("currentUsername", "the null user");
        JsonArray usersObject = new JsonArray();
        for (User user : users) {
            JsonObject userObject = new JsonObject();
            userObject.addProperty("username", user.getUsername());
            userObject.addProperty("password", user.getHashedPassword());
            userObject.addProperty("nickname", user.getNickname());
            userObject.addProperty("email", user.getEmail());
            userObject.addProperty("slogan", user.getSlogan());
            userObject.addProperty("questionIndex", user.getQuestionIndex());
            userObject.addProperty("answerToQuestion", user.getHashedAnswerToQuestion());
            userObject.addProperty("highScore", user.getHighScore());
            userObject.addProperty("image", user.getAvatar());
            usersObject.add(userObject);
        }
        root.add("users", usersObject);
        Gson gson = new Gson();
        String json = gson.toJson(root);
        try {
            FileWriter writer = new FileWriter(address);
            writer.write(json);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* loadDate:
    true : everything is ok
    false: error
    put address = "src/main/java/org/example/Model/Data.json" in the method */
    public static boolean loadData(String address) {
        users = new ArrayList<>();
        File input = new File(address);
        try {
            JsonElement root = JsonParser.parseReader(new FileReader(input));
            JsonObject rootObject = root.getAsJsonObject();
            stayLoggedIn = rootObject.get("stayLoggedIn").getAsBoolean();
            JsonArray usersObject = rootObject.get("users").getAsJsonArray();
            for (JsonElement userElement : usersObject) {
                String username = userElement.getAsJsonObject().get("username").getAsString();
                String password = userElement.getAsJsonObject().get("password").getAsString();
                String nickname = userElement.getAsJsonObject().get("nickname").getAsString();
                String email = userElement.getAsJsonObject().get("email").getAsString();
                String slogan = userElement.getAsJsonObject().get("slogan").getAsString();
                int questionIndex = userElement.getAsJsonObject().get("questionIndex").getAsInt();
                int highScore = userElement.getAsJsonObject().get("highScore").getAsInt();
                String answerToQuestion = userElement.getAsJsonObject().get("answerToQuestion").getAsString();
                String image = userElement.getAsJsonObject().get("image").getAsString();
                User user = new User(username, "", nickname, email, slogan);
                user.setHashedPassword(password);
                user.setQuestionIndex(questionIndex);
                user.setHighScore(highScore);
                user.setAnswerToQuestion(answerToQuestion);
//                user.setAvatar(image);
            }
            if (stayLoggedIn) {
                String currentUsername = rootObject.get("currentUsername").getAsString();
                currentUser = getUserByName(currentUsername);
            }
            if(!flag) initializePublicRoom();
            flag = true;
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* saveMap
    true: everything is ok
    false: error
    map will be saved in "src/main/java/org/example/Model/Maps/[fileName].bin" */
    public static boolean saveMap(String fileName, Tile[][] map) {
        int height = map.length;
        int width = map[0].length;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/org/example/Model/Maps/" + fileName + ".bin");
            fileOutputStream.write(height / 256);
            fileOutputStream.write(height % 256);
            fileOutputStream.write(width / 256);
            fileOutputStream.write(width % 256);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int b = 0;
                    if (map[i][j].getBuilding() != null && map[i][j].getBuilding() instanceof Tree)
                        b = 1 + ((Tree) map[i][j].getBuilding()).getTreeType().ordinal();
                    fileOutputStream.write(b * 16 + map[i][j].getType().ordinal());
                }
            }
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* loadMap
    null: error
    map will be loaded from "src/main/java/org/example/Model/Maps/[fileName].bin" */
    public static Tile[][] loadMap(String fileName) {
        String path = "src/main/java/org/example/Model/Maps/" + fileName + ".bin";
        File f = new File(path);
        if (!f.exists() || f.isDirectory()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            int height = fileInputStream.read() * 256 + fileInputStream.read();
            int width = fileInputStream.read() * 256 + fileInputStream.read();
            Tile[][] map = new Tile[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int b = fileInputStream.read();
                    int building = b / 16;
                    int type = b % 16;
                    map[i][j] = new Tile(TileStructure.values()[type], j, i);
                    if (building > 0)
                        map[i][j].setBuilding(new Tree(j, i, TreeType.values()[building - 1]));
                }
            }
            fileInputStream.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
