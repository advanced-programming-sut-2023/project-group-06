package org.example.Model;

import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Storage;

import java.util.ArrayList;

public class Kingdom {

    //todo decide about nonSoldierUnits...
    private int happiness = 0;
    private int foodDiversity = 0;
    private int totalFoodAmount = 0;
    private int foodRate = 0;
    private int tax = 0;
    private int wealth;
    private int population = 1;
    private int maxPopulation = 9;
    private int fear = 0;
    private int horseNumber;
    private int availableEngineers;
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Unit> nonSoldierUnits = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Storage> resources = new ArrayList<>();
    private ArrayList<Storage> foods = new ArrayList<>();
    private ArrayList<Storage> weapons = new ArrayList<>();
    private ArrayList<TradeRequest> tradeRequestsSentByMe = new ArrayList<>();
    private ArrayList<TradeRequest> tradeRequestsAcceptedByMe = new ArrayList<>();
    private ArrayList<TradeRequest> allTradeRequestsSentToMe = new ArrayList<>();
    private User owner;
    private Soldier king;
    private Building mainCastle;
    private int happinessIncrease;

    public Kingdom(User owner) {
        this.wealth = 500;
        this.owner = owner;
    }

    public void addToHappinessIncrease(int amount){
        this.happinessIncrease += amount;
    }

    public int getHappinessIncrease() {
        return happinessIncrease;
    }

