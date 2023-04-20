package org.example.Model;

public enum SoldierType {
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
