package org.example.Model.BuildingGroups;

import org.example.Controller.GameControllers.GameController;
import org.example.Model.EquipmentType;
import org.example.Model.Kingdom;
import org.example.Model.Resources;
import org.example.Model.Tile;

public class Building {
    private int xCoordinate;
    private int yCoordinate;
    private int delay = 0;
    private int hitPoint;
    private Kingdom owner;
    private int happinessIncrease;
    private BuildingType buildingType;
    private int direction;
    private boolean isFlammable = false;
    private int lastOiledTurn = -1;
    private boolean isOnFire = false;
    private EquipmentType equipmentType = null;
    public Building(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.owner = owner;
        this.buildingType = buildingType;
        this.hitPoint = buildingType.getHitPoint();
    }

    public Building(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate, int direction){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.owner = owner;
        this.buildingType = buildingType;
        this.direction = direction;
        this.hitPoint = buildingType.getHitPoint();
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void subDelay(){
        this.delay -= 1;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void addHitPoint(int hitPoint) {
        this.hitPoint += hitPoint;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public void setOwner(Kingdom owner) {
        this.owner = owner;
    }

    public int getHappinessIncrease() {
        return happinessIncrease;
    }

    public void addHappinessIncrease(int happinessIncrease) {
        this.happinessIncrease += happinessIncrease;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getDirection() {
        return direction;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public boolean isFlammable() {
        return isFlammable;
    }

    public void setFlammable(boolean flammable) {
        isFlammable = flammable;
        if (flammable) lastOiledTurn = GameController.currentGame.getNumberOfTurns();
    }

    public int getLastOiledTurn() {
        return lastOiledTurn;
    }

    public String toString() {
        String output = this.getBuildingType().getName();
        output += ", x: " + xCoordinate + ", y: " + yCoordinate + ", type: " + getBuildingType().getName() + ", hp: " + hitPoint;
        output += ", is it flammable? " + isFlammable;
        return output;
    }
}