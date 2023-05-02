package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.Game;
import org.example.Model.Soldier;
import org.example.Model.UnitType;
import org.example.View.Response;

import java.util.ArrayList;
import java.util.Objects;
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
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        //check for the target tile todo
        if(!currentGame.getTileByCoordinates(y, x).getType().CanBeCrossed() ||
                (currentGame.getTileByCoordinates(y, x).getBuilding() != null
                        && !currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().isCanYouEnterIt()))
            return Response.CANT_GO_THERE;
        int number = 0;
        for(Soldier soldier : soldiers){
            soldier.setKingSaidToMove(true);
            soldier.setWishPlace(currentGame.getTileByCoordinates(y, x));
        }
        return Response.MOVE_SUCCESSFUL;
    }

    public static Response patrolUnit(Matcher matcher){
        int x1 = Integer.parseInt(matcher.group("x1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        //todo check target places
        for(Soldier soldier : soldiers){
            soldier.setSaidToPatrol(true);
            soldier.setPatrolWishPlace1(currentGame.getTileByCoordinates(y1, x1));
            soldier.setPatrolWishPlace2(currentGame.getTileByCoordinates(y2, x2));
            soldier.setWishPlace(soldier.getPatrolWishPlace1());
            soldier.setKingSaidToMove(false);
        }
        return Response.PATROL_SUCCESSFUL;
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

    public static Response setUnitState(Matcher matcher){
        String stateString = matcher.group("state");
        int state;
        if(Objects.equals(stateString, "offensive"))
            state = 2;
        else if(Objects.equals(stateString, "standing"))
            state = 1;
        else if(Objects.equals(stateString, "defensive"))
            state = 0;
        else return Response.INVALID_STATE;
        for(Soldier soldier : soldiers){
            soldier.setState(state);
        }
        return Response.SET_STATE_SUCCESSFUL;
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
