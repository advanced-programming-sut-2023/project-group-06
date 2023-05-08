package org.example.Model;

import org.example.Controller.GameControllers.GameController;
import org.example.Model.BuildingGroups.Building;

public class Unit {
    private int XCoordinate;
    private int YCoordinate;
    private Kingdom owner;
    private UnitType unitType;
    private Tile wishPlace;
    boolean isKingSaidToMove = false;
    private int cowStored;
    protected int speed;
    protected int health;
    protected boolean isAvailable = true;
    public boolean isKingSaidToMove() {
        return isKingSaidToMove;
    }

    public void setKingSaidToMove(boolean kingSaidToMove) {
        isKingSaidToMove = kingSaidToMove;
    }

    public Tile getWishPlace() {
        return wishPlace;
    }

    public void setWishPlace(Tile wishPlace) {
        this.wishPlace = wishPlace;
    }

    public int getCowStored() {
        return cowStored;
    }

    public void addToStored(int amount){
        this.cowStored += amount;
    }

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
        owner.addUnit(this);
        this.unitType = unitType;
        this.speed = unitType.getSpeed();
        this.health = unitType.getHitPoint();
        wishPlace = GameController.currentGame.getTileByCoordinates(YCoordinate,XCoordinate);
        if (unitType == UnitType.ENGINEER) owner.addEngineer(this);
    }

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
        owner.addUnit(this);
    }

    public Kingdom getOwner() {
        return owner;
    }

    public int getSpeed() {
        return speed;
    }

    public void addToSpeed(int amount){
        this.speed += amount;
    }

    public int getXCoordinate() {
        return XCoordinate;
    }

    public int getYCoordinate() {
        return YCoordinate;
    }

    public void setXCoordinate(int XCoordinate) {
        this.XCoordinate = XCoordinate;
    }

    public void setYCoordinate(int YCoordinate) {
        this.YCoordinate = YCoordinate;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public String cowToString() {
        return getUnitType().getName() + ": hp: " + ", owner: " + getOwner().getOwner().getUsername() + ", x: " + getXCoordinate() + ", y: " + getYCoordinate();
    }

    public int getHealth() {
        return health;
    }

    public void subHealth(int hit) {
        health -= hit;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String toString() {
        String output = getUnitType().getName() + ": hp: " + health + ", owner: "
                + getOwner().getOwner().getUsername() + ", x: " + getXCoordinate() + ", y: " + getYCoordinate();
        if (this.unitType == UnitType.ENGINEER) output += ", is available? " + isAvailable();
        return output;
    }
}
