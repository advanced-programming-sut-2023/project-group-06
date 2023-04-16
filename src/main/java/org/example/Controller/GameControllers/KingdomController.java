package org.example.Controller.GameControllers;

import org.example.Model.Kingdom;
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

    public static Response showFoodList(){
        return null;
        //todo
    }

    public static Response setFoodRate(Matcher matcher){
        String foodRateString = matcher.group("foodRate");
        int foodRate = Integer.parseInt(foodRateString);
        if(foodRate > 2 || foodRate < -2)
            return Response.FOOD_RATE_NUMBER_INVALID;
        //if he is out of food it must give errors
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
        currentKingdom.setTax(fearRate);
        return Response.SET_FEAR_RATE_SUCCESSFUL;
    }
}
