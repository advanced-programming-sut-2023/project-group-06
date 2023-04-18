package org.example.Model;

import com.google.gson.*;
import org.example.View.Response;

import java.io.*;
import java.util.ArrayList;

public class Data {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static boolean stayLoggedIn = false;

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

    public static void sortUsersByHighScore() {
        //todo
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static int getUserRank(User user) {
        sortUsersByHighScore();
        return 0;
        //todo
    }

    /* saveDate:
    true: everything is ok
    false: error
    put address = "src/main/java/org/example/Model/data.json" in the method */
    public static boolean saveData(String address) {
        JsonObject root = new JsonObject();
        root.addProperty("stayLoggedIn", stayLoggedIn);
        if(stayLoggedIn) root.addProperty("currentUsername", currentUser.getUsername());
        else root.addProperty("currentUsername", "the null user");
        JsonArray usersObject = new JsonArray();
        for (User user : users) {
            JsonObject userObject = new JsonObject();
            userObject.addProperty("username", user.getUsername());
            userObject.addProperty("password", user.getPassword());
            userObject.addProperty("nickname", user.getNickname());
            userObject.addProperty("email", user.getEmail());
            userObject.addProperty("slogan", user.getSlogan());
            userObject.addProperty("questionIndex", user.getQuestionIndex());
            userObject.addProperty("answerToQuestion", user.getAnswerToQuestion());
            userObject.addProperty("highScore", user.getHighScore());
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
    put address = "src/main/java/org/example/Model/data.json" in the method */
    public static boolean loadData(String address) {
        File input = new File(address);
        try {
            JsonElement root = JsonParser.parseReader(new FileReader(input));
            JsonObject rootObject = root.getAsJsonObject();
            stayLoggedIn = rootObject.get("stayLoggedIn").getAsBoolean();
            JsonArray usersObject = rootObject.get("users").getAsJsonArray();
            for(JsonElement userElement : usersObject){
                String username = userElement.getAsJsonObject().get("username").getAsString();
                String password = userElement.getAsJsonObject().get("password").getAsString();
                String nickname = userElement.getAsJsonObject().get("nickname").getAsString();
                String email = userElement.getAsJsonObject().get("email").getAsString();
                String slogan = userElement.getAsJsonObject().get("slogan").getAsString();
                int questionIndex = userElement.getAsJsonObject().get("questionIndex").getAsInt();
                int highScore = userElement.getAsJsonObject().get("highScore").getAsInt();
                String answerToQuestion = userElement.getAsJsonObject().get("answerToQuestion").getAsString();
                User user = new User(username, password, nickname, email, slogan);
                user.setQuestionIndex(questionIndex);
                user.setHighScore(highScore);
                user.setAnswerToQuestion(answerToQuestion);
            }
            if(stayLoggedIn){
                String currentUsername = rootObject.get("currentUsername").getAsString();
                currentUser = getUserByName(currentUsername);
            }

            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
