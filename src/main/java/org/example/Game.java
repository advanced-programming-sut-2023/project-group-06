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

    public Game(boolean isPublic, long startTime, int capacity, int id, Client admin) {
        this.isPublic = isPublic;
        this.startTime = startTime;
        this.capacity = capacity;
        this.id = id;
        this.admin = admin;
        players.add(admin);
    }

    boolean isGameStarted = false;

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

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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
        return root;
    }
}
