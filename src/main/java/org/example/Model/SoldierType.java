package org.example.Model;

public enum SoldierType {
    ;

    int attackPower;
    int defensePower;
    int speed;
    int range;
    int secondRange;
    int cost;
    int precision;
    int delay;
    boolean isCapableOfClimbing;
    boolean canThrowLadders;
    boolean canDigDitch;

    SoldierType(int attackPower, int defensePower, int speed, int range, int secondRange,
                int cost, int precision, int delay, boolean isCapableOfClimbing,
                boolean canThrowLadders, boolean canDigDitch) {
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.speed = speed;
        this.range = range;
        this.secondRange = secondRange;
        this.cost = cost;
        this.precision = precision;
        this.delay = delay;
        this.isCapableOfClimbing = isCapableOfClimbing;
        this.canThrowLadders = canThrowLadders;
        this.canDigDitch = canDigDitch;
    }
}
