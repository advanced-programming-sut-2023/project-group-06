package org.example.Model;

import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.Tree;

import java.util.ArrayList;

public class Game {
    private int mapWidth;
    private int mapHeight;
    private int numberOfPlayers;
    private ArrayList<User> players;
    private ArrayList<Kingdom> kingdoms = new ArrayList<>();
    private ArrayList<TradeRequest> tradeRequests = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Tile> ditches = new ArrayList<>();
    private int turnIndex = 0;
    private int numberOfTurns = 1;

    Tile[][] map;

    public Tile getTileByCoordinates(int y, int x) {
        return map[y][x];
    }

    public Game(ArrayList<User> players) {
        this.players = players;
        this.numberOfPlayers = players.size();
        for(User user : this.players){
            kingdoms.add(new Kingdom(user));
        }
    }

    public void initializeMap(int width, int height){
        map = new Tile[height][width];
        mapHeight = height;
        mapWidth = width;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                map[j][i] = new Tile(TileStructure.EARTH, i, j);
            }
        }
    }

    public void setMap(Tile[][] map, int mapWidth, int mapHeight) {
        this.map = map;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
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

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void cutTree(){
        Tree tree = trees.get(0);
        this.getTileByCoordinates(tree.getYCoordinate(), tree.getXCoordinate()).setBuilding(null);
        trees.remove(0);
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
            if(tradeRequest.getId() == id)
                return tradeRequest;
        }
        return null;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void nextTurn(){
        this.turnIndex++;
        this.turnIndex %= numberOfPlayers;
        this.numberOfTurns++;
    }

    public Kingdom currentPlayer(){
        return kingdoms.get(turnIndex);
    }

    public int getTurnIndex() {
        return turnIndex;
    }
    public int getATradeRequestId() {
        int size = tradeRequests.size();
        return size + 1;
    }

    public Kingdom getKingdomByUsername(String username) {
        for (Kingdom kingdom : kingdoms) {
            if (kingdom.getOwner().getUsername().equals(username)) return kingdom;
        }
        return null;
    }

    public void removeKingdom(Kingdom kingdom){
        this.kingdoms.remove(kingdom);
        this.numberOfPlayers--;
    }

    public ArrayList<Tile> getDitches() {
        return ditches;
    }
}
