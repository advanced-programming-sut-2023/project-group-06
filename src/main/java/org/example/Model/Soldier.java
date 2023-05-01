package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Soldier extends Unit {
    private int attackPower;
    private double defensePower;
    private int speed;
    private int range;
    private int secondRange = 0;
    private int precision;
    private int delay;
    private int health;
    private int state = 1; // 0 --> defensive   1 --> standing  2 --> offensive
    private boolean isCapableOfClimbing;
    private boolean canThrowLadders;
    private boolean canDigDitch;
    private boolean visibility;
    private boolean hasOil = false;
    private boolean isFlammable = false;
    private boolean isArab = false;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        super(XCoordinate, YCoordinate, owner, unitType);
        owner.addSoldier(this);
        health = unitType.getHitPoint();
        speed = unitType.getSpeed();
        range = unitType.getRange();
        secondRange = unitType.getSecondRange();
        attackPower = unitType.getAttackPower();
        isArab = unitType.isArab();
    }

    public int getState() {
        return state;
    }

    public boolean isArab() {
        return isArab;
    }

    public int getRange() {
        return range;
    }

    public int getSecondRange() {
        return secondRange;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHealth() {
        return health;
    }

    public void subHealth(int hit) {
        health -= hit;
    }

    public boolean isFlammable() {
        return isFlammable;
    }

    public void setFlammable(boolean flammable) {
        isFlammable = flammable;
    }

    public boolean isHasOil() {
        return hasOil;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }

    public String toString() {
        return getUnitType().getName() + ": hp: " + health + ", owner: " + getOwner().getOwner().getUsername() + ", x: " + getXCoordinate() + ", y: " + getYCoordinate();
    }
}