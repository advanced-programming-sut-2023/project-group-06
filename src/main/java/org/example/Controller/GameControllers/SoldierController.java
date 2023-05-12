package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Controller.PathFinder;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Producers;
import org.example.Model.BuildingGroups.Towers;
import org.example.View.Response;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Deque;
import java.util.concurrent.Callable;
import java.util.Objects;
import java.util.regex.Matcher;

import static org.example.View.Response.NO_LADDER;
import static org.example.View.Response.THROW_LADDER;

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
        if(currentGame.getTileByCoordinates(y, x).getHeight() <= -2)
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
        if(currentGame.getTileByCoordinates(y, x).getHeight() <= -2)
            return Response.CANT_GO_THERE;
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
        if(soldiers.get(0).getUnitType() == UnitType.ENGINEER)
            return Response.THIS_UNIT_CANT_PATROL;
        if(x1 < 0 || x1 >= currentGame.getMapWidth() || y1 < 0 || y1 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(x2 < 0 || x2 >= currentGame.getMapWidth() || y2 < 0 || y2 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(currentGame.getTileByCoordinates(y1, x1).getHeight() <= -2)
            return Response.CANT_GO_THERE;
        if(currentGame.getTileByCoordinates(y2, x2).getHeight() <= -2)
            return Response.CANT_GO_THERE;
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
        if(currentGame.getTileByCoordinates(soldiers.get(0).getYCoordinate(), soldiers.get(0).getXCoordinate()).getBuilding() != null
                || currentGame.getTileByCoordinates(soldiers.get(0).getYCoordinate(), soldiers.get(0).getXCoordinate()).getBuilding().getBuildingType() != BuildingType.WALL)
            return NO_LADDER;
        Building building = currentGame.getTileByCoordinates(soldiers.get(0).getYCoordinate(), soldiers.get(0).getXCoordinate()).getBuilding();
        for(int i = 0; i < 4; i++){
            if((((Towers) building).lather & (1 << i)) == 0)
                ((Towers) building).lather |= (1 << i);
        }
        return THROW_LADDER;
    }

    public static Response digDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!soldiers.get(0).getUnitType().isCanDigDitch())
            return Response.CANT_DIG_DITCH;
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
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
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
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

    public static Response stopDigging(){
        boolean wereTheyAboutToDig = false;
        for(Unit unit : soldiers){
            if(unit.getUnitType() != UnitType.ENGINEER){
                if(((Soldier) unit).getDitch() != null){
                    wereTheyAboutToDig = true;
                    ((Soldier) unit).setDitch(null);
                }
            }
        }
        if(!wereTheyAboutToDig) return Response.THESE_UNITS_WERE_NOT_ABOUT_TO_DIG;
        return Response.STOP_DIGGING;
    }

    public static Response fireAtEnemy(Matcher matcher){
        String nullGroup;
        if ((nullGroup = Controller.nullGroup(matcher, "x", "y")) != null) return Response.getEmptyResponseByName(nullGroup);
        int x = Integer.parseInt(Controller.makeEntryValid(matcher.group("x")));
        int y = Integer.parseInt(Controller.makeEntryValid(matcher.group("y")));
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if (!soldiers.get(0).getUnitType().isArcherType()) return Response.ARCHER_TYPE;
        Soldier archer = (Soldier) soldiers.get(0);
        int range = archer.getUnitType().getRange();
        if (currentGame.getTileByCoordinates(archer.getYCoordinate(), archer.getXCoordinate()).getBuilding() != null
                && currentGame.getTileByCoordinates(archer.getYCoordinate(), archer.getXCoordinate()).getBuilding().getBuildingType().getBuildingClass() == Towers.class)
            range += ((Towers) currentGame.getTileByCoordinates(archer.getYCoordinate(), archer.getXCoordinate()).getBuilding()).getFireRange();
        int squareOfDistance = (x - archer.getXCoordinate()) * (x - archer.getXCoordinate()) + (y - archer.getYCoordinate()) * (y - archer.getYCoordinate());
        if (squareOfDistance > range * range || squareOfDistance < archer.getUnitType().getSecondRange() * archer.getUnitType().getSecondRange())
            return Response.NOT_IN_RANGE;
        int attackPower = archer.getUnitType().getAttackPower();
        if (!currentGame.getTileByCoordinates(y,x).existEnemyOnThisTile(soldiers.get(0).getOwner())) return Response.NO_ENEMY_ON_THIS_TILE;
        attackPower += (int) (((double)archer.getOwner().getFear() / 20) * attackPower);
        if (currentGame.getTileByCoordinates(y, x).getEquipment() != null) {
            int attackPowerToShield = Math.min(currentGame.getTileByCoordinates(y, x).getEquipment().getHealth(),attackPower);
            currentGame.getTileByCoordinates(y, x).getEquipment().subHealth(attackPowerToShield);
            attackPower -= attackPowerToShield;
        }
        Unit unit = null;
        for (Unit unit1 : currentGame.getTileByCoordinates(y, x).getAllUnits()) {
            if (unit1.getOwner() != archer.getOwner()) {
                unit = unit1;
                break;
            }
        }
        attackPower -= (int) ((double)attackPower * unit.getUnitType().getDefensePower());
        if(Math.random() < archer.getUnitType().getPrecision())
            attackPower = 20;
        unit.subHealth(attackPower);
        return Response.LETS_ATTACK;
    }

    public static Response attack(Matcher matcher){
        String nullGroup;
        if ((nullGroup = Controller.nullGroup(matcher, "x", "y")) != null) return Response.getEmptyResponseByName(nullGroup);
        int x = Integer.parseInt(Controller.makeEntryValid(matcher.group("x")));
        int y = Integer.parseInt(Controller.makeEntryValid(matcher.group("y")));
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if (!currentGame.getTileByCoordinates(y,x).existEnemyOnThisTile(soldiers.get(0).getOwner())) return Response.NO_ENEMY_ON_THIS_TILE;
        for (Unit unit : soldiers) {
            unit.setWishPlace(currentGame.getTileByCoordinates(y, x));
        }
        return Response.LETS_ATTACK;
    }

    public static Response pourOil(Matcher matcher){
        boolean check = soldiers.get(0).getUnitType() == UnitType.OIL_ENGINEER;
        if (!check) return Response.INAPPROPRIATE_UNIT;
        check = false;
        for (Unit soldier : soldiers) if (((Soldier) soldier).isHasOil()) check = true;
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
        for (Unit unit : currentGame.getTileByCoordinates(destinationY,destinationX).getAllUnits()) {
            if (!(unit instanceof Equipment) && unit.getOwner() != currentGame.currentPlayer()) {
                unit.setFlammable(true);
            }
        }
        Building thisBuilding;
        if ((thisBuilding = currentGame.getTileByCoordinates(destinationY,destinationX).getBuilding()) != null && thisBuilding.getOwner() != currentGame.currentPlayer())
            thisBuilding.setFlammable(true);
        Producers destinationBuilding = soldiers.get(0).getOwner().getRandomOilSmelter(y,x);
        destinationY = destinationBuilding.getYCoordinate();
        destinationX = destinationBuilding.getXCoordinate();
        Tile destination = currentGame.getTileByCoordinates(destinationY,destinationX);
        for (Unit soldier : soldiers) {
            ((Soldier) soldier).setHasOil(false);
            if (destination == null) destination = currentGame.getTileByCoordinates(y,x);
            soldier.setWishPlace(destination);
            soldier.setKingSaidToMove(true);
        }
        return Response.POUR_OIL;
    }

    public static Response digTunnel(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(soldiers.get(0).getUnitType() != UnitType.TUNNELER)
            return Response.CANT_DIG_TUNNEL;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() == null ||
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.TREE ||
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.ROCK)
            return Response.DIG_TUNNEL_UNDER_BUILDING;
        if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.WALL &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.STAIR &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.LOOKOUT_TOWER &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.DEFENSE_TURRET)
            return Response.DIG_UNDER_THIS_TYPE;
        Building building = currentGame.getTileByCoordinates(y, x).getBuilding();
        Tile center = currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate());
        Tile adjacent = GameController.getAdjacentCell(center, soldiers.get(0));
        if(adjacent == null)
            return Response.THE_UNIT_CANT_GO_THERE;
        for(Unit soldier : soldiers){
            building.getTunnelers().add((Soldier) soldier);
            ((Soldier) soldier).setTunnel(building);
            soldier.setKingSaidToMove(true);
            soldier.setWishPlace(adjacent);
        }
        return Response.DIG_TUNNEL;
    }

    public static Response putLadder(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String directionString = Controller.makeEntryValid(matcher.group("direction"));
        int direction = GameController.getDirection(directionString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(soldiers.get(0).getUnitType() != UnitType.LADDER_MAN)
            return Response.LADDER_MAN;
        Soldier ladderMan = null;
        for(Unit soldier : soldiers){
            if(((Soldier) soldier).isHasLadder()) {
                ladderMan = ((Soldier) soldier);
                break;
            }
        }
        if(ladderMan == null) return Response.OUT_OF_LADDER;
        int frontX = x;
        int frontY = y;
        if(direction % 2 == 0) frontY += direction - 1;
        else frontX += 2 - direction;
        if(currentGame.getTileByCoordinates(frontY, frontX).getBuilding() == null ||
                currentGame.getTileByCoordinates(frontY, frontX).getBuilding().getBuildingType() != BuildingType.WALL)
            return Response.PUT_LADDER_NEXT_TO_WALL;
        Building building = currentGame.getTileByCoordinates(frontY, frontX).getBuilding();
        ladderMan.setLadder(building);
        building.getLadderMen().add(ladderMan);
        ladderMan.setLadder(building);
        ladderMan.setKingSaidToMove(true);
        ladderMan.setWishPlace(currentGame.getTileByCoordinates(y, x));
        return Response.LETS_PUT_LADDER;
    }

    public static Response buildEquipment(Matcher matcher){
        String nullGroupName;
        if ((nullGroupName = Controller.nullGroup(matcher,"equipmentName")) != null)
            return Response.getEmptyResponseByName(nullGroupName);
        EquipmentType equipmentType = EquipmentType.getEquipmentTypeByString(Controller.makeEntryValid(matcher.group("equipmentName")));
        if (equipmentType == null) return Response.INVALID_EQUIPMENT;
        int availableEngineers = 0;
        for (Unit engineer : soldiers) {
            if (engineer.isAvailable()) availableEngineers++;
        }
        if (soldiers.get(0).getOwner().getWealth() < equipmentType.getCost()) return Response.NOT_ENOUGH_GOLD_EQUIPMENT;
        if (availableEngineers < equipmentType.getEngineerPrice()) return Response.NOT_ENOUGH_ENGINEERS_EQUIPMENT;
        if (GameController.currentGame.getTileByCoordinates(soldiers.get(0).getYCoordinate(),soldiers.get(0).getXCoordinate()).getBuilding() != null)
            return Response.BUILDING_ALREADY_EXIST;
        if (GameController.currentGame.getTileByCoordinates(soldiers.get(0).getYCoordinate(),soldiers.get(0).getXCoordinate()).getEquipment() != null)
            return Response.EXIST_PORTABLE_SHIELD;
        Building siegeTent = new Building(soldiers.get(0).getOwner(), BuildingType.SIEGE_TENT,soldiers.get(0).getXCoordinate(),soldiers.get(0).getYCoordinate());
        siegeTent.getOwner().getBuildings().add(siegeTent);
        GameController.currentGame.getTileByCoordinates(soldiers.get(0).getYCoordinate(),soldiers.get(0).getXCoordinate()).setBuilding(siegeTent);
        siegeTent.setDelay(equipmentType.getDelay());
        siegeTent.setEquipmentType(equipmentType);
        soldiers.get(0).getOwner().addToWealth(-1 * equipmentType.getCost());
        availableEngineers = equipmentType.getEngineerPrice();
        for (Unit engineer : soldiers) {
            if (availableEngineers == 0) break;
            if (engineer.isAvailable()) {
                engineer.setAvailable(false);
                availableEngineers--;
            }
        }
        return Response.EQUIPMENT_BUILT;
    }

    public static Response disband(){
        if (soldiers.get(0).getUnitType() == UnitType.KING) return Response.CANT_DISBAND_KING;
        for (Unit unit : soldiers) {
            currentGame.getTileByCoordinates(unit.getYCoordinate(),unit.getXCoordinate()).removeUnit(unit);
            unit.getOwner().addToPopulation(-1);
            unit.getOwner().removeUnit(unit);
        }
        return Response.DISBAND_SUCCESSFUL;
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
