package org.example.Model;

import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.Storage;

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
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Resources> resources = new ArrayList<>();
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Weapon> weapons = new ArrayList<>();
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

    public void payResource(Resources resource){
        int amount = resource.getAmount();
        for(Resources resources1 : this.resources){
            if(resources1.getType() == resource.getType()){
                int cost = Math.min(amount, resources1.getAmount());
                resources1.addToAmount(-1 * cost);
                amount -= cost;
            }
            if(amount == 0) break;
        }
    }

    public int getResourceAmountByType(ResourcesType resourcesType){
        int amount = 0;
        for(Resources resources1 : this.resources){
            if(resources1.getType() == resourcesType)
                amount += resources1.getAmount();
        }
        return amount;
    }

    public int getFoodAmountByType(FoodType foodType){
        int amount = 0;
        for(Food food : this.foods){
            if(food.getType() == foodType)
                amount += food.getAmount();
        }
        return amount;
    }
}