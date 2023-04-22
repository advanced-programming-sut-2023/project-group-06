package org.example.Controller.GameControllers;

import org.example.Model.FoodType;
import org.example.Model.Kingdom;
import org.example.Model.ResourcesType;
import org.example.Model.WeaponType;
import org.example.View.MainMenu;
import org.example.View.Response;

import java.util.regex.Matcher;

public class KingdomController {
    public static Kingdom currentKingdom;
    public static String showPopularityFactors(){
        return null;
        //todo
    }

    public static int showPopularity(){
        return currentKingdom.getHappiness();
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
    //salam
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
        output += "Oil: " + currentKingdom.getWeaponAmountByType(WeaponType.OIL);
        return output;
    }
}
