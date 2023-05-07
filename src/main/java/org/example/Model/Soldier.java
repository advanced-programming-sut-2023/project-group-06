package org.example.Model;

import org.example.Controller.GameControllers.GameController;
import org.example.Model.BuildingGroups.Building;

public class Soldier extends Unit {
    private int attackPower;
    private double defensePower;
    private int range;
    private int secondRange = 0;
    private int precision;
    private int delay;
    private int state = 1; // 0 --> defensive   1 --> standing  2 --> offensive
    private boolean isCapableOfClimbing;
    private boolean canThrowLadders;
    private boolean canDigDitch;
    private boolean hasOil = false;
    private boolean isFlammable = false;
    private boolean isArab = false;
    private boolean isSaidToPatrol = false;
    private Tile patrolWishPlace1;
    private Tile patrolWishPlace2;
    private int lastOiledTurn = -1;
    private Tile ditch;
    private Tile fill;
    private Building tunnel;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        super(XCoordinate, YCoordinate, owner, unitType);
        owner.addSoldier(this);
        range = unitType.getRange();
        secondRange = unitType.getSecondRange();
        attackPower = unitType.getAttackPower();
        isArab = unitType.isArab();
    }

    public Building getTunnel() {
        return tunnel;
    }

    public void setTunnel(Building tunnel) {
        this.tunnel = tunnel;
    }

    public Tile getFill() {
        return fill;
    }

    public void setFill(Tile fill) {
        this.fill = fill;
    }

    public Tile getDitch() {
        return ditch;
    }

    public void setDitch(Tile ditch) {
        this.ditch = ditch;
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

    public boolean isFlammable() {
        return isFlammable;
    }

    public void setFlammable(boolean flammable) {
        isFlammable = flammable;
        if (flammable) lastOiledTurn = GameController.currentGame.getNumberOfTurns();
    }

    public boolean isHasOil() {
        return hasOil;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }

    public int getLastOiledTurn() {
        return lastOiledTurn;
    }

    public String toString() {
        String output = getUnitType().getName() + ": hp: " + getHealth() + ", owner: " + getOwner().getOwner().getUsername() + ", x: " + getXCoordinate() + ", y: " + getYCoordinate();
        output += ", is it flammable? " + isFlammable + ", has it oil? " + hasOil;
        return output;
    }
}