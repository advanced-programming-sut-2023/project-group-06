package org.example.View.GameMenus;

import org.example.Controller.GameControllers.KingdomController;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Scanner;

public class KingdomMenu extends Menu {
    KingdomController kingdomController;

    public MenuType run(Scanner scanner){
        while(true){
            String command = scanner.nextLine();
            if(Commands.getMatcher(command, Commands.SHOW_POPULARITY_FACTORS).find())
                System.out.println(KingdomController.showPopularityFactors());
            else if(Commands.getMatcher(command, Commands.SHOW_POPULARITY).find())
                System.out.println(KingdomController.showPopularity());
            else if(Commands.getMatcher(command, Commands.SHOW_FOOD_LIST).find())
                System.out.println(KingdomController.showFoodList());
            else if(Commands.getMatcher(command, Commands.SHOW_FOOD_RATE).find())
                System.out.println(KingdomController.showFoodRate());
            else if(Commands.getMatcher(command, Commands.SHOW_TAX_RATE).find())
                System.out.println(KingdomController.showTaxRate());
            else if(Commands.getMatcher(command, Commands.SET_FOOD_RATE).find())
                System.out.println(KingdomController.setFoodRate(Commands.getMatcher(command, Commands.SET_FOOD_RATE)).message);
            else if(Commands.getMatcher(command, Commands.SET_TAX_RATE).find())
                System.out.println(KingdomController.setTaxRate(Commands.getMatcher(command, Commands.SET_TAX_RATE)).message);
            else if(Commands.getMatcher(command, Commands.SET_FEAR_RATE).find())
                System.out.println(KingdomController.setFearRate(Commands.getMatcher(command, Commands.SET_FEAR_RATE)).message);
            else if(Commands.getMatcher(command, Commands.EXIT).find())
                return MenuType.GAME_MENU;
            else System.out.println(Response.INVALID_COMMAND);
        }
    }
}
