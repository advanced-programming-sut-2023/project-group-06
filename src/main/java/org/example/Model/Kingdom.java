package org.example.Model;

import org.example.Controller.GameControllers.GameController;
import org.example.Controller.PathFinder;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Producers;
import org.example.Model.BuildingGroups.Storage;

import java.util.ArrayList;
import java.util.Deque;

public class Kingdom {

    //todo decide about nonSoldierUnits...
    private int happiness = 0;
    private int foodDiversity;
    private int totalFoodAmount = 0;
    private int foodRate = 0;
    private int tax;
    private int wealth;
    private int population = 0;
    private int maxPopulation = 9;
    private int fear = 0;
    private ArrayList<Unit> engineers = new ArrayList<>();
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Unit> cows = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Storage> resources = new ArrayList<>();
    private ArrayList<Storage> foods = new ArrayList<>();
    private ArrayList<Storage> weapons = new ArrayList<>();
    private ArrayList<Storage> stables = new ArrayList<>();
    private ArrayList<Storage> engineerGuilds = new ArrayList<>();
    private ArrayList<Producers> quarries = new ArrayList<>();
    private ArrayList<Producers> inns = new ArrayList<>();
    private ArrayList<Producers> oilSmelter = new ArrayList<>();
    private ArrayList<TradeRequest> tradeRequestsSentByMe = new ArrayList<>();
    private ArrayList<TradeRequest> tradeRequestsAcceptedByMe = new ArrayList<>();
    private ArrayList<TradeRequest> allTradeRequestsSentToMe = new ArrayList<>();
    private ArrayList<Equipment> equipments = new ArrayList<>();
    private ArrayList<Tile> ditches = new ArrayList<>();
    private User owner;
    private Soldier king;
    private Building mainCastle;
    private int happinessIncrease;
    private String color;

