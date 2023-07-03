package org.example.Model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;

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
    private ArrayList<FriendRequest> friendRequestsSentByMe = new ArrayList<>();
    private ArrayList<FriendRequest> friendRequestsReceivedByMe = new ArrayList<>();
    private ArrayList<User> myFriends = new ArrayList<>();
    private ArrayList<String> allClients = new ArrayList<>();
    private ArrayList<WaitingGame> allWaitingGames = new ArrayList<>();
    private boolean isOnline = false;
    private long lastSeen = 0;

    public User(String username, String password, String nickname, String email, String slogan) throws IOException {
        this.username = username;
        this.hashedPassword = DigestUtils.sha256Hex(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        Data.addUser(this);
        /*this.avatar = new Image(ProfileMenu.class.getResource("/Images/avatar8.jpg").toString());*/
        this.avatar = User.class.getResource("/Images/avatar8.jpg").toExternalForm();
//        sendToServer();
        if(Data.flag) {
            chats.add(Data.getPublicRoom());
            Data.getPublicRoom().getUsers().add(this);
        }
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
        if (client == null) return;
        client.dataOutputStream.writeUTF(toGson("", null));
    }

    public void sendToServer(String commandType, JsonObject context) throws IOException {
        System.out.println("client:  " + client + "  " + " command:  " + commandType + "  user:  " + username + " context:  " + context);
        if (client == null) return;
        client.dataOutputStream.writeUTF(toGson(commandType, context));
        System.out.println(commandType + " (sendToServer) " + context);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (client != null) {
            UserThread userThread = new UserThread(this);
            userThread.setDaemon(true);
            userThread.start();
        }
    }

    public String toGson(String commandType, JsonObject context) {
        JsonObject data = new JsonObject();
        JsonObject user = toJson();
        data.add("user", user);
        JsonObject time = new JsonObject();
        time.addProperty("time", (client.isClientActive) ? System.currentTimeMillis() / 1000L : -1L);
        data.add("isAlive", time);
        JsonObject command = new JsonObject();
        command.addProperty("command type", commandType);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "null");
        data.add("command content", (context != null) ? context : jsonObject);
        data.add("command type", command);
        String output = new Gson().toJson(data);
        return output;
    }

    public JsonObject toJson() {
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
        return user;
    }

    public void sendMessageCommand(Message message) throws IOException {
        sendToServer("send message", message.toJson());
    }

    public void deleteMessageCommand(Message message) throws IOException {
        sendToServer("delete message", message.toJson());
    }

    public void editMessage(Message message) throws IOException {
        sendToServer("edit message", message.toJson());
    }

    public void createRoom(ChatRoom chatRoom) throws IOException {
        System.out.println(chatRoom.toJson() + " ppppppp");
        sendToServer("create room", chatRoom.toJson());
    }

    public void addToGroup(ChatRoom chatRoom) throws IOException {
        System.out.println("added To Groupppp");
        sendToServer("add to group", chatRoom.toJson());
    }

    public void inactivateClient() throws IOException {
        sendToServer("inactivate", null);
        client.isClientActive = false;
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

    public ChatRoom getChatWithId(int id) {
        for (ChatRoom chat : chats) {
            if (chat.getId() == id) return chat;
        }
        return null;
    }

    public ArrayList<FriendRequest> getFriendRequestsSentByMe() {
        return friendRequestsSentByMe;
    }

    public ArrayList<FriendRequest> getFriendRequestsReceivedByMe() {
        return friendRequestsReceivedByMe;
    }

    public ArrayList<User> getMyFriends() {
        return myFriends;
    }

    public void acceptRequestCommand(FriendRequest friendRequest) throws IOException {
        sendToServer("accept request", friendRequest.toJson());
    }

    public void rejectRequestCommand(FriendRequest friendRequest) throws IOException {
        sendToServer("reject request", friendRequest.toJson());
    }

    public void sendRequestCommand(FriendRequest friendRequest) throws IOException {
        sendToServer("send request", friendRequest.toJson());
    }

    public boolean isOnline(User user){
        return user.isOnline();
    }

    public long getLastSeen(User user) {
        return user.getLastSeen();
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

    public void setMyFriends(ArrayList<User> myFriends) {
        this.myFriends = myFriends;
    }

    public void setFriendRequestsReceivedByMe(ArrayList<FriendRequest> friendRequests) {
        this.friendRequestsReceivedByMe = friendRequests;
    }

    public ArrayList<String> getAllClients() {
        return allClients;
    }

    public void setAllClients(ArrayList<String> allClients) {
        this.allClients = allClients;
    }

    public ArrayList<WaitingGame> getAllWaitingGames() {
        return allWaitingGames;
    }

    public void setAllWaitingGames(ArrayList<WaitingGame> allWaitingGames) {
        this.allWaitingGames = allWaitingGames;
    }

    public void createWaitingGameCommand(WaitingGame waitingGame) throws IOException {
        sendToServer("create waiting game", waitingGame.toJson());
    }

    public void joinCommand(WaitingGame waitingGame) throws IOException {
        sendToServer("join waiting room", waitingGame.toJson());
    }

    public void deleteWaitingRoomCommand(WaitingGame waitingGame) throws IOException {
        sendToServer("delete waiting room", waitingGame.toJson());
    }

    public WaitingGame getWaitingRoomById(int id){
        for (WaitingGame waitingGame : allWaitingGames) {
            if(waitingGame.getId() == id)
                return waitingGame;
        }
        return null;
    }
}
