package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.KingdomController;
import org.example.Controller.GameControllers.MapController;
import org.example.View.Commands;
import org.example.View.Response;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Application {
    GameController gameController;

//    public MenuType run(Scanner scanner){
//        Matcher matcher;
//        while(true){
//            System.out.println("do you want a default map?(y,n)");
//            String command = scanner.nextLine();
//            if(Objects.equals(command, "y")){
//                String mapName;
//                while(true){
//                    System.out.println("enter the map name: ");
//                    command = scanner.nextLine();
//                    if((matcher = Commands.getMatcher(command, Commands.CHOOSE_DEFAULT_MAP)).find()) {
//                        mapName = matcher.group("mapName");
//                        String result = GameController.setDefaultMap(mapName).message;
//                        System.out.println(result);
//                        if(Objects.equals(result, "Map is set!"))
//                            break;
//                    }
//                    else System.out.println(Response.INVALID_COMMAND.message);
//                }
//                break;
//            }
//            else if(Objects.equals(command, "n")){
//                while(true) {
//                    System.out.println("set map width and height");
//                    command = scanner.nextLine();
//                    if((matcher = Commands.getMatcher(command, Commands.SET_MAP_WIDTH_HEIGHT)).find()){
//                        System.out.println(GameController.initializeMap(matcher).message);
//                        break;
//                    }
//                    else System.out.println(Response.INVALID_COMMAND.message);
//                }
//                break;
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command, Commands.START_THE_GAME).find()) {
//                System.out.println("game started");
//                break;
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.DROP_TREE)).find())
//                System.out.println(GameController.dropTree(matcher).message);
//            else if ((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
//                int x = Integer.parseInt(matcher.group("x"));
//                int y = Integer.parseInt(matcher.group("y"));
//                if (x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
//                    System.out.println("Can't show the map outside the boundaries");
//                else {
//                    MapController.currentX = x;
//                    MapController.currentY = y;
//                    MapController.currentGame = GameController.currentGame;
//                    System.out.print(MapController.showMap(x, y));
//                    MenuType.MAP_MENU.menu.run(scanner);
//                }
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_ONE_TILE)).find())
//                System.out.println(GameController.setTextureOneTile(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_MULTIPLE_TILES)).find())
//                System.out.println(GameController.setTextureMultipleTiles(matcher).message);
//            else if ((matcher = Commands.getMatcher(command, Commands.SAVE_MAP)).find()) {
//                Response response = MapController.saveMap(matcher.group("name"));
//                System.out.println(response.message);
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//        int turnIndex = 0;
//        System.out.printf("turn 1 : %s is now playing\n", GameController.currentPlayer.getOwner().getUsername());
//        System.out.println("choose a color and put your main castle");
//        while(true) {
//            String command = scanner.nextLine();
//            if ((matcher = Commands.getMatcher(command, Commands.PUT_MAIN_CASTLE)).find() && GameController.currentPlayer.getMainCastle() == null)
//                System.out.println(GameController.putMainCastle(matcher).message);
//            else if (Commands.getMatcher(command, Commands.PUT_MAIN_CASTLE).find())
//                System.out.println("you have already put your main castle!");
//            else if (GameController.currentPlayer.getMainCastle() != null && Commands.getMatcher(command, Commands.NEXT_TURN).find()){
//                System.out.printf(GameController.nextTurn().message, GameController.currentGame.getNumberOfTurns(),
//                        GameController.currentPlayer.getOwner().getUsername());
//                turnIndex++;
//                if(turnIndex < GameController.currentGame.getNumberOfPlayers())
//                    System.out.println("choose a color and put your main castle");
//            }
//            else if(GameController.currentPlayer.getMainCastle() == null && Commands.getMatcher(command, Commands.NEXT_TURN).find())
//                System.out.println(Response.PUT_MAIN_CASTLE.message);
//            else if ((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
//                int x = Integer.parseInt(matcher.group("x"));
//                int y = Integer.parseInt(matcher.group("y"));
//                if (x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
//                    System.out.println("Can't show the map outside the boundaries");
//                else {
//                    MapController.currentX = x;
//                    MapController.currentY = y;
//                    MapController.currentGame = GameController.currentGame;
//                    System.out.print(MapController.showMap(x, y));
//                    MenuType.MAP_MENU.menu.run(scanner);
//                }
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//            if(turnIndex == GameController.currentGame.getNumberOfPlayers())
//                break;
//        }
//        while(true){
//            String command = scanner.nextLine();
//            if(Commands.getMatcher(command, Commands.ENTER_KINGDOM_MENU).find()) {
//                System.out.println(Response.ENTER_KINGDOM_MENU.message);
//                KingdomController.currentKingdom = GameController.currentPlayer;
//                MenuType.KINGDOM_MENU.menu.run(scanner);
//            }
//            else if(Commands.getMatcher(command, Commands.ENTER_TRADE_MENU).find()) {
//                System.out.println(Response.ENTER_TRADE_MENU.message);
//                GameController.initializeTradeController();
//                MenuType.TRADE_MENU.menu.run(scanner);
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find()) {
//                int x = Integer.parseInt(matcher.group("x"));
//                int y = Integer.parseInt(matcher.group("y"));
//                if(x < 7 || x > GameController.currentGame.getMapWidth() - 8 || y < 2 || y > GameController.currentGame.getMapHeight() - 3)
//                    System.out.println("Can't show the map outside the boundaries");
//                else{
//                    MapController.currentX = x;
//                    MapController.currentY = y;
//                    MapController.currentGame = GameController.currentGame;
//                    System.out.print(MapController.showMap(x, y));
//                    MenuType.MAP_MENU.menu.run(scanner);
//                }
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.DROP_TREE)).find())
//                System.out.println(GameController.dropTree(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_ONE_TILE)).find())
//                System.out.println(GameController.setTextureOneTile(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE_MULTIPLE_TILES)).find())
//                System.out.println(GameController.setTextureMultipleTiles(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.DROP_BUILDING)).find())
//                System.out.println(GameController.dropBuilding(matcher).message);
//            else if (Commands.getMatcher(command, Commands.NEXT_TURN).find()) {
//                String result = GameController.nextTurn().message;
//                if(result.startsWith("The winner")) {
//                    System.out.println("The game is finished");
//                    System.out.printf(result, GameController.currentGame.getPlayers().get(0).getUsername());
//                    System.out.println("Entered main menu");
//                    GameController.currentGame.getPlayers().get(0).addHighScore(10000 +
//                            GameController.currentGame.getKingdoms().get(0).getHappiness() +
//                            GameController.currentGame.getKingdoms().get(0).getMaxPopulation());
//                    return MenuType.MAIN_MENU;
//                }
//                System.out.printf(result, GameController.currentGame.getNumberOfTurns(),
//                        GameController.currentPlayer.getOwner().getUsername());
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.CLEAR_BLOCK)).find())
//                System.out.println(GameController.clearBlock(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.DROP_ROCK)).find())
//                System.out.println(GameController.dropRuck(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_THE_GATE)).find())
//                System.out.println(GameController.setTheGate(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.DROP_UNIT)).find())
//                System.out.println(GameController.dropUnit(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.REMOVE_DITCH)).find())
//                System.out.println(GameController.removeDitch(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.DROP_STAIR)).find())
//                System.out.println(GameController.dropStair(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SELECT_BUILDING)).find()) {
//                String result = GameController.selectBuilding(matcher).message;
//                System.out.println(result);
//                if(Objects.equals(result, "Select building successful!"))
//                    MenuType.BUILDING_MENU.menu.run(scanner);
//                else if(Objects.equals(result, "Entered shop menu!"))
//                    MenuType.SHOP_MENU.menu.run(scanner);
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.SELECT_UNIT)).find()) {
//                String result = GameController.selectUnit(matcher).message;
//                System.out.println(result);
//                if(Objects.equals(result, "Select soldier successful!"))
//                    MenuType.SOLDIER_MENU.menu.run(scanner);
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
