package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Soldier extends Unit {
    int attackPower;
    double defensePower;
    int speed;
    int range;
    int secondRange = 0;
    int cost;
    int precision;
    int delay;
    int health;
    int state; // 0 --> defensive   1 --> standing  2 --> offensive
    boolean isCapableOfClimbing;
    boolean canThrowLadders;
    boolean canDigDitch;
    boolean visibility;
    boolean isArab = false;
    UnitType unitType;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        super(XCoordinate, YCoordinate, owner);
        this.unitType = unitType;
        owner.addSoldier(this);
        owner.addToPopulation(1);
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
}