package org.example.View.GameMenus;

import org.example.Controller.Controller;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.KingdomController;
import org.example.Controller.GameControllers.MapController;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
    GameController gameController;

    //some commands will return null if they are successful
    public MenuType run(Scanner scanner){
        Matcher matcher;
        boolean defaultMap = false;
        while(true){
            System.out.println("do you want a default map?(y,n)");
            String command = scanner.nextLine();
            if(Objects.equals(command, "y")){
                int mapNumber = 0;
                while(true){
                    System.out.println("choose a default map from 1 to 10");
                    command = scanner.nextLine();
                    if((matcher = Commands.getMatcher(command, Commands.CHOOSE_DEFAULT_MAP)).find()) {
                        mapNumber = Integer.parseInt(matcher.group("mapNumber"));
                        break;
                    }
                    else System.out.println(Response.INVALID_COMMAND.message);
                }
                //setDefaultMap(mapNumber, mapNumber.height, mapNumber.width)
                defaultMap = true;
                break;
            }
            else if(Objects.equals(command, "n")){
                while(true) {
                    System.out.println("set map width and height");
                    command = scanner.nextLine();
                    if((matcher = Commands.getMatcher(command, Commands.SET_MAP_WIDTH_HEIGHT)).find()){
                        GameController.initializeMap(matcher);//I hope currentGame isn't needed here
                        break;
                    }
                    else System.out.println(Response.INVALID_COMMAND.message);
                }
                break;
            }
            else System.out.println(Response.INVALID_COMMAND.message);
        }
        while (true) {
            String command = scanner.nextLine();
            if (Commands.getMatcher(command, Commands.START_THE_GAME).find()) {
                System.out.println("game started");
                break;
                //todo
            }
            else if((matcher = Commands.getMatcher(command, Commands.DROP_TREE)).find())
                System.out.println(GameController.dropTree(matcher).message);
            else if ((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                if (x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
                    System.out.println("Can't show the map near boundary points");
                else {
                    MapController.currentX = x;
                    MapController.currentY = y;
                    MapController.currentGame = GameController.currentGame;
                    MapController.showMap(x, y);
                    MenuType.MAP_MENU.menu.run(scanner);
                }
            } else System.out.println(Response.INVALID_COMMAND.message);
        }
        while(true){
            String command = scanner.nextLine();
            if(Commands.getMatcher(command, Commands.ENTER_KINGDOM_MENU).find()) {
                System.out.println(Response.ENTER_KINGDOM_MENU.message);
                KingdomController.currentKingdom = GameController.currentPlayer;
                return MenuType.KINGDOM_MENU;
            }
            else if(Commands.getMatcher(command, Commands.ENTER_SHOP_MENU).find()) {
                System.out.println(Response.ENTER_SHOP_MENU.message);
                return MenuType.SHOP_MENU;
            }
            else if(Commands.getMatcher(command, Commands.ENTER_TRADE_MENU).find()) {
                System.out.println(Response.ENTER_TRADE_MENU.message);
                return MenuType.TRADE_MENU;
            }
            else if((matcher = Commands.getMatcher(command, Commands.DROP_TREE)).find())
                System.out.println(GameController.dropTree(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                if(x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
                    System.out.println("Can't show the map near boundary points");
                else{
                    MapController.showMap(x, y);
                    return MenuType.MAP_MENU;
                }
            }
            else if((matcher = Commands.getMatcher(command, Commands.DROP_BUILDING)).find())
                System.out.println(GameController.dropBuilding(matcher).message);

            //todo
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
