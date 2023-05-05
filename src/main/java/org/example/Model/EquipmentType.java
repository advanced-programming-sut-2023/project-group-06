package org.example.Model;

public enum EquipmentType {
    PORTABLE_SHIELD(200,0,1,1,0,false,0,0),
    BATTERING_RAM(300,500,4,2,1,false,1,0),
    SIEGE_TOWER(400,0,4,2,0,false,0,0),
    CATAPULT(600,250,2,1,12,false,1,0.2), //manjenigh moteharek
    TREBUCHET(800,400,3,3,15,false,0,0), //manjenigh sabet
    FIRE_BALLISTA(500,200,2,2,5,false,2,0) //sang andaz atashin

    ;
    private int health;
    private int damage;
    private int engineerPrice;
    private int delay;
    private int range;
    private boolean doesAttackSoldiers;
    private int speed;
    private double precision;

    EquipmentType(int health, int damage, int engineerPrice, int delay, int range, boolean doesAttackSoldiers, int speed, double precision) {
        this.health = health;
        this.damage = damage;
        this.engineerPrice = engineerPrice;
        this.delay = delay;
        this.range = range;
        this.doesAttackSoldiers = doesAttackSoldiers;
        this.speed = speed;
        this.precision = precision;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getEngineerPrice() {
        return engineerPrice;
    }

    public int getDelay() {
        return delay;
    }

    public int getRange() {
        return range;
    }

    public boolean isDoesAttackSoldiers() {
        return doesAttackSoldiers;
    }

    public int getSpeed() {
        return speed;
    }

    public double getPrecision() {
        return precision;
    }
}
