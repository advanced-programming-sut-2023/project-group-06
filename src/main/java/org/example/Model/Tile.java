package org.example.Model;

import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Towers;

import java.util.ArrayList;

public class Tile {
    //todo handle nonSoldierUnits...
    private TileStructure type;
    private Building building;
    private int xCoordinate;
    private int yCoordinate;
    private int height;
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Unit> nonSoldierUnits = new ArrayList<>();

    public Tile(TileStructure type, int xCoordinate, int yCoordinate) {
        this.type = type;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        if (!type.CanBeCrossed()) height = -2;
        else height = 0;
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

    public ArrayList<Unit> getUnits() {
        return nonSoldierUnits;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public void setType(TileStructure type) {
        this.type = type;
    }

    public void setBuilding(Building building) {
        this.building = building;
        if(building == null) return;
        if (building instanceof Towers) height = 3;
        else if (building.getBuildingType() == BuildingType.STAIR) height = 1;
        else if (building.getBuildingType() == BuildingType.WALL) height = 2;
        else if(building.getBuildingType() == BuildingType.BRIDGE) height = 0;
        else height = -2;
    }

    public void addSoldier(Soldier soldier) {
        this.soldiers.add(soldier);
    }

    public void addToNonSoldierUnits(Unit unit) {
        this.nonSoldierUnits.add(unit);
    }

    public void removeFromNonSoldierUnits(Unit unit) {
        this.nonSoldierUnits.remove(unit);
    }

    public void removeSoldier(Soldier soldier) {
        this.soldiers.remove(soldier);
    }

    public boolean existEnemyOnThisTile(Kingdom ourKingdom) {
        for (Soldier soldier : soldiers) {
            if (soldier.getOwner() != ourKingdom) return true;
        }
        return false;
    }
}
