package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Controller.PathFinder;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.Producers;
import org.example.View.Response;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Deque;
import java.util.concurrent.Callable;
import java.util.Objects;
import java.util.regex.Matcher;

public class SoldierController {
    public static ArrayList<Unit> soldiers;
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
        for(Unit unit : soldiers){
            if(unit.getUnitType() == type) {
                number++;
                unit.setKingSaidToMove(true);
                unit.setWishPlace(currentGame.getTileByCoordinates(y, x));
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
        for(Unit soldier : soldiers){
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
        if(soldiers.get(0).getUnitType() == UnitType.ENGINEER)
            return Response.THIS_UNIT_CANT_PATROL;
        for(Unit soldier : soldiers){
            ((Soldier) soldier).setSaidToPatrol(true);
            ((Soldier) soldier).setPatrolWishPlace1(currentGame.getTileByCoordinates(y1, x1));
            ((Soldier) soldier).setPatrolWishPlace2(currentGame.getTileByCoordinates(y2, x2));
            soldier.setWishPlace(((Soldier) soldier).getPatrolWishPlace1());
            soldier.setKingSaidToMove(false);
        }
        return Response.PATROL_SUCCESSFUL;
    }

    public static Response stopPatrolling(){
        for(Unit soldier : soldiers){
            if(((Soldier) soldier).isSaidToPatrol()) {
                ((Soldier) soldier).setSaidToPatrol(false);
                soldier.setWishPlace(currentGame.getTileByCoordinates(soldier.getYCoordinate(), soldier.getXCoordinate()));
            }
        }
        return Response.STOP_PATROL;
    }

    public static Response throwLadder(){
        return null;
        //todo
        //after select unit
    }

    public static Response digDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!soldiers.get(0).getUnitType().isCanDigDitch())
            return Response.CANT_DIG_DITCH;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.DITCH_UNDER_BUILDING;
        if(currentGame.getTileByCoordinates(y, x).getAllUnits().size() > 0)
            return Response.DITCH_UNDER_SOLDIERS;
        if(currentGame.getTileByCoordinates(y, x).isDitch())
            return Response.THERE_IS_ALREADY_DITCH;
        if(currentGame.getTileByCoordinates(y, x).getType() != TileStructure.EARTH &&
                currentGame.getTileByCoordinates(y, x).getType() != TileStructure.GRASS &&
                currentGame.getTileByCoordinates(y, x).getType() != TileStructure.MEADOW)
            return Response.CANT_DIG_HERE;
        for(Unit soldier : soldiers){
            soldier.setKingSaidToMove(true);
            soldier.setWishPlace(currentGame.getTileByCoordinates(y, x));
            ((Soldier) soldier).setDitch(currentGame.getTileByCoordinates(y, x));
        }
        soldiers.get(0).getOwner().getDitches().add(currentGame.getTileByCoordinates(y, x));
        currentGame.getTileByCoordinates(y, x).setDitchDelay(3);
        return Response.DIGGING;
    }

    public static Response fillDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!currentGame.getTileByCoordinates(y, x).isDitch())
            return Response.THERE_IS_NO_DITCH;
        if(!soldiers.get(0).getUnitType().isCanDigDitch())
            return Response.CANT_FILL_DITCH;
        Tile adjacent = GameController.getAdjacentCell(currentGame.getTileByCoordinates(y, x), soldiers.get(0));
        if(adjacent == null)
            return Response.THE_UNIT_CANT_GO_THERE;
        for(Unit soldier : soldiers){
            soldier.setKingSaidToMove(true);
            soldier.setWishPlace(adjacent);
            ((Soldier) soldier).setFill(currentGame.getTileByCoordinates(y, x));
        }
        return Response.FILLING;
    }

    public static Response setUnitState(Matcher matcher){
        String stateString = matcher.group("state");
        int state;
        if(soldiers.get(0).getUnitType() == UnitType.ENGINEER)
            return Response.YOU_CANT_SET_STATE_FOR_ENGINEERS;
        if(Objects.equals(stateString, "offensive"))
            state = 2;
        else if(Objects.equals(stateString, "standing"))
            state = 1;
        else if(Objects.equals(stateString, "defensive"))
            state = 0;
        else return Response.INVALID_STATE;
        for(Unit soldier : soldiers){
            ((Soldier) soldier).setState(state);
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
        /*boolean check = true;
        for (Soldier soldier : soldiers) if (soldier.getUnitType() != UnitType.OIL_ENGINEER) check = false;
        if (!check) return Response.INAPPROPRIATE_UNIT;
        check = false;
        for (Soldier soldier : soldiers) if (soldier.isHasOil()) check = true;
        if (!check) return Response.NO_OIL;
        String[] groupNames = {"direction"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName("direction");
        String direction = Controller.makeEntryValid(matcher.group("direction"));
        String[] allDirections = {"s","n","e","w"};
        if (direction.equals("r")) direction = allDirections[(int)Math.floor(4*Math.random())];
        int x = soldiers.get(0).getXCoordinate();
        int y = soldiers.get(0).getYCoordinate();
        if (isDirectionOutOfBoundaries(direction,y,x)) return Response.OUT_OF_BOUNDARIES;
        int destinationX = directionX(x,direction);
        int destinationY = directionY(y,direction);
        for (Soldier soldier : currentGame.getTileByCoordinates(destinationY,destinationX).getSoldiers()) {
            if (soldier.getOwner() != currentGame.currentPlayer()) {
                soldier.setFlammable(true);
            }
        }
        Building thisBuilding;
        if ((thisBuilding = currentGame.getTileByCoordinates(destinationY,destinationX).getBuilding()) != null && thisBuilding.getOwner() != currentGame.currentPlayer())
            thisBuilding.setFlammable(true);
        Producers destinationBuilding = soldiers.get(0).getOwner().getRandomOilSmelter(y,x);
        destinationY = destinationBuilding.getYCoordinate();
        destinationX = destinationBuilding.getXCoordinate();
        Tile destination = currentGame.getTileByCoordinates(destinationY,destinationX);
        for (Soldier soldier : soldiers) {
            soldier.setHasOil(false);
            if (destination == null) destination = currentGame.getTileByCoordinates(y,x);
            soldier.setWishPlace(destination);
            soldier.setKingSaidToMove(true);
        }*/
        return Response.POUR_OIL;
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

    private static boolean isDirectionOutOfBoundaries(String direction, int y, int x) {
        if (direction.equals("n") && y == 0) return true;
        if (direction.equals("e") && x == 0) return true;
        if (direction.equals("w") && x + 1 == currentGame.getMapWidth()) return true;
        if (direction.equals("s") && y + 1 == currentGame.getMapHeight()) return true;
        return false;
    }

    private static int directionX(int x, String direction) {
        if (direction.equals("e")) return x+1;
        if (direction.equals("w")) return x-1;
        return x;
    }

    private static int directionY(int y, String direction) {
        if (direction.equals("n")) return y - 1;
        if (direction.equals("s")) return y + 1;
        return y;
    }
}
