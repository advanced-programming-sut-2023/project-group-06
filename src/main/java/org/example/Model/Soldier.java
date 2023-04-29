package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Soldier extends Unit {
    int attackPower;
    double defensePower;
    double speed;
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
        owner.addToPopulation(1);
        health = unitType.getHitPoint();
        speed = unitType.getSpeed();
        range = unitType.getRange();
        secondRange = unitType.getSecondRange();
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

    public double getSpeed() {
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
        return getUnitType().getName() + ": hp: " + health + ", owner: " + getOwner().getOwner().getUsername();
    }
}