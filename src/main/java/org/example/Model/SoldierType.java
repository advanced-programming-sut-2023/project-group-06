package org.example.Model;

public enum SoldierType {
    //king must be checked
    KING(12121, 13131, 1211, 13123, 13131, 123, 0, 123, 0, null, false, false, false),
    ;
    int hitPoint;
    int attackPower;
    double defensePower;
    double speed;
    int range;
    int secondRange;
    int cost;
    double precision;
    int delay;
    WeaponType weapon;
    boolean canClimb;
    boolean canThrowLadders;
    boolean canDigDitch;

    SoldierType(int hitPoint, int attackPower, double defensePower, double speed, int range, int secondRange, int cost,
                double precision, int delay, WeaponType weapon, boolean canClimb, boolean canThrowLadders, boolean canDigDitch) {
        this.hitPoint = hitPoint;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.speed = speed;
        this.range = range;
        this.secondRange = secondRange;
        this.cost = cost;
        this.precision = precision;
        this.delay = delay;
        this.weapon = weapon;
        this.canClimb = canClimb;
        this.canThrowLadders = canThrowLadders;
        this.canDigDitch = canDigDitch;
    }
}
