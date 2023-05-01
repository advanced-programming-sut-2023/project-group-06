package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Unit {
    private int XCoordinate;
    private int YCoordinate;
    private Kingdom owner;
    private UnitType unitType;
    private Tile wishPlace;
    boolean isKingSaidToMove = false;
    private int cowStored;
    private int speed;
    private int health;

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
        health = unitType.getHitPoint();
        wishPlace = GameController.currentGame.getTileByCoordinates(YCoordinate,XCoordinate);
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

    public String toString() {
        return getUnitType().getName() + ": hp: " + health + ", owner: " + getOwner().getOwner().getUsername() + ", x: " + getXCoordinate() + ", y: " + getYCoordinate();
    }
}