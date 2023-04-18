package org.example.Model;

import org.example.Model.BuildingGroups.Building;

import java.util.ArrayList;

public class Kingdom {
    private int happiness = 0;
    private int foodDiversity = 0;
    private int totalFoodAmount = 0;
    private int foodRate;
    private int tax;
    private int wealth;
    private int population = 0;
    private int maxPopulation;
    private int fear = 0;
    private int horseNumber;
    private int engineers;
    /////// adding food storages
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Resources> resources = new ArrayList<>();
    private User owner;
    private Soldier king;

    public Kingdom(User owner) {
        this.tax = 0;
        this.wealth = 1000;
        this.maxPopulation = 100;
        this.owner = owner;
        //todo
    }

    public int getHappiness() {
        return happiness;
    }

    public int getFoodDiversity() {
        return foodDiversity;
    }

    public int getTotalFoodAmount() {
        return totalFoodAmount;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTax() {
        return tax;
    }

    public int getWealth() {
        return wealth;
    }

    public int getPopulation() {
        return population;
    }

    public void addPopulation(int number) { population += number; }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public int getFear() {
        return fear;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void addEngineers(int number) { engineers += number; }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public User getOwner() {
        return owner;
    }

    public Soldier getKing() {
        return king;
    }

    public void setFoodDiversity(int foodDiversity) {
        this.foodDiversity = foodDiversity;
    }

    public void addToTotalFoodAmount(int totalFoodAmount) {
        this.totalFoodAmount = totalFoodAmount;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public void addToWealth(int wealth) {
        this.wealth = wealth;
    }

    public void addToPopulation(int population) {
        this.population = population;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public ArrayList<Resources> getResources() {
        return resources;
    }

    public void addResources(Resources resource) {
        this.resources.add(resource);
    }
}