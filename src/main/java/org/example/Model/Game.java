package org.example.Model;

import java.util.ArrayList;

public class Game {
    private int mapWidth;
    private int mapHeight;
    private int numberOfPlayers;
    private ArrayList<User> players;
    private ArrayList<Kingdom> kingdoms;
    private ArrayList<TradeRequest> tradeRequests = new ArrayList<>();
    private int whoseTurn = 0;

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
        mapHeight = height;
        mapWidth = width;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                map[i][j] = new Tile(TileStructure.EARTH, i, j);
            }
        }
    }

    public void setMap(Tile[][] map, int mapWidth, int mapHeight) {
        this.map = map;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
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

    public void setNewTile(int x, int y, TileStructure type){
        map[x][y] = new Tile(type, x, y);
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
        for(TradeRequest tradeRequest : tradeRequests){
            if(tradeRequest.id == id)
                return tradeRequest;
        }
        return null;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void nextTurn(){
        //it can shift the members of kingdoms // increase the turn index instead
        //todo
    }

    public Kingdom currentPlayer(){
        return kingdoms.get(whoseTurn);
    }
}