    public int getEngineers() {
        return availableEngineers;
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

    public void addToPopulation(int number) { population += number; }

    public void addToHappiness(int number){
        this.happiness += number;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void addToMaxPopulation(int amount){
        this.maxPopulation += amount;
    }

    public int getFear() {
        return fear;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void addEngineers(int number) { availableEngineers += number; }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void removeBuilding(Building building){
        this.buildings.remove(building);
    }

    public User getOwner() {
        return owner;
    }

    public Soldier getKing() {
        return king;
    }

    public void setKing(Soldier king) {
        this.king = king;
    }

    public Building getMainCastle() {
        return mainCastle;
    }

    public void setMainCastle(Building mainCastle) {
        this.mainCastle = mainCastle;
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
        this.wealth += wealth;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public ArrayList<Storage> getResources() {
        return resources;
    }

    public ArrayList<Storage> getFoods() {
        return foods;
    }

    public int getHorseNumber() {
        return horseNumber;
    }

    public void addToHorseNumber(int amount){
        this.horseNumber += amount;
    }

    public int getAvailableEngineers() {
        return availableEngineers;
    }

    public void addAvailableEngineers(int amount) {
        this.availableEngineers += amount;
    }

    public ArrayList<Storage> getWeapons() {
        return weapons;
    }

    public void addSoldier(Soldier soldier) {
        this.soldiers.add(soldier);
    }

    public ArrayList<Unit> getNonSoldierUnits() {
        return nonSoldierUnits;
    }

    public void addNonSoldierUnits(Unit unit) {
        this.nonSoldierUnits.add(unit);
    }

    public ArrayList<TradeRequest> getTradeRequestsSentByMe() {
        return tradeRequestsSentByMe;
    }

    public ArrayList<TradeRequest> getTradeRequestsAcceptedByMe() {
        return tradeRequestsAcceptedByMe;
    }

    public void addToTradeRequestsAcceptedByMe(TradeRequest tradeRequest) {
        this.tradeRequestsAcceptedByMe.add(tradeRequest);
    }

    public void addToTradeRequestsSentByMe(TradeRequest tradeRequest) {
        this.tradeRequestsSentByMe.add(tradeRequest);
    }

    public ArrayList<TradeRequest> getAllTradeRequestsSentToMe() {
        return allTradeRequestsSentToMe;
    }

    public void addToAllTradeRequestsSentToMe(TradeRequest tradeRequest) {
        this.allTradeRequestsSentToMe.add(tradeRequest);
    }

    public TradeRequest getTradeRequestSentToMeById(int id) {
        for (TradeRequest tradeRequest : allTradeRequestsSentToMe) {
            if (tradeRequest.getId() == id) return tradeRequest;
        }
        return null;
    }

    public TradeRequest getTradeRequestsNotAcceptedByMeById(int id) {
        for (TradeRequest tradeRequest : allTradeRequestsSentToMe) {
            if ((tradeRequest.getId() == id) && !tradeRequest.isAccepted()) return tradeRequest;
        }
        return null;
    }

    public Food getFoodByTypeFromStorage(Storage storage, FoodType type){
        for(Asset asset : storage.getAssets()){
            if(((Food)asset).getType() == type)
                return (Food) asset;
        }
        return null;
    }

    public Weapon getWeaponByTypeFromStorage(Storage storage, WeaponType type){
        for(Asset asset : storage.getAssets()){
            if(((Weapon)asset).getType() == type)
                return (Weapon) asset;
        }
        return null;
    }

    public Resources getResourcesByTypeFromStorage(Storage storage, ResourcesType type){
        for(Asset asset : storage.getAssets()){
            if(((Resources)asset).getType() == type)
                return (Resources) asset;
        }
        return null;
    }

    public void eatFoods(int amount){
        int totalFood = amount;
        for(Storage storage : foods){
            for(Asset asset : storage.getAssets()){
                int cost = Math.min(totalFood, asset.getAmount());
                asset.addToAmount(-1 * cost);
                totalFood -= cost;
                storage.addToStored(-1 * cost);
                if(totalFood == 0) return;
            }
        }
    }

    public void payResource(Resources resource){
        int amount = resource.getAmount();
        for(Storage storage : resources){
            for(Asset asset : storage.getAssets()){
                if(((Resources)asset).getType() == resource.getType()){
                    int cost = Math.min(amount, asset.getAmount());
                    asset.addToAmount(-1 * cost);
                    amount -= cost;
                    storage.addToStored(-1 * cost);
                    if(amount == 0) return;
                }
            }
        }
    }

    public void useWeaponToCreateUnit(Weapon weapon){
        int amount = weapon.getAmount();
        for(Storage storage : weapons){
            for(Asset asset : storage.getAssets()){
                if(((Weapon)asset).getType() == weapon.getType()){
                    int cost = Math.min(amount, asset.getAmount());
                    asset.addToAmount(-1 * cost);
                    amount -= cost;
                    storage.addToStored(-1 * cost);
                    if(amount == 0) return;
                }
            }
        }
    }

    public int getResourceAmountByType(ResourcesType resourcesType){
        int amount = 0;
        for(Storage storage : resources){
            for(Asset asset : storage.getAssets()){
                if(((Resources)asset).getType() == resourcesType)
                    amount += asset.getAmount();
            }
        }
        return amount;
    }

    public int getResourcesAmount() {
        int amount = 0;
        for (Storage storage : resources) {
            if (storage.getBuildingType() == BuildingType.STOCKPILE) amount += storage.getStored();
        }
        return amount;
    }

    public int getResourcesCapacity() {
        int capacity = 0;
        for (Storage storage : resources) {
            if (storage.getBuildingType() == BuildingType.STOCKPILE) capacity += storage.getCapacity();
        }
        return capacity;
    }

    public int getFoodAmountByType(FoodType foodType){
        int amount = 0;
        for(Storage storage : foods){
            for(Asset asset : storage.getAssets()){
                if(((Food)asset).getType() == foodType)
                    amount += asset.getAmount();
            }
        }
        return amount;
    }

    public int getWeaponAmountByType(WeaponType weaponType) {
        int amount = 0;
        for(Storage storage : weapons){
            for(Asset asset : storage.getAssets()){
                if(((Weapon)asset).getType() == weaponType)
                    amount += asset.getAmount();
            }
        }
        return amount;
    }

    public void addAsset(Asset asset){
        int amount = asset.getAmount();
        if(asset instanceof Food){
            for(Storage storage : foods){
                int cost = Math.min(amount, storage.getCapacity() - storage.getStored());
                boolean assetTypeFound = false;
                for(Asset asset1 : storage.getAssets()){
                    if(((Food)asset1).getType() == ((Food) asset).getType()){
                        asset1.addToAmount(cost);
                        assetTypeFound = true;
                        break;
                    }
                }
                if(!assetTypeFound) {
                    Food newFood = new Food(cost, ((Food)asset).getType());
                    storage.getAssets().add(newFood);
                }
                amount -= cost;
                storage.addToStored(cost);
            }
        }
        else if(asset instanceof Weapon){
            for(Storage storage : weapons){
                int cost = Math.min(amount, storage.getCapacity() - storage.getStored());
                boolean assetTypeFound = false;
                for(Asset asset1 : storage.getAssets()){
                    if(((Weapon)asset1).getType() == ((Weapon) asset).getType()){
                        asset1.addToAmount(cost);
                        assetTypeFound = true;
                        break;
                    }
                }
                if(!assetTypeFound) {
                    Weapon newWeapon = new Weapon(cost, ((Weapon)asset).getType());
                    storage.getAssets().add(newWeapon);
                }
                amount -= cost;
                storage.addToStored(cost);
            }
        }
        else if(asset instanceof Resources){
            for(Storage storage : resources){
                int cost = Math.min(amount, storage.getCapacity() - storage.getStored());
                boolean assetTypeFound = false;
                for(Asset asset1 : storage.getAssets()){
                    if(((Resources)asset1).getType() == ((Resources) asset).getType()){
                        asset1.addToAmount(cost);
                        assetTypeFound = true;
                        break;
                    }
                }
                if(!assetTypeFound) {
                    Resources newResource = new Resources(cost, ((Resources)asset).getType());
                    storage.getAssets().add(newResource);
                }
                amount -= cost;
                storage.addToStored(cost);
            }
        }
    }
}