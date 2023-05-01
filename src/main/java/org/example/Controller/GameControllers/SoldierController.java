package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Controller.PathFinder;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.Game;
import org.example.Model.Soldier;
import org.example.Model.Tile;
import org.example.Model.UnitType;
import org.example.View.Response;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.Callable;
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
        boolean check = true;
        for (Soldier soldier : soldiers) if (soldier.getUnitType() != UnitType.OIL_ENGINEER) check = false;
        if (!check) return Response.INAPPROPRIATE_UNIT;
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
        if ((thisBuilding = currentGame.getTileByCoordinates(destinationY,destinationX).getBuilding()) != null)
            thisBuilding.setFlammable(true);
        PathFinder pathFinder = new PathFinder(GameController.currentGame.getMap());
        Deque<Tile> path = null;
        Tile destination = null;
        for (Building building : currentGame.currentPlayer().getOilSmelter()) {
            path = pathFinder.findPath(currentGame.getTileByCoordinates(y,x),currentGame.getTileByCoordinates(building.getYCoordinate(),building.getXCoordinate()));
            if (path != null) {
                destination = currentGame.getTileByCoordinates(building.getYCoordinate(),building.getXCoordinate());
                break;
            }
        }
        for (Soldier soldier : soldiers) {
            soldier.setHasOil(false);
            if (destination == null) destination = currentGame.getTileByCoordinates(y,x);
            soldier.setWishPlace(destination);
        }
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
        if (direction.equals("e")) return x-1;
        if (direction.equals("w")) return x+1;
        return x;
    }

    private static int directionY(int y, String direction) {
        if (direction.equals("n")) return y - 1;
        if (direction.equals("s")) return y + 1;
        return y;
    }
}
