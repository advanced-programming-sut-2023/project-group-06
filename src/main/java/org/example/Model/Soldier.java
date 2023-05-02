package org.example.Model;

import org.example.Controller.GameControllers.GameController;

public class Soldier extends Unit {
    int attackPower;
    double defensePower;
    int range;
    int secondRange = 0;
    int precision;
    int delay;
    int state = 1; // 0 --> defensive   1 --> standing  2 --> offensive
    boolean isCapableOfClimbing;
    boolean canThrowLadders;
    boolean canDigDitch;
    boolean isSaidToPatrol = false;
    Tile patrolWishPlace1;
    Tile patrolWishPlace2;
    boolean isArab = false;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        super(XCoordinate, YCoordinate, owner, unitType);
        owner.addSoldier(this);
        range = unitType.getRange();
        secondRange = unitType.getSecondRange();
        attackPower = unitType.getAttackPower();
    }

    public void setSaidToPatrol(boolean saidToPatrol) {
        isSaidToPatrol = saidToPatrol;
    }

    public boolean isSaidToPatrol() {
        return isSaidToPatrol;
    }

    public void setPatrolWishPlace1(Tile patrolWishPlace1) {
        this.patrolWishPlace1 = patrolWishPlace1;
    }

    public void setPatrolWishPlace2(Tile patrolWishPlace2) {
        this.patrolWishPlace2 = patrolWishPlace2;
    }

    public Tile getPatrolWishPlace1() {
        return patrolWishPlace1;
    }

    public void switchPatrolPlaces(){
        Tile tile = patrolWishPlace1;
        patrolWishPlace1 = patrolWishPlace2;
        patrolWishPlace2 = tile;
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

    public int getAttackPower() {
        return attackPower;
    }

    public void setState(int state) {
        this.state = state;
    }
}