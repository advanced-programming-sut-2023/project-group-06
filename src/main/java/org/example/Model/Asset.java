package org.example.Model;

public class Asset {
    private int amount;
    public Asset(int amount) {
        this.amount = amount;
    }

    public void addToAmount(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return amount;
    }
}
