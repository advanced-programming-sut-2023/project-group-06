package org.example.Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.Model.Client;
import org.example.View.ChatMenu;
import org.example.View.ProfileMenu;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Comparable<User>, Serializable {
    private String username = "";
    private String hashedPassword = "";
    private String nickname = "";
    private String email = "";
    private String hashedAnswerToQuestion = "";
    private int questionIndex = -1;
    private String slogan = "";
    private int highScore;
    private int numberOfLoginAttempts = 0;
    private long lastLoginAttemptTime;
    private String avatar;
    private Client client;
    private ArrayList<ChatRoom> chats = new ArrayList<>();

    public User(String username, String password, String nickname, String email, String slogan) throws IOException {
        this.username = username;
        this.hashedPassword = DigestUtils.sha256Hex(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        Data.addUser(this);
        /*this.avatar = new Image(ProfileMenu.class.getResource("/Images/avatar8.jpg").toString());*/
        this.avatar = "/Images/avatar8.jpg";
        sendToServer();
        chats.add(Data.getPublicRoom());
        Data.getPublicRoom().getUsers().add(this);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) throws IOException {
        this.hashedPassword = hashedPassword;
        sendToServer();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IOException {
        this.username = username;
        sendToServer();
    }

    public boolean isPasswordCorrect(String password) {
        return (hashedPassword.equals(DigestUtils.sha256Hex(password)));
    }

    public boolean changePassword(String newPassword, String oldPassword) throws IOException {
        if (isPasswordCorrect(oldPassword)) {
            hashedPassword = DigestUtils.sha256Hex(newPassword);
            sendToServer();
            return true;
        }
        return false;
    }

    public boolean changePasswordBySecurityQuestion(String answer, String newPassword) throws IOException {
        if (hashedAnswerToQuestion.equals(DigestUtils.sha256Hex(answer))) {
            hashedPassword = DigestUtils.sha256Hex(newPassword);
            sendToServer();
            return true;
        }
        return false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) throws IOException {
        this.nickname = nickname;
        sendToServer();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IOException {
        this.email = email;
        sendToServer();
    }

    public boolean isAnswerToQuestionCorrect(String answerToQuestion) {
        return hashedAnswerToQuestion.equals(DigestUtils.sha256Hex(answerToQuestion));
    }

    public String getHashedAnswerToQuestion() {
        return hashedAnswerToQuestion;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setAnswerToQuestion(String answerToQuestion) throws IOException {
        if (!this.hashedAnswerToQuestion.equals("")) return;
        this.hashedAnswerToQuestion = answerToQuestion;
        sendToServer();
    }

    public void setQuestionIndex(int questionIndex) {
        if (this.questionIndex != -1) return;
        this.questionIndex = questionIndex;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) throws IOException {
        this.slogan = slogan;
        sendToServer();
    }

    public int getNumberOfLoginAttempts() {
        return numberOfLoginAttempts;
    }

    public void setNumberOfLoginAttempts(int numberOfLoginAttempts) throws IOException {
        this.numberOfLoginAttempts = numberOfLoginAttempts;
        sendToServer();
    }

    public long getLastLoginAttemptTime() {
        return lastLoginAttemptTime;
    }

    public void setLastLoginAttemptTime(long lastLoginAttemptTime) throws IOException {
        this.lastLoginAttemptTime = lastLoginAttemptTime;
        sendToServer();
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) throws IOException {
        this.highScore = highScore;
        sendToServer();
    }

    public void addHighScore(int highScore) throws IOException {
        this.highScore += highScore;
        sendToServer();
    }

    public int getRank() {
        return Data.getUserRank(this);
    }

    public int compareTo(User user) {
        return this.highScore - user.highScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) throws IOException {
        this.avatar = avatar;
        sendToServer();
    }

    public void sendToServer() throws IOException {
        if(client == null) return;
        client.dataOutputStream.writeUTF(toGson(""));
    }

    public void sendToServer(String command) throws IOException {
        if (client == null) return;
        client.dataOutputStream.writeUTF(toGson(command));
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String toGson(String commandString) {
        JsonObject data = new JsonObject();
        JsonObject dataType = new JsonObject();
        dataType.addProperty("type", "user");
        data.add("dataType", dataType);
        JsonObject user = new JsonObject();
        user.addProperty("username", this.getUsername());
        user.addProperty("password", this.getHashedPassword());
        user.addProperty("nickname", this.getNickname());
        user.addProperty("email", this.getEmail());
        user.addProperty("slogan", this.getSlogan());
        user.addProperty("questionIndex", this.getQuestionIndex());
        user.addProperty("answerToQuestion", this.getHashedAnswerToQuestion());
        user.addProperty("highScore", this.getHighScore());
        user.addProperty("image", this.getAvatar());
        data.add("user", user);
        client.toggle = 1 - client.toggle;
        JsonObject toggle = new JsonObject();
        toggle.addProperty("toggle", client.toggle);
        data.add("isAlive", toggle);
        JsonObject command = new JsonObject();
        command.addProperty("command", commandString);
        String output = new Gson().toJson(data);
        return output;
    }

    public void inactivateClient() {
        client.isClientAlive = false;
    }

    public ArrayList<ChatRoom> getChats() {
        return chats;
    }

    public boolean hasPrivateChatWith(String name){
        for(ChatRoom chatRoom : chats){
            if(chatRoom.getChatType() == ChatType.PRIVATE && chatRoom.hasSomeOne(name)) return true;
        }
        return false;
    }

    public boolean haveRoom(String name) {
        for(ChatRoom chatRoom : this.getChats())
            if(chatRoom.getChatType() == ChatType.ROOM && Objects.equals(chatRoom.getName(), name))
                return true;
        return false;
    }
}
