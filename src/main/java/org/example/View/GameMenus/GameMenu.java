package org.example.View.GameMenus;

import org.example.Controller.Controller;
import org.example.Controller.GameControllers.BuildingController;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.KingdomController;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.Data;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
    GameController gameController;

    //some commands will return null if they are successful
    public MenuType run(Scanner scanner){
        Matcher matcher;
        while(true){
            System.out.println("do you want a default map?(y,n)");
            String command = scanner.nextLine();
            if(Objects.equals(command, "y")){
                String mapName;
                while(true){
                    System.out.println("enter the map name: ");
                    command = scanner.nextLine();
                    if((matcher = Commands.getMatcher(command, Commands.CHOOSE_DEFAULT_MAP)).find()) {
                        mapName = matcher.group("mapName");
                        //if map doesn't exist return map not found
                        break;
                    }
                    else System.out.println(Response.INVALID_COMMAND.message);
                }
                GameController.setDefaultMap(Data.loadMap(mapName), Objects.requireNonNull(Data.loadMap(mapName)).length, Objects.requireNonNull(Data.loadMap(mapName))[0].length);
                break;
            }
            else if(Objects.equals(command, "n")){
                while(true) {
                    System.out.println("set map width and height");
                    command = scanner.nextLine();
                    if((matcher = Commands.getMatcher(command, Commands.SET_MAP_WIDTH_HEIGHT)).find()){
                        System.out.println(GameController.initializeMap(matcher).message);
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
                    System.out.println("Can't show the map outside the boundaries");
                else {
                    MapController.currentX = x;
                    MapController.currentY = y;
                    MapController.currentGame = GameController.currentGame;
                    MapController.showMap(x, y);
                    MenuType.MAP_MENU.menu.run(scanner);
                }
            }
            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_ONE_TILE)).find())
                System.out.println(GameController.setTextureOneTile(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_MULTIPLE_TILES)).find())
                System.out.println(GameController.setTextureMultipleTiles(matcher).message);
            else if ((matcher = Commands.getMatcher(command, Commands.SAVE_MAP)).find()) {
                Response response = MapController.saveMap(matcher.group("name"));
                System.out.println(response.message);
            }
            else System.out.println(Response.INVALID_COMMAND.message);
        }
        int turnIndex = 0;
        System.out.println("choose a color and put your main castle");
        while(true) {
            String command = scanner.nextLine();
            if ((matcher = Commands.getMatcher(command, Commands.PUT_MAIN_CASTLE)).find() && GameController.currentPlayer.getMainCastle() == null)
                System.out.println(GameController.putMainCastle(matcher).message);
            else if (Commands.getMatcher(command, Commands.PUT_MAIN_CASTLE).find())
                System.out.println("you have already put your main castle!");
            else if (GameController.currentPlayer.getMainCastle() != null && Commands.getMatcher(command, Commands.NEXT_TURN).find()){
                System.out.println(GameController.nextTurn().message);
                turnIndex++;
                if(turnIndex < GameController.currentGame.getNumberOfPlayers())
                    System.out.println("choose a color and put your main castle");
            }
            else if(GameController.currentPlayer.getMainCastle() == null && Commands.getMatcher(command, Commands.NEXT_TURN).find())
                System.out.println(Response.PUT_MAIN_CASTLE.message);
            else if ((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                if (x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
                    System.out.println("Can't show the map outside the boundaries");
                else {
                    MapController.currentX = x;
                    MapController.currentY = y;
                    MapController.currentGame = GameController.currentGame;
                    MapController.showMap(x, y);
                    MenuType.MAP_MENU.menu.run(scanner);
                }
            }
            else System.out.println(Response.INVALID_COMMAND.message);
            if(turnIndex == GameController.currentGame.getNumberOfPlayers())
                break;
        }
        while(true){
            String command = scanner.nextLine();
            if(Commands.getMatcher(command, Commands.ENTER_KINGDOM_MENU).find()) {
                System.out.println(Response.ENTER_KINGDOM_MENU.message);
                KingdomController.currentKingdom = GameController.currentPlayer;
                MenuType.KINGDOM_MENU.menu.run(scanner);
            }
            else if(Commands.getMatcher(command, Commands.ENTER_SHOP_MENU).find()) {
                System.out.println(Response.ENTER_SHOP_MENU.message);
                MenuType.SHOP_MENU.menu.run(scanner);
            }
            else if(Commands.getMatcher(command, Commands.ENTER_TRADE_MENU).find()) {
                System.out.println(Response.ENTER_TRADE_MENU.message);
                MenuType.TRADE_MENU.menu.run(scanner);
            }
            else if((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                if(x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
                    System.out.println("Can't show the map outside the boundaries");
                else{
                    MapController.currentX = x;
                    MapController.currentY = y;
                    MapController.currentGame = GameController.currentGame;
                    MapController.showMap(x, y);
                    MenuType.MAP_MENU.menu.run(scanner);
                }
            }
            else if((matcher = Commands.getMatcher(command, Commands.DROP_TREE)).find())
                System.out.println(GameController.dropTree(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_ONE_TILE)).find())
                System.out.println(GameController.setTextureOneTile(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_MULTIPLE_TILES)).find())
                System.out.println(GameController.setTextureMultipleTiles(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.DROP_BUILDING)).find())
                System.out.println(GameController.dropBuilding(matcher).message);
            else if (Commands.getMatcher(command, Commands.NEXT_TURN).find())
                System.out.println(GameController.nextTurn().message);
            else if((matcher = Commands.getMatcher(command, Commands.CLEAR_BLOCK)).find())
                System.out.println(GameController.clearBlock(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.DROP_ROCK)).find())
                System.out.println(GameController.dropRuck(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.SELECT_BUILDING)).find()) {
                String result = GameController.selectBuilding(matcher).message;
                System.out.println(result);
                if(Objects.equals(result, "Select building successful!"))
                    MenuType.BUILDING_MENU.menu.run(scanner);
            }
            else if((matcher = Commands.getMatcher(command, Commands.SELECT_UNIT)).find())
                System.out.println(GameController.selectUnit(matcher).message);
            //todo
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
