package org.example.Model;

public class Weapon extends Asset{
    WeaponType type;

    public Weapon(int amount, WeaponType type) {
        super(amount);
        this.type = type;
    }

    public WeaponType getType() {
        return type;
    }
}
