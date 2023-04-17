package org.example.Controller.GameControllers;

import org.example.Model.Game;
import org.example.Model.Soldier;
import org.example.View.Response;

import java.util.regex.Matcher;

public class MapController {
    public static Game currentGame;
    public static Response showMap(Matcher matcher){
        return null;
        //todo
    }

    public static void moveMap(Matcher matcher){
        //todo
    }

    public static String showDetails(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return "Invalid coordinates";
        String result = "";
        result += "ground structure: " + currentGame.getTileByCoordinates(y, x).getType().toString();///////I hope toString works
        //show resources
        for(Soldier soldier : currentGame.getTileByCoordinates(y, x).getSoldiers()){
            //show soldiers
        }
        String buildingString = "empty";
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            buildingString = currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().toString();//////// I hope toString works
        result += "buildings: " + buildingString;
        return result;
    }
}
