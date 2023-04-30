package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.Game;
import org.example.Model.Soldier;
import org.example.Model.UnitType;
import org.example.View.Response;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class SoldierController {
    public static ArrayList<Soldier> soldiers;
    public static Game currentGame;

    public static Response moveUnitWithType(Matcher matcher){
        /*matcher.find();
        String[] groupNames = {"type","x","y"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        if(matcher.group("x") == null || matcher.group("y") == null)
            return Response.INVALID_INPUT;*/
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitType type = UnitType.getSoldierTypeByString(Controller.makeEntryValid(matcher.group("type")));
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(type == null)
            return Response.INVALID_TYPE;
        //check for the target tile todo
        if(!currentGame.getTileByCoordinates(y, x).getType().CanBeCrossed() ||
                (currentGame.getTileByCoordinates(y, x).getBuilding() != null
                        && !currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().isCanYouEnterIt()))
            return Response.CANT_GO_THERE;
        int number = 0;
        for(Soldier soldier : soldiers){
            if(soldier.getUnitType() == type) {
                number++;
                soldier.setKingSaidToMove(true);
                soldier.setWishPlace(currentGame.getTileByCoordinates(y, x));
            }
        }
        if(number == 0)
            return Response.NO_UNITS_WITH_THAT_TYPE;
        return Response.MOVE_SUCCESSFUL;
    }

    public static Response moveUnitWithoutType(Matcher matcher){
        return null;
        //todo
    }

    public static Response patrolUnit(Matcher matcher){
        return null;
        //todo
        //after select unit
    }

    public static Response throwLadder(){
        return null;
        //todo
        //after select unit
    }

    public static Response digDitch(Matcher matcher){
        return null;
        //todo
        //after select unit
    }

    public static Response setUnitPosition(Matcher matcher){
        return null;
        //todo
        //after select unit
    }

    public static Response fireAtEnemy(Matcher matcher){
        return null;
        //todo
        //after select person
    }

    public static Response attack(Matcher matcher){
        return null;
        //todo
        //after select person
    }

    public static Response pourOil(Matcher matcher){
        return null;
        //todo
        //after select person
    }

    public static Response digTunnel(Matcher matcher){
        return null;
        //todo
        //after select person
    }

    public static Response buildEquipment(Matcher matcher){
        return null;
        //todo
        //after select person
    }

    public static Response disband(){
        return null;
        //todo
        //after select persson
    }
}
