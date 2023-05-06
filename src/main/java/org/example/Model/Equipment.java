package org.example.Model;

public class Equipment extends Unit {
    private int damage;
    private int engineerPrice;
    private EquipmentType equipmentType;
    private int delay;
    private int range;
    private boolean doesAttackSoldiers;
    private double precision;
    private int cost;

    public Equipment(EquipmentType equipmentType, Kingdom owner, int x, int y) {
        super(x,y,owner);
        this.equipmentType = equipmentType;
        super.speed = equipmentType.getSpeed();
        range = equipmentType.getRange();
        doesAttackSoldiers = equipmentType.isDoesAttackSoldiers();
        super.health = equipmentType.getHealth();
        damage = equipmentType.getDamage();
        engineerPrice = equipmentType.getEngineerPrice();
        precision = equipmentType.getPrecision();
        cost = equipmentType.getCost();
        owner.addEquipment(this);
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

    public int getDelay() {
        return delay;
    }

    public void subDelay(int delay) {
        this.delay -= delay;
    }

    public int getRange() {
        return range;
    }

    public boolean doesAttackSoldiers() {
        return doesAttackSoldiers;
    }

    public double getPrecision() {
        return precision;
    }
}
