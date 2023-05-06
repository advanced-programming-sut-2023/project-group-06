package org.example.Model;

public class Equipment {
    private int health;
    private int damage;
    private int engineerPrice;
    private EquipmentType equipmentType;
    private Kingdom owner;
    private int delay;
    private int x;
    private int y;
    private int range;
    private boolean doesAttackSoldiers;
    private int speed;
    private double precision;

    public Equipment(EquipmentType equipmentType, Kingdom owner, int x, int y) {
        this.equipmentType = equipmentType;
        this.owner = owner;
        this.x = x;
        this.y = y;
        speed = equipmentType.getSpeed();
        range = equipmentType.getRange();
        doesAttackSoldiers = equipmentType.isDoesAttackSoldiers();
        health = equipmentType.getHealth();
        damage = equipmentType.getDamage();
        engineerPrice = equipmentType.getEngineerPrice();
        precision = equipmentType.getPrecision();
    }

    public int getHealth() {
        return health;
    }

    public void subHealth(int health) {
        this.health -= health;
    }

    public void resetHealth() {
        this.health = equipmentType.getHealth();
    }

    public int getDamage() {
        return damage;
    }

    public int getEngineerPrice() {
        return engineerPrice;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public int getDelay() {
        return delay;
    }

    public void subDelay(int delay) {
        this.delay -= delay;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRange() {
        return range;
    }

    public boolean doesAttackSoldiers() {
        return doesAttackSoldiers;
    }

    public int getSpeed() {
        return speed;
    }

    public double getPrecision() {
        return precision;
    }
}
