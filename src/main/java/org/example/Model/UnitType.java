package org.example.Model;

import org.example.View.Graphics.SuperImage;

public enum UnitType {
    KING(SuperImage.KING, 800, 160, 0.2, 3, 1, 0, 0, 0, 0, null, null, false, false, false,"king", false),
    ARCHER(SuperImage.ARCHER, 200,100,0.15,4,7,3,12,0.3,0,WeaponType.BOW, null,false,false,false,"archer", true),
    CROSSBOW_MAN(SuperImage.CROSSBOW_MAN, 250, 120, 0.2, 3, 4, 0, 20, 0.1, 0, WeaponType.CROSSBOW, WeaponType.LEATHER_ARMOR, false, false, false, "crossbow man", true),
    SPEAR_MAN(SuperImage.SPEAR_MAN, 150, 180, 0.1, 3 ,1, 0, 8, 0.1, 2, WeaponType.SPEAR, null, true, true, false, "spear man", false),
    PIKE_MAN(SuperImage.PIKE_MAN, 500, 200, 0.3, 2, 1, 0, 20, 0, 0, WeaponType.PIKE, WeaponType.METAL_ARMOR, false, false, false, "pike man", false),
    MACE_MAN(SuperImage.MACE_MAN, 350, 300, 0.2, 3, 1, 0, 20, 0, 0, WeaponType.MACE, WeaponType.LEATHER_ARMOR, true, false, false, "mace man", false),
    SWORDS_MAN(SuperImage.SWORDS_MAN, 250, 350, 0.27, 1, 1, 0, 40, 0, 3, WeaponType.SWORDS, WeaponType.METAL_ARMOR, false, true, false, "swordsman", false),
    KNIGHT(SuperImage.KNIGHT, 600, 400, 0.35, 4, 1, 0, 120, 0, 0, WeaponType.SWORDS, WeaponType.METAL_ARMOR, false, false, false, "knight", false),
    TUNNELER(SuperImage.TUNNELER, 100, 100, 0.1, 3, 1, 0, 30, 0, 2, null, null, false, true, false, "tunneler", false),
    LADDER_MAN(SuperImage.LADDER_MAN, 80, 0, 0.05, 3, 1, 0, 6, 0, 2, null, null, true, false, false, "ladder man", false),
    ENGINEER(SuperImage.ENGINEER, 80, 0, 0.05, 3, 0, 0, 30, 0, 3, null, null, false, true, false, "engineer", false),
    OIL_ENGINEER(SuperImage.OIL_ENGINEER, 80, 100, 0.05, 2, 1, 0, 0, 0, 0, WeaponType.OIL, null, false, false, false, "oil engineer", false),
    BLACK_MONK(SuperImage.BLACK_MONK, 200, 100, 0.15, 1, 1, 0, 10, 0.2, 0, null, null, false, false, false, "black monk", false),

    ARABIAN_BOW(SuperImage.ARABIAN_BOW, 200, 100, 0.12, 3, 8, 5, 75, 0.3, 0, WeaponType.BOW, null, false, false, true, "archer bow", true),
    SLAVE(SuperImage.SLAVE, 50, 20, 0, 3, 1, 0, 5, 0, 4, null, null, false, true, true, "slave", false),
    SLINGER(SuperImage.SLINGER, 100, 30, 0.1, 3, 2, 0, 12, 0.2, 0, null, null, false, false, true, "slinger", true),
    ASSASSIN(SuperImage.ASSASSIN, 400, 300, 0.3, 2, 1, 0, 100, 0, 0, null, null, true, false, true, "assassin", false),
    HORSE_ARCHER(SuperImage.HORSE_ARCHER, 300, 120, 0.2, 5, 6, 2, 60, 0.5, 0, WeaponType.BOW, null, false, false, true, "horse archer", true),
    ARABIAN_SWORDSMAN(SuperImage.ARABIAN_SWORDSMAN, 500, 220, 0.2, 4, 1, 0, 80, 0, 0, WeaponType.SWORDS, WeaponType.LEATHER_ARMOR, false, false, true, "arabian swordsman", false),
    FIRE_THROWER(SuperImage.FIRE_THROWER, 150,100 , 0.1, 4, 3, 0, 70, 0.1, 0, null, null, false, false, true, "fire thrower", true),
    COW(SuperImage.COW, 20, 0, 0, 1, 0, 0, 0, 0, 0, null, null, false, false, false, "cow", false),
    DOG(SuperImage.DOG, 200,100,0,4,1,0,0,0,0,null,null,false,false,false,"dog",false),
    ;
    //cow and war dogs?

    int hitPoint;
    int attackPower;
    double defensePower;
    int speed;
    int range;
    int secondRange;
    int cost;
    double precision;
    int delay;
    WeaponType weapon;
    WeaponType weapon2;
    boolean canClimb;
    /*boolean canThrowLadders;*/
    boolean canDigDitch;
    boolean isArab;
    boolean isArcherType;
    String name;
    SuperImage img;

    UnitType(SuperImage img, int hitPoint, int attackPower, double defensePower, int speed, int range, int secondRange, int cost,
             double precision, int delay, WeaponType weapon, WeaponType weapon2, boolean canClimb,
            /* boolean canThrowLadders,*/ boolean canDigDitch, boolean isArab, String name, boolean isArcherType) {
        this.img = img;
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
        this.weapon2 = weapon2;
        this.canClimb = canClimb;
        /*this.canThrowLadders = canThrowLadders;*/
        this.canDigDitch = canDigDitch;
        this.isArab = isArab;
        this.name = name;
        this.isArcherType = isArcherType;
    }

    public static UnitType getSoldierTypeByString(String soldierName) {
        UnitType type = null;
        for (UnitType ts : UnitType.values()) {
            if (ts.name.equals(soldierName)) type = ts;
        }
        return type;
    }
    public SuperImage getImg() {
        return img;
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

    public boolean isArcherType() {
        return isArcherType;
    }

    public int getSpeed() {
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

    public boolean isCanDigDitch() {
        return canDigDitch;
    }

    public boolean isArab() {
        return isArab;
    }

    public String getName() {
        return name;
    }

    public WeaponType getWeapon2() {
        return weapon2;
    }
}
