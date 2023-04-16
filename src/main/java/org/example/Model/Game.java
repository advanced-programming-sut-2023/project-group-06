package org.example.Model;

import java.util.ArrayList;

public class Game {
    private int mapWidth;
    private int mapHeight;
    private int numberOfPlayers;
    private ArrayList<User> players;
    private ArrayList<Kingdom> kingdoms;
    private ArrayList<TradeRequest> tradeRequests = new ArrayList<>();
    Tile[][] map;

    public Tile getTileByCoordinates(int y, int x) {
        return map[y][x];
    }

    public Game(ArrayList<User> players) {
        this.players = players;
        this.numberOfPlayers = players.size();
        kingdoms = new ArrayList<>();
        for(User user : this.players){
            kingdoms.add(new Kingdom(user));
        }
    }

    public void initializeMap(int width, int height){
        map = new Tile[width][height];
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapWidth(int width) { mapWidth = width; }

    public void setMapHeight(int height) { mapHeight = height; }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public ArrayList<TradeRequest> getTradeRequests() {
        return tradeRequests;
    }

    public void addTradeRequest(TradeRequest tradeRequest) {
        this.tradeRequests.add(tradeRequest);
    }

    public void removeTradeRequest(TradeRequest tradeRequest){
        this.tradeRequests.remove(tradeRequest);
    }

    public TradeRequest getTradeRequestById(int id){
        return null;
        //todo
    }

    public Tile[][] getMap() {
        return map;
    }
}
