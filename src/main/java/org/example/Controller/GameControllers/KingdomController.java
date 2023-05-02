package org.example.Controller.GameControllers;

import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.View.Response;

import java.util.regex.Matcher;

public class KingdomController {
    public static Kingdom currentKingdom;
    public static String showPopularityFactors(){
        String result = "";
        result += "Food: " + (currentKingdom.getFoodDiversity() + currentKingdom.getFoodRate() * 4 - 1) + "\n";
        result += "Tax: " + currentKingdom.taxEffectOnHappiness(currentKingdom.getTax()) + "\n";
        result += "Fear: " + currentKingdom.getFear() + "\n";
        result += "Religion: " + currentKingdom.getHappinessIncrease() + "\n";
        result += "Wine usage: " + currentKingdom.wineUsage() + "\n";
        return result;
    }

    public static int showPopularity(){
        return currentKingdom.getHappiness();
    }

    public static int showPopulation(){
        return currentKingdom.getMaxPopulation();
    }

    public static int showWealth(){
        return currentKingdom.getWealth();
    }

    public static String showFoodList(){
        String result = "";
        result += "meat: " + currentKingdom.getFoodAmountByType(FoodType.MEAT) + "\n";
        result += "apples: " + currentKingdom.getFoodAmountByType(FoodType.APPLES) + "\n";
        result += "cheese: " + currentKingdom.getFoodAmountByType(FoodType.CHEESE) + "\n";
        result += "bread: " + currentKingdom.getFoodAmountByType(FoodType.BREAD) + "\n";
        return result;
    }

    public static Response setFoodRate(Matcher matcher){
        String foodRateString = matcher.group("foodRate");
        int foodRate = Integer.parseInt(foodRateString);
        if(foodRate > 2 || foodRate < -2)
            return Response.FOOD_RATE_NUMBER_INVALID;
        if(currentKingdom.getTotalFoodAmount() == 0)
            return Response.OUT_OF_FOOD;
        currentKingdom.setFoodRate(foodRate);
        return Response.SET_FOOD_RATE_SUCCESSFUL;
    }

    public static int showFoodRate(){
        return currentKingdom.getFoodRate();
    }

    public static Response setTaxRate(Matcher matcher){
        String taxRateString = matcher.group("taxRate");
        int taxRate = Integer.parseInt(taxRateString);
        if(taxRate < -3 || taxRate > 8)
            return Response.TAX_RATE_NUMBER_INVALID;
        if(currentKingdom.getWealth() == 0)
            return Response.OUT_OF_MONEY;
        currentKingdom.setTax(taxRate);
        return Response.SET_TAX_RATE_SUCCESSFUL;
    }

    public static int showTaxRate(){
        return currentKingdom.getTax();
    }

    public static Response setFearRate(Matcher matcher){
        String fearRateString = matcher.group("fearRate");
        int fearRate = Integer.parseInt(fearRateString);
        if(fearRate < -5 || fearRate > 5)
            return Response.FEAR_RATE_NUMBER_INVALID;
        currentKingdom.setFear(fearRate);
        return Response.SET_FEAR_RATE_SUCCESSFUL;
    }

    public static String showResources() {
        String output = "";
        output += "Stone: " + currentKingdom.getResourceAmountByType(ResourcesType.STONE) + '\n';
        output += "Wood: " + currentKingdom.getResourceAmountByType(ResourcesType.WOOD) + '\n';
        output += "Wheat: " + currentKingdom.getResourceAmountByType(ResourcesType.WHEAT) + '\n';
        output += "Ale: " + currentKingdom.getResourceAmountByType(ResourcesType.ALE) + '\n';
        output += "Hops: " + currentKingdom.getResourceAmountByType(ResourcesType.HOPS) + '\n';
        output += "Iron: " + currentKingdom.getResourceAmountByType(ResourcesType.IRON) + '\n';
        output += "Pitch: " + currentKingdom.getResourceAmountByType(ResourcesType.PITCH) + '\n';
        output += "Flour: " + currentKingdom.getResourceAmountByType(ResourcesType.FLOUR);
        return output;
    }
    public static String showWeapons() {
        String output = "";
        output += "Bow: " + currentKingdom.getWeaponAmountByType(WeaponType.BOW) + '\n';
        output += "Crossbow: " + currentKingdom.getWeaponAmountByType(WeaponType.CROSSBOW) + '\n';
        output += "Pike: " + currentKingdom.getWeaponAmountByType(WeaponType.PIKE) + '\n';
        output += "Mace: " + currentKingdom.getWeaponAmountByType(WeaponType.MACE) + '\n';
        output += "Swords: " + currentKingdom.getWeaponAmountByType(WeaponType.SWORDS) + '\n';
        output += "Spear: " + currentKingdom.getWeaponAmountByType(WeaponType.SPEAR) + '\n';
        output += "Leather armor: " + currentKingdom.getWeaponAmountByType(WeaponType.LEATHER_ARMOR) + '\n';
        output += "Metal armor: " + currentKingdom.getWeaponAmountByType(WeaponType.METAL_ARMOR) +'\n';
        output += "Oil: " + currentKingdom.getOilAmount();
        return output;
    }

    public static int showGold() {
        return currentKingdom.getWealth();
    }

    public static String showAvailableEngineers(){
        String result = "";
        result += "Available Engineers: " + currentKingdom.getAvailableEngineers() + " pop: " + currentKingdom.getPopulation();
        return result;
    }

    public static String showSoldiers() {
        String output = "all soldiers";
        for (int i = 0; i < currentKingdom.getSoldiers().size(); i++) {
            output += "\n" + currentKingdom.getSoldiers().get(i).toString();
        }
        for(Unit unit : currentKingdom.getCows())
            output += "\n" + unit.cowToString();
        return output;
    }

    public static String showHorses() {
        String output = "horse number: " + currentKingdom.getHorseNumber() + '\n';
        output += "all stables:";
        for (int i = 0; i < currentKingdom.getStables().size(); i++) {
            output += '\n' + currentKingdom.getStables().get(i).toString();
        }
        return output;
    }

    public static String showBuildings() {
        String output = "all buildings:";
        for (Building building : currentKingdom.getBuildings()) {
            output += '\n' + building.toString();
        }
        return output;
    }
}
