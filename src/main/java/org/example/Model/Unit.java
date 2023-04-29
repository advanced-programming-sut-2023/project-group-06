package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Unit {
    private int XCoordinate;
    private int YCoordinate;
    private Kingdom owner;
    private UnitType unitType;
    private Tile wishPlace;
    boolean isKingSaidToMove = false;

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

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
        this.unitType = unitType;
        wishPlace = GameController.currentGame.getTileByCoordinates(YCoordinate,XCoordinate);
    }

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
    }

    public Kingdom getOwner() {
        return owner;
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
}