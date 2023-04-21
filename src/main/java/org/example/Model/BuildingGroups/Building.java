package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;

public class Building {
    private int xCoordinate;
    private int yCoordinate;
    private int delay = 0;
    private int hitPoint;
    private Kingdom owner;
    private int happinessIncrease;
    private BuildingType buildingType;
    private int direction;

    public Building(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.owner = owner;
        this.buildingType = buildingType;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void addDelay(int delay){
        this.delay += delay;
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


}