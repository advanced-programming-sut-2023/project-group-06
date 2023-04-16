package org.example.Model;

import java.util.regex.Matcher;

public class Soldier extends Person{
    int attackPower;
    int defensePower;
    int speed;
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
    SoldierType soldierType;

    public Soldier(int XCoordinate, int YCoordinate, Kingdom owner, SoldierType soldierType) {
        super(XCoordinate, YCoordinate, owner);
        this.soldierType = soldierType;
    }
}
