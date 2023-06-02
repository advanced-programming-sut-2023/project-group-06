package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.KingdomController;
import org.example.View.Commands;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class KingdomMenu extends Application {
    KingdomController kingdomController;

//    public MenuType run(Scanner scanner){
//        while(true){
//            Matcher matcher;
//            String command = scanner.nextLine();
//            if(Commands.getMatcher(command, Commands.SHOW_POPULARITY_FACTORS).find())
//                System.out.print(KingdomController.showPopularityFactors());
//            else if(Commands.getMatcher(command, Commands.SHOW_POPULARITY).find())
//                System.out.println(KingdomController.showPopularity());
//            else if(Commands.getMatcher(command, Commands.SHOW_FOOD_LIST).find())
//                System.out.print(KingdomController.showFoodList());
//            else if(Commands.getMatcher(command, Commands.SHOW_FOOD_RATE).find())
//                System.out.println(KingdomController.showFoodRate());
//            else if(Commands.getMatcher(command, Commands.SHOW_TAX_RATE).find())
//                System.out.println(KingdomController.showTaxRate());
//            else if((matcher = Commands.getMatcher(command, Commands.SET_FOOD_RATE)).find())
//                System.out.println(KingdomController.setFoodRate(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_TAX_RATE)).find())
//                System.out.println(KingdomController.setTaxRate(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_FEAR_RATE)).find())
//                System.out.println(KingdomController.setFearRate(matcher).message);
//            else if(Commands.getMatcher(command, Commands.EXIT).find()) {
//                System.out.println("Kingdom menu closed successfully!");
//                return null;
//            }
//            else if(Commands.getMatcher(command,Commands.SHOW_RESOURCES).find())
//                System.out.println(KingdomController.showResources());
//            else if (Commands.getMatcher(command,Commands.SHOW_WEAPONS).find())
//                System.out.println(KingdomController.showWeapons());
//            else if(Commands.getMatcher(command, Commands.SHOW_POPULATION).find())
//                System.out.println(KingdomController.showPopulation());
//            else if(Commands.getMatcher(command, Commands.SHOW_WEALTH).find())
//                System.out.println(KingdomController.showWealth());
//            else if (Commands.getMatcher(command,Commands.SHOW_GOLD).find())
//                System.out.println(KingdomController.showGold());
//            else if(Commands.getMatcher(command, Commands.SHOW_AVAILABLE_ENGINEERS).find())
//                System.out.println(KingdomController.showAvailableEngineers());
//            else if (Commands.getMatcher(command,Commands.SHOW_SOLDIERS).find())
//                System.out.println(KingdomController.showSoldiers());
//            else if (Commands.getMatcher(command,Commands.SHOW_HORSE).find())
//                System.out.println(KingdomController.showHorses());
//            else if (Commands.getMatcher(command,Commands.SHOW_BUILDINGS).find()) {
//                System.out.println(KingdomController.showBuildings());
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
