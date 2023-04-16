package org.example.Model;

public class Food extends Asset{
    private FoodType type;

    public Food(int amount, FoodType type) {
        super(amount);
        this.type = type;
    }

    public FoodType getType() {
        return type;
    }
}

