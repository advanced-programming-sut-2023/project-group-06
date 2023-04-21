package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Gate;
import org.example.Model.BuildingGroups.Towers;
import org.example.View.Response;

import java.util.regex.Matcher;

public class BuildingController {
    public static Building building;

    public static Response createUnit(Matcher matcher){
        String[] groupNames = {"type","count"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        int count;
        if (matcher.group("count") == null) count = 1;
        else count = Integer.parseInt(Controller.makeEntryValid(matcher.group("count")));
        UnitType type = UnitType.getSoldierTypeByString(Controller.makeEntryValid(matcher.group("type")));
        if (type == null) return Response.INVALID_SOLDIER_TYPE;
        if (count <= 0) return Response.INVALID_COUNT;
        if (type.getCost() * count > GameController.currentPlayer.getWealth()) return Response.NOT_ENOUGH_GOLD_UNIT;
        int numberOfWeapons = (type.getWeapon() != null) ? GameController.currentPlayer.getWeaponAmountByType(type.getWeapon()) : 0;
        int numberOfWeaponsNeeded = (type.getWeapon() != null) ? count : 0;
        if (numberOfWeaponsNeeded < numberOfWeapons) return Response.NOT_ENOUGH_WEAPON_UNIT;
        if (GameController.currentPlayer.getMaxPopulation() - count < GameController.currentPlayer.getPopulation())
            return Response.NOT_ENOUGH_PEASANT;
        Response response;
        if (building.getBuildingType() == BuildingType.BARRACKS) response = createUnitBarracks(type, count);
        else if (building.getBuildingType() == BuildingType.MERCENARY_POST) response = createUnitMercenaryPost(type, count);
        else if (building.getBuildingType() == BuildingType.ENGINEERS_GUILD) response = createUnitEngineerGuild(type, count);
        else return Response.CANT_CREATE_ANY_UNIT_IN_BUILDING;
        if (response == Response.UNIT_CREATED_SUCCESSFULLY) {
            building.getOwner().addToWealth(-1 * type.getCost() * count);
            if (type.getWeapon() != null) building.getOwner().useWeaponToCreateUnit(new Weapon(numberOfWeaponsNeeded,type.getWeapon()));
        }
        return response;
    }

    public static Response repair(){
        int x = building.getXCoordinate();
        int y = building.getYCoordinate();
        if (building.getBuildingType().getResourcesPrice().getType() != ResourcesType.STONE
                && !building.getBuildingType().getName().equals("small stone gatehouse")) return Response.NOT_REPAIRABLE;
        if (existEnemyNearTile(y,x)) return Response.EXIST_ENEMY_NEAR_TILE;
        if (showBuildingHp() == building.getHitPoint()) return Response.FULL_HIT_POINT;
        building.setHitPoint(building.getBuildingType().getHitPoint());
        return Response.REPAIRED;
    }

    private static boolean existEnemyNearTile(int y, int x) {
        for (int yy = y - 4; yy <= y + 4; y++) {
            for (int xx = x - 4; xx <= x + 4; xx++) {
                if (existEnemyOnThisTile(yy,xx)) return true;
            }
        }
        return false;
    }

    private static boolean existEnemyOnThisTile(int y, int x) {
        if (GameController.currentGame.getMapWidth() <= y || y < 0) return false;
        if (GameController.currentGame.getMapHeight() <= x || x < 0) return false;
        return GameController.currentGame.getTileByCoordinates(y,x).existEnemyOnThisTile(building.getOwner());
    }

    private static Response createUnitBarracks(UnitType type, int count) {
        if (type.isArab() || type.getName().equals("king") || type.getName().equals("engineer")) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), type);
        }
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createUnitMercenaryPost(UnitType type, int count) {
        if (!type.isArab()) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), type);
        }
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createUnitEngineerGuild(UnitType type, int count) {
        if (!type.getName().equals("engineer")) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        building.getOwner().addEngineers(count);
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    public static int showBuildingHp() {
        return building.getHitPoint();
    }
}