    public Kingdom(User owner) {
        this.wealth = 500;
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Unit> getEngineers() {
        return engineers;
    }

    public void addEngineer(Unit engineer){
        this.engineers.add(engineer);
    }

    public void addToHappinessIncrease(int amount){
        this.happinessIncrease += amount;
    }

    public int getHappinessIncrease() {
        return happinessIncrease;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getTotalFoodAmount() {
        return this.getFoodAmountByType(FoodType.APPLES) +
                this.getFoodAmountByType(FoodType.CHEESE) +
                this.getFoodAmountByType(FoodType.MEAT) +
                this.getFoodAmountByType(FoodType.BREAD);
    }

    public ArrayList<Tile> getDitches() {
        return ditches;
    }

    public ArrayList<Unit> getCows() {
        return cows;
    }

    public ArrayList<Unit> getUnits() {
        return units;
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

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void removeBuilding(Building building){
        this.buildings.remove(building);
    }

    public void removeUnit(Unit unit){
        this.units.remove(unit);
        if(unit instanceof Soldier)
            this.soldiers.remove((Soldier) unit);
        if (unit instanceof Equipment)
            this.equipments.remove((Equipment) unit);
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
        this.totalFoodAmount += totalFoodAmount;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public void addToWealth(int wealth) {
        this.wealth += wealth;
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

    public Producers getRandomQuarry(){
        ArrayList<Producers> nonEmptyQuarries = new ArrayList<>();
        for(Producers quarry : quarries){
            if(quarry.getStored() > 0)
                nonEmptyQuarries.add(quarry);
        }
        if(nonEmptyQuarries.size() == 0)
            return null;
        else return nonEmptyQuarries.get((int) (Math.random() * nonEmptyQuarries.size()));
    }

    public Storage getRandomStockpile(){
        ArrayList<Storage> nonEmptyStockpiles = new ArrayList<>();
        for(Storage storage : resources){
            if(storage.getStored() < storage.getCapacity())
                nonEmptyStockpiles.add(storage);
        }
        if(nonEmptyStockpiles.size() == 0)
            return null;
        else return nonEmptyStockpiles.get((int) (Math.random() * nonEmptyStockpiles.size()));
    }

    public Producers getRandomOilSmelter(int y, int x) {
        ArrayList<Producers> nonEmptyOilSmelters = new ArrayList<>();
        for (Producers producers : oilSmelter) {
            if (producers.getStored() > 0) nonEmptyOilSmelters.add(producers);
        }
        PathFinder pathFinder = new PathFinder(GameController.currentGame.getMap());
        Deque<Tile> path = null;
        Producers destination = null;
        for (Producers building : nonEmptyOilSmelters) {
            path = pathFinder.findPath(GameController.currentGame.getTileByCoordinates(y,x),
                    GameController.currentGame.getTileByCoordinates(building.getYCoordinate(),building.getXCoordinate()));
            if (path != null) {
                destination = building;
                break;
            }
        }
        return destination;
    }

    public int getHorseNumber() {
        int number = 0;
        for(Storage storage : stables)
            number += storage.getStored();
        return number;
    }

    public void takeHorseFromStable(){
        for(Storage storage : stables){
            if(storage.getStored() > 0){
                storage.addToStored(-1);
                break;
            }
        }
    }

    public boolean killCow(int count){
        ArrayList<Unit> toKillCows = new ArrayList<>();
        for(Unit unit : cows){
            if(unit.getCowStored() == 0)
                toKillCows.add(unit);
        }
        if(toKillCows.size() < count) return false;
        else{
            int killed = 0;
            for(Unit cow : toKillCows){
                this.removeUnit(cow);
                this.cows.remove(cow);
                killed++;
                if(killed == count) break;
            }
        }
        return true;
    }

    public int wineUsage(){
        int amount = 0;
        for(Producers producers : inns)
            amount += Math.min(producers.getStored(), 5);
        return (int) ((double) amount * 0.4);
    }

    public ArrayList<Storage> getWeapons() {
        return weapons;
    }

    public ArrayList<Storage> getStables() {
        return stables;
    }

    public void addSoldier(Soldier soldier) {
        this.soldiers.add(soldier);
    }

    public void addUnit(Unit unit){
        this.units.add(unit);
    }

    public int getAvailableEngineers() {
        int totalEngineers = 0;
        for (Unit engineer : engineers) {
            if (engineer.isAvailable()) totalEngineers++;
        }
        return totalEngineers;
    }

    public void payEngineer(int count, int y, int x) {
        int amount = count;
        for (Storage storage : engineerGuilds) {
            if (storage.getStored() > 0) {
                int payment = Math.min(amount, storage.getStored());
                storage.addToStored(-1 * payment);
                amount -= payment;
                int cnt = 0;
                int i = 0;
                while (cnt < payment) {
                    Tile tile = GameController.currentGame.getTileByCoordinates(storage.getYCoordinate(),storage.getXCoordinate());
                    if (tile.getAllUnits().get(i).getUnitType() == UnitType.ENGINEER && tile.getAllUnits().get(i).isAvailable()) {
                        cnt++;
                        tile.getAllUnits().get(i).setWishPlace(GameController.currentGame.getTileByCoordinates(y,x));
                        tile.getAllUnits().get(i).setAvailable(false);
                        tile.getAllUnits().get(i).setKingSaidToMove(true);
                    }
                    i++;
                }
            }
            if (amount == 0) break;
        }
    }

    public void payEngineer(int count) {
        int amount = count;
        for (Storage storage : engineerGuilds) {
            if (storage.getStored() > 0) {
                int payment = Math.min(amount, storage.getStored());
                storage.addToStored(-1 * payment);
                amount -= payment;
                int cnt = 0;
                Tile tile = GameController.currentGame.getTileByCoordinates(storage.getYCoordinate(),storage.getXCoordinate());
                int i = tile.getAllUnits().size() - 1;
                while (cnt < payment) {
                    if (tile.getAllUnits().get(i).getUnitType() == UnitType.ENGINEER && tile.getAllUnits().get(i).isAvailable()) {
                        cnt++;
                        units.remove(tile.getAllUnits().get(i));
                        tile.removeUnit(tile.getAllUnits().get(i));
                    }
                    i--;
                }
            }
            if (amount == 0) break;
        }
    }

    public int getOilAmount(){
        int amount = 0;
        for(Producers producers : oilSmelter)
            amount += producers.getStored();
        return amount;
    }

    public ArrayList<Storage> getEngineerGuilds() {
        return engineerGuilds;
    }

    public ArrayList<Producers> getQuarries() {
        return quarries;
    }

    public ArrayList<Producers> getInns() {
        return inns;
    }

    public ArrayList<Producers> getOilSmelter() {
        return oilSmelter;
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

    public void removeFromTradeRequestsSentToMeById(int id) {
        for (int i = allTradeRequestsSentToMe.size() - 1; i >= 0; i--) {
            TradeRequest tradeRequest = allTradeRequestsSentToMe.get(i);
            if (tradeRequest.getId() == id) allTradeRequestsSentToMe.remove(i);
        }
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

    public int getFoodDiversity(){
        int apple = 0;
        int meat = 0;
        int cheese = 0;
        int bread = 0;
        for (Building building : buildings) {
            if(building.getBuildingType() == BuildingType.APPLE_ORCHARD)
                apple = 1;
            else if(building.getBuildingType() == BuildingType.BAKERY)
                bread = 1;
            else if(building.getBuildingType() == BuildingType.HUNTERS_POST)
                meat = 1;
            else if(building.getBuildingType() == BuildingType.DIARY_FARMER)
                cheese = 1;
        }
        return apple + meat + cheese + bread;
    }

    public int taxEffectOnHappiness(int theTax){
        int addToHappiness;
        if(theTax > 3)
            addToHappiness = -4 * theTax + 8;
        else if(theTax > 0)
            addToHappiness = -2 * theTax;
        else addToHappiness = -2 * theTax + 1;
        return addToHappiness;
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
        if(resource.getType() != ResourcesType.ALE) {
            for (Storage storage : resources) {
                for (Asset asset : storage.getAssets()) {
                    if (((Resources) asset).getType() == resource.getType()) {
                        int cost = Math.min(amount, asset.getAmount());
                        asset.addToAmount(-1 * cost);
                        amount -= cost;
                        storage.addToStored(-1 * cost);
                        if (amount == 0) return;
                    }
                }
            }
        }
        else{
            for(Producers producers : inns){
                int payment = -1 * Math.min(producers.getStored(), amount);
                producers.addToStored(payment);
                amount -= payment;
                if(amount == 0) return;
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
        if(resourcesType != ResourcesType.ALE) {
            for (Storage storage : resources) {
                for (Asset asset : storage.getAssets()) {
                    if (((Resources) asset).getType() == resourcesType)
                        amount += asset.getAmount();
                }
            }
        }
        else{
            for(Producers storage : inns)
                amount += storage.getStored();
        }
        return amount;
    }

    public int getResourcesAmount() {
        int amount = 0;
        for (Storage storage : resources) {
            amount += storage.getStored();
        }
        return amount;
    }

    public int getAleAmount(){
        int amount = 0;
        for (Producers producers : inns) {
            amount += producers.getStored();
        }
        return amount;
    }

    public int getResourcesCapacity() {
        int capacity = 0;
        for (Storage storage : resources)
            capacity += storage.getCapacity();
        return capacity;
    }

    public int getInnsCapacity(){
        int capacity = 0;
        for (Producers producers : inns)
            capacity += producers.getCapacity();
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

    public void addStoneToStockpile(Unit cow, Storage stockpile){
        int cost = Math.min(cow.getCowStored(), stockpile.getCapacity() - stockpile.getStored());
        cow.addToStored(-1 * cost);
        boolean assetStoneFound = false;
        for(Asset asset : stockpile.getAssets()){
            if(((Resources) asset).getType() == ResourcesType.STONE){
                asset.addToAmount(cost);
                assetStoneFound = true;
                break;
            }
        }
        if(!assetStoneFound){
            Resources newResource = new Resources(cost, ResourcesType.STONE);
            stockpile.getAssets().add(newResource);
        }
        stockpile.addToStored(cost);
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
        else if(asset instanceof Resources && ((Resources) asset).getType() != ResourcesType.ALE){
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
        else if (asset instanceof Resources && ((Resources)asset).getType() == ResourcesType.ALE) {
            for(Producers producers : inns){
                int cost = Math.min(amount, producers.getCapacity() - producers.getStored());
                producers.addToStored(cost);
                amount -= cost;
            }
        }
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
    }
}