package org.example.Controller.GameControllers;

import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.Data;
import org.example.Model.Game;
import org.example.Model.Soldier;
import org.example.Model.TileStructure;
import org.example.View.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapController {
    public static Game currentGame;
    public static int currentX;
    public static int currentY;
    public static void showMapRow(int x, int y){
        for(int i = 0; i < 60; i++)
            System.out.print("-");
        System.out.println("-");
        for(int i = 0; i < 15; i++){
            String tileColor = currentGame.getTileByCoordinates(y, x - 7 + i).getType().getColorNumber();
            if(currentGame.getTileByCoordinates(y, x - 7 + i).getSoldiers().size() > 0)
                System.out.print("|" + "\u001B[" + tileColor + "m" + " S " + "\u001B[0m");
            else if(currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding() != null) {
                if(currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getBuildingType() != BuildingType.TREE)
                    System.out.print("|" + "\u001B[" + tileColor + "m" + " B " + "\u001B[0m");//instance of wall
                else System.out.print("|" + "\u001B[" + tileColor + "m" + " T " + "\u001B[0m");
            }
            else System.out.print("|" + "\u001B[" + tileColor + "m" + "   " + "\u001B[0m");
        }
        System.out.println("|");
    }

    public static void showMap(int x, int y){
        currentX = x;
        currentY = y;
        showMapRow(x, y - 2);
        showMapRow(x, y - 1);
        showMapRow(x, y);
        showMapRow(x, y + 1);
        showMapRow(x, y + 2);
        for(int i = 0; i < 60; i++)
            System.out.print("-");
        System.out.println("-");
    }

    public static void moveMap(String input){
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        Matcher matcher;
        matcher = Pattern.compile("up").matcher(input);
        while(matcher.find())
            up++;
        matcher = Pattern.compile("down").matcher(input);
        while(matcher.find())
            down++;
        matcher = Pattern.compile("left").matcher(input);
        while(matcher.find())
            left++;
        matcher = Pattern.compile("right").matcher(input);
        while(matcher.find())
            right++;
        matcher = Pattern.compile("up (?<up>\\d+)").matcher(input);
        while(matcher.find())
            up += Integer.parseInt(matcher.group("up")) - 1;
        matcher = Pattern.compile("down (?<down>\\d+)").matcher(input);
        while(matcher.find())
            down += Integer.parseInt(matcher.group("down")) - 1;
        matcher = Pattern.compile("left (?<left>\\d+)").matcher(input);
        while(matcher.find())
            left += Integer.parseInt(matcher.group("left")) - 1;
        matcher = Pattern.compile("right (?<right>\\d+)").matcher(input);
        while(matcher.find())
            right += Integer.parseInt(matcher.group("right")) - 1;

        int newX = currentX + right - left;
        int newY = currentY + down - up;
        if(newX < 7 || newX > GameController.currentGame.getMapWidth() - 8 || newY < 2 || newY > GameController.currentGame.getMapHeight() - 3)
            System.out.println("Can't show the map near boundary points");
        else{
            currentX = newX;
            currentY = newY;
            showMap(currentX, currentY);
        }
    }

    public static String showDetails(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return "Invalid coordinates\n";
        String result = "";
        result += "ground structure: " + currentGame.getTileByCoordinates(y, x).getType().toString().toLowerCase() + "\n";
        //show resources
        for(Soldier soldier : currentGame.getTileByCoordinates(y, x).getSoldiers()){
            //show soldiers
        }
        String buildingString = "empty\n";
        boolean tree = false;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null) {
            if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.TREE)
                buildingString = currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().toString().toLowerCase() + "\n";
            else tree = true;
        }
        result += "buildings: " + buildingString;
        if(tree) result += "there is a tree here!\n";
        return result;
    }

    public static Response saveMap(String name) {
        currentGame = GameController.currentGame;
        System.out.println(name);
        // if (name == null) return Response.EMPTY_MAP_NAME;
        System.out.println(currentGame.getMap() == null);
        Data.saveMap(name, currentGame.getMap());
        return Response.SAVE_MAP_SUCCESSFUL;
    }
}
