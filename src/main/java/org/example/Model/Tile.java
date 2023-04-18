package org.example.Model;

import org.example.Model.BuildingGroups.Building;

import java.util.ArrayList;

public class Tile {
    private TileStructure type;
    private Building building;
    private int xCoordinate;
    private int yCoordinate;
    private ArrayList<Soldier> soldiers = new ArrayList<>();

    public Tile(TileStructure type, int xCoordinate, int yCoordinate) {
        this.type = type;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public TileStructure getType() {
        return type;
    }

    public Building getBuilding() {
        return building;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setType(TileStructure type) {
        this.type = type;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void addSoldier(Soldier soldier) {
        this.soldiers.add(soldier);
    }

    public void removeSoldier(Soldier soldier) {
        this.soldiers.remove(soldier);
    }
}
