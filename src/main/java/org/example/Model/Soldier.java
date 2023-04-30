package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Soldier extends Unit {
    int attackPower;
    double defensePower;
    int speed;
    int range;
    int secondRange = 0;
    int precision;
    int delay;
    int health;
    int state = 1; // 0 --> defensive   1 --> standing  2 --> offensive
    boolean isCapableOfClimbing;
    boolean canThrowLadders;
    boolean canDigDitch;
    boolean visibility;
    boolean isArab = false;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        super(XCoordinate, YCoordinate, owner, unitType);
        owner.addSoldier(this);
        health = unitType.getHitPoint();
        speed = unitType.getSpeed();
        range = unitType.getRange();
        secondRange = unitType.getSecondRange();
        attackPower = unitType.getAttackPower();
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

    public String toString() {
        return getUnitType().getName() + ": hp: " + health + ", owner: " + getOwner().getOwner().getUsername() + ", x: " + getXCoordinate() + ", y: " + getYCoordinate();
    }
}