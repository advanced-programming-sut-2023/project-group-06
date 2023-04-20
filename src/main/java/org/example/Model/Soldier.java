package org.example.Model;

public class Soldier extends Unit {
    int attackPower;
    double defensePower;
    double speed;
    int range;
    int secondRange = 0;
    int cost;
    int precision;
    int delay;
    int state;
    boolean isCapableOfClimbing;
    boolean canThrowLadders;
    boolean canDigDitch;
    boolean visibility;
    UnitType unitType;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, UnitType unitType) {
        super(XCoordinate, YCoordinate, owner);
        this.unitType = unitType;
        owner.addSoldier(this);
    }
}
