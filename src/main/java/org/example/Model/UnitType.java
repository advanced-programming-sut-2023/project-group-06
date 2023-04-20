package org.example.Model;

public enum UnitType {
    //king must be checked
    KING(12121, 13131, 1211, 13123, 13131, 123, 0, 123, 0, null, false, false, false,false,"king"),
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
    boolean isArab;
    String name;

    UnitType(int hitPoint, int attackPower, double defensePower, double speed, int range, int secondRange, int cost,
             double precision, int delay, WeaponType weapon, boolean canClimb, boolean canThrowLadders, boolean canDigDitch, boolean isArab, String name) {
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
        this.isArab = isArab;
        this.name = name;
    }

    public static UnitType getSoldierTypeByString(String soldierName) {
        UnitType type = null;
        for (UnitType ts : UnitType.values()) {
            if (ts.name.equals(soldierName)) type = ts;
        }
        return type;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public double getDefensePower() {
        return defensePower;
    }

    public double getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public int getSecondRange() {
        return secondRange;
    }

    public int getCost() {
        return cost;
    }

    public double getPrecision() {
        return precision;
    }

    public int getDelay() {
        return delay;
    }

    public WeaponType getWeapon() {
        return weapon;
    }

    public boolean isCanClimb() {
        return canClimb;
    }

    public boolean isCanThrowLadders() {
        return canThrowLadders;
    }

    public boolean isCanDigDitch() {
        return canDigDitch;
    }

    public boolean isArab() {
        return isArab;
    }

    public String getName() {
        return name;
    }
}
