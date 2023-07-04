package org.example;

import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Client implements Comparable<Client> {
    private boolean isOnline = true;
    private long lastSeen = 0;
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
    private ArrayList<String> notDeliveredFriendRequestsSentByMe = new ArrayList<>();
    private ArrayList<String> notRespondedFriendRequestsReceivedByMe = new ArrayList<>();
    private ArrayList<Client> friends = new ArrayList<>();
    private ChatRoom gameRoom = null;
    private ArrayList<String> mapNames = new ArrayList<>();

    public Client(String username, String password, String nickname, String email, String slogan) throws IOException {
        this.username = username;
        this.hashedPassword = DigestUtils.sha256Hex(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        /*this.avatar = new Image(ProfileMenu.class.getResource("/Images/avatar8.jpg").toString());*/
        this.avatar = "/Images/avatar8.jpg";
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordCorrect(String password) {
        return (hashedPassword.equals(DigestUtils.sha256Hex(password)));
    }

    public boolean changePassword(String newPassword, String oldPassword) {
        if (isPasswordCorrect(oldPassword)) {
            hashedPassword = DigestUtils.sha256Hex(newPassword);
            return true;
        }
        return false;
    }

    public boolean changePasswordBySecurityQuestion(String answer, String newPassword) {
        if (hashedAnswerToQuestion.equals(DigestUtils.sha256Hex(answer))) {
            hashedPassword = DigestUtils.sha256Hex(newPassword);
            return true;
        }
        return false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setAnswerToQuestion(String answerToQuestion) {
        this.hashedAnswerToQuestion = answerToQuestion;
    }

    public void setQuestionIndex(int questionIndex) {
        if (this.questionIndex != -1) return;
        this.questionIndex = questionIndex;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getNumberOfLoginAttempts() {
        return numberOfLoginAttempts;
    }

    public void setNumberOfLoginAttempts(int numberOfLoginAttempts) {
        this.numberOfLoginAttempts = numberOfLoginAttempts;
    }

    public long getLastLoginAttemptTime() {
        return lastLoginAttemptTime;
    }

    public void setLastLoginAttemptTime(long lastLoginAttemptTime) {
        this.lastLoginAttemptTime = lastLoginAttemptTime;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void addHighScore(int highScore){
        this.highScore += highScore;
    }


    public int compareTo(Client client) {
        return this.highScore - client.highScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String toString() {
        return "username: " + username + "\n\t" +
        "password: " + this.getHashedPassword() + "\n\t" +
        "nickname: " +  this.getNickname() + "\n\t" +
        "email: " + this.getEmail() + "\n\t" +
        "slogan: " + this.getSlogan() + "\n\t" +
        "questionIndex: " + this.getQuestionIndex() + "\n\t" +
        "answerToQuestion: " + this.getHashedAnswerToQuestion() + "\n\t" +
        "highScore: " + this.getHighScore() + "\n\t" +
        "image: " + this.getAvatar() + '\n';
    }

    public void override(Client client) {
        this.username = client.getUsername();
        this.hashedPassword = client.hashedPassword;
        this.avatar = client.avatar;
        this.email = client.email;
        this.nickname = client.nickname;
        this.slogan = client.slogan;
        this.highScore = client.highScore;
        this.hashedAnswerToQuestion = client.hashedAnswerToQuestion;
        this.questionIndex = client.questionIndex;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public ArrayList<String> getNotDeliveredFriendRequestsSentByMe() {
        return notDeliveredFriendRequestsSentByMe;
    }

    public synchronized void addToNotDeliveredFriendRequestsSentByMe(String notDeliveredFriendRequestSentByMe) {
        this.notDeliveredFriendRequestsSentByMe.add(notDeliveredFriendRequestSentByMe);
    }

    public synchronized void removeFromNotDeliveredFriendRequestsSentByMe(int index) {
        this.notDeliveredFriendRequestsSentByMe.remove(index);
    }

    public ArrayList<String> getNotRespondedFriendRequestsReceivedByMe() {
        return notRespondedFriendRequestsReceivedByMe;
    }

    public synchronized void addToNotRespondedFriendRequestsReceivedByMe(String notRespondedFriendRequestReceivedByMe) {
        this.notRespondedFriendRequestsReceivedByMe.add(notRespondedFriendRequestReceivedByMe);
    }

    public synchronized void removeFromNotRespondedFriendRequestsReceivedByMe(int index) {
        this.notRespondedFriendRequestsReceivedByMe.remove(index);
    }

    public ArrayList<Client> getFriends() {
        return friends;
    }

    public synchronized void addFriend(Client friend) {
        this.friends.add(friend);
    }

    public ChatRoom getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(ChatRoom gameRoom) {
        this.gameRoom.getUsernames().remove(this.getUsername());
        if (gameRoom != null) gameRoom.getUsernames().add(this.getUsername());
        this.gameRoom = gameRoom;
    }

    public JsonObject toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("is online", isOnline);
        root.addProperty("last seen", lastSeen);
        root.addProperty("username", username);
        return root;
    }

    public ArrayList<String> getMapNames() {
        return mapNames;
    }

    public synchronized void addMapName(String mapName) {
        synchronized (mapNames) {
            this.mapNames.add(mapName);
        }
    }
}

