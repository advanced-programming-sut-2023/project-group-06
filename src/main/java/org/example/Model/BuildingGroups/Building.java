package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;

public class Building {
    private int delay = 0;
    private int hitPoint;
    private int goldPrice;
    private Kingdom owner;
    private Resources resourcesPrice;
    private int workerPrice;
    private int engineerPrice;
    private int happinessIncrease;
    private BuildingType buildingType;
    private int direction;

    public Building(Kingdom owner, BuildingType buildingType){
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

    public int getGoldPrice() {
        return goldPrice;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public void setOwner(Kingdom owner) {
        this.owner = owner;
    }

    public Resources getResourcesPrice() {
        return resourcesPrice;
    }

    public int getWorkerPrice() {
        return workerPrice;
    }

    public int getEngineerPrice() {
        return engineerPrice;
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
}