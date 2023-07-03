package org.example.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class WaitingGame {
    private int id;
    private ArrayList<User> players;
    private int capacity;
    private User admin;
    private long startTime;
    private boolean isPublic = true;
    private boolean isGameStarted = false;
    private static int count = 0;
    private boolean isGameStarted = false;

    public WaitingGame(int capacity, User admin, boolean isPublic) {
        this.capacity = capacity;
        this.admin = admin;
        this.id = count;
        count++;
        this.isPublic = isPublic;
        players = new ArrayList<>();
        players.add(admin);
        startTime = System.currentTimeMillis() / 1000L;
    }

    public WaitingGame(int id, ArrayList<User> players, int capacity, User admin, long startTime, boolean isPublic, boolean isGameStarted) {
        this.id = id;
        this.players = players;
        this.capacity = capacity;
        this.admin = admin;
        this.startTime = startTime;
        this.isPublic = isPublic;
        count = id + 1;
        this.isGameStarted = isGameStarted;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public int getCapacity() {
        return capacity;
    }

    public User getAdmin() {
        return admin;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public JsonObject toJson(){
        JsonObject root = new JsonObject();
        JsonArray playersObj = new JsonArray();
        for (User player : players) {
            playersObj.add(player.getUsername());
        }
        root.add("players", playersObj);
        root.addProperty("id", id);
        root.addProperty("capacity", capacity);
        root.addProperty("admin", admin.getUsername());
        root.addProperty("start time", startTime);
        root.addProperty("is public", isPublic);
        return root;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean isGameStarted) {
        this.isGameStarted = isGameStarted;
    }
}
