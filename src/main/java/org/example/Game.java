package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Game {
    boolean isPublic = true;
    long startTime;
    int capacity;
    ArrayList<Client> players = new ArrayList<>();
    int id;
    Client admin;
    boolean isGameStarted = false;
    ChatRoom chatRoom;
    private static int count = 0;

    public Game(boolean isPublic, long startTime, int capacity, int id, Client admin) {
        this.isPublic = isPublic;
        this.startTime = startTime;
        this.capacity = capacity;
        this.id = id;
        this.admin = admin;
        ArrayList<String> usernames = new ArrayList<>(); usernames.add(admin.getUsername());
        chatRoom = new ChatRoom(usernames, ChatType.GAME, "null", id, 0);
        players.add(admin);
    }
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Client> getPlayers() {
        return players;
    }

    public synchronized void addPlayer(Client player) {
        this.players.add(player);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getAdmin() {
        return admin;
    }

    public void setAdmin(Client admin) {
        this.admin = admin;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public JsonObject toJson() {
        JsonObject root = new JsonObject();
        JsonArray playersObj = new JsonArray();
        for (Client player : players) {
            playersObj.add(player.getUsername());
        }
        root.addProperty("id", id);
        root.add("players", playersObj);
        root.addProperty("is public", isPublic);
        root.addProperty("start time", startTime);
        root.addProperty("capacity", capacity);
        root.addProperty("admin", admin.getUsername());
        root.addProperty("is game started", isGameStarted);
        JsonArray container = new JsonArray();
        container.add(chatRoom.toJson());
        root.add("game room", container);
        return root;
    }

    public synchronized void removePlayer(Client player) {
        players.remove(player);
    }
}
