package org.example.Model;

import org.example.Controller.GameControllers.GameController;
import org.example.Model.BuildingGroups.Building;
import org.example.View.GameMenus.GameMenu;
import org.example.View.Graphics.SuperImage;

public class Unit {
    protected int XCoordinate;
    protected int YCoordinate;
    protected Kingdom owner;
    protected UnitType unitType;
    protected Tile wishPlace;
    protected boolean isKingSaidToMove = false;
    protected int cowStored;
    protected int speed;
    protected int health;
    protected boolean isAvailable = true;
    private boolean isFlammable = false;
    private int lastOiledTurn = -1;
    private int fireDamageEachTurn = 0;
    private SuperImage img;

    public SuperImage getImg() {
        return img;
    }

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

    public void addToStored(int amount) {
        this.cowStored += amount;
    }

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
        if (owner != null) owner.addUnit(this);
        this.unitType = unitType;
        this.speed = unitType.getSpeed();
        this.health = unitType.getHitPoint();
        if (GameController.currentGame != null)
            wishPlace = GameController.currentGame.getTileByCoordinates(YCoordinate, XCoordinate);
        if (unitType == UnitType.ENGINEER) owner.addEngineer(this);
        this.img = unitType.getImg();
    }

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
        wishPlace = GameController.currentGame.getTileByCoordinates(YCoordinate, XCoordinate);
        owner.addUnit(this);
    }

    public Kingdom getOwner() {
        return owner;
    }

    public int getSpeed() {
        return speed;
    }

    public void addToSpeed(int amount) {
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

    public boolean isFlammable() {
        return isFlammable;
    }

    public int getLastOiledTurn() {
        return lastOiledTurn;
    }

    public int getFireDamageEachTurn() {
        return fireDamageEachTurn;
    }

    public void addToFireDamageEachTurn(int fireDamageEachTurn) {
        this.fireDamageEachTurn += fireDamageEachTurn;
    }

    public void resetFireDamageEachTurn() {
        this.fireDamageEachTurn = 0;
    }

    public void setFlammable(boolean flammable) {
        isFlammable = flammable;
        if (flammable) lastOiledTurn = GameController.currentGame.getNumberOfTurns();
    }

    public String toString() {
        String output = getUnitType().getName() + ": hp: " + getHealth() + "\nowner: "
                + (getOwner() == null? "null" : getOwner().getOwner().getUsername()) + "\nx: " + getXCoordinate() + ", y: "
                + getYCoordinate() + "\nwishPlace: " + wishPlace + "\nfireDamageTaken: " + fireDamageEachTurn + "\nis available? "
                + isAvailable;
        return output;
    }
}
