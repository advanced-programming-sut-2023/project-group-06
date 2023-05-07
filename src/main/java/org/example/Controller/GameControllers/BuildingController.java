package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.Model.BuildingGroups.*;
import org.example.View.Response;

import java.util.regex.Matcher;

public class BuildingController {
    public static Building building;

    // TODO
    //  KILLING COWS FOR LEATHER ARMOR

    public static Response createUnit(Matcher matcher) {
        matcher.find();
        String[] groupNames = {"type", "count"};
        String nullGroupName = Controller.nullGroup(matcher, groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        int count;
        if (matcher.group("count") == null) count = 1;
        else count = Integer.parseInt(Controller.makeEntryValid(matcher.group("count")));
        UnitType type = UnitType.getSoldierTypeByString(Controller.makeEntryValid(matcher.group("type")));
        if (type == null) return Response.INVALID_SOLDIER_TYPE;
        if (count <= 0) return Response.INVALID_COUNT;
        Response response;
        if (type.getCost() * count > GameController.currentPlayer.getWealth()) return Response.NOT_ENOUGH_GOLD_UNIT;
        int numberOfWeapons = (type.getWeapon() != null) ? GameController.currentPlayer.getWeaponAmountByType(type.getWeapon()) : 0;
        int numberOfWeaponsNeeded = (type.getWeapon() != null) ? count : 0;
        int numberOfWeapons2 = (type.getWeapon2() != null) ? GameController.currentPlayer.getWeaponAmountByType(type.getWeapon2()) : 0;
        int numberOfWeapons2Needed = (type.getWeapon2() != null) ? count : 0;
        if ((numberOfWeaponsNeeded > numberOfWeapons || numberOfWeapons2Needed > numberOfWeapons2) && type != UnitType.OIL_ENGINEER)
            return Response.NOT_ENOUGH_WEAPON_UNIT;
        if (building.getBuildingType() == BuildingType.BARRACKS) response = createUnitBarracks(type, count);
        else if (building.getBuildingType() == BuildingType.MERCENARY_POST)
            response = createUnitMercenaryPost(type, count);
        else if (building.getBuildingType() == BuildingType.ENGINEERS_GUILD)
            response = createUnitEngineerGuild(type, count);
        else if (building.getBuildingType() == BuildingType.CATHEDRAL) response = createUnitCathedral(type, count);
        else if (building.getBuildingType() == BuildingType.OIL_SMELTER) response = createUnitOilSmelter(type, count);
        else response = Response.CANT_CREATE_ANY_UNIT_IN_BUILDING;
        return response;
    }

    public static Response repair(){
        int x = building.getXCoordinate();
        int y = building.getYCoordinate();
        if (building.getBuildingType().getResourcesPrice().getType() != ResourcesType.STONE)
            return Response.NOT_REPAIRABLE;
        if (building.getBuildingType().getResourcesPrice().getAmount() > building.getOwner().getResourceAmountByType(ResourcesType.STONE)) return Response.NOT_ENOUGH_RESOURCES;
        if (existEnemyNearTile(y, x)) return Response.EXIST_ENEMY_NEAR_TILE;
        if (showBuildingHp() == building.getHitPoint()) return Response.FULL_HIT_POINT;
        building.getOwner().payResource(new Resources(building.getBuildingType().getResourcesPrice().getAmount(),ResourcesType.STONE));
        building.setHitPoint(building.getBuildingType().getHitPoint());
        return Response.REPAIRED;
    }

    public static Response createWeapon(Matcher matcher) {
        matcher.find();
        String[] groupNames = {"type", "count"};
        String nullGroupName = Controller.nullGroup(matcher, groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        int count;
        if (matcher.group("count") == null) count = 1;
        else count = Integer.parseInt(Controller.makeEntryValid(matcher.group("count")));
        WeaponType type = WeaponType.getWeaponTypeByString(Controller.makeEntryValid(matcher.group("type")));
        if (type == null) return Response.INVALID_WEAPON_TYPE;
        if (count <= 0) return Response.INVALID_COUNT;
        if (type.getResourcePrice() != null) {
            if (building.getOwner().getResourceAmountByType(type.getResourcesPriceType()) < count * type.getResourcePriceAmount())
                return Response.NOT_ENOUGH_RESOURCES_WEAPON;
            else {
                if (building.getBuildingType() == BuildingType.FLETCHER) return createWeaponFletcher(type, count);
                if (building.getBuildingType() == BuildingType.BLACKSMITH) return createWeaponBlacksmith(type, count);
                if (building.getBuildingType() == BuildingType.POLETURNER) return createWeaponPoleturner(type, count);
                if (building.getBuildingType() == BuildingType.ARMORER) return createWeaponArmorer(type, count);
            }
        } else if (type.getBuildingType() == BuildingType.DIARY_FARMER) return createWeaponDiaryFarmer(type, count);
        return Response.CANT_CREATE_ANY_WEAPON_BUILDING;
    }

    private static boolean existEnemyNearTile(int y, int x) {
        for (int yy = y - 4; yy <= y + 4; y++) {
            for (int xx = x - 4; xx <= x + 4; xx++) {
                if (existEnemyOnThisTile(yy, xx)) return true;
            }
        }
        return false;
    }

    private static boolean existEnemyOnThisTile(int y, int x) {
        if (GameController.currentGame.getMapWidth() <= y || y < 0) return false;
        if (GameController.currentGame.getMapHeight() <= x || x < 0) return false;
        return GameController.currentGame.getTileByCoordinates(y, x).existEnemyOnThisTile(building.getOwner());
    }

    private static Response createUnitBarracks(UnitType type, int count) {
        if (GameController.currentPlayer.getMaxPopulation() - GameController.currentPlayer.getPopulation() - GameController.currentPlayer.getAvailableEngineers() < count)
            return Response.NOT_ENOUGH_PEASANT;
        if (type.isArab() || type.getName().equals("king") || type.getName().equals("engineer") || type.getName().equals("oil engineer") || type.getName().equals("dog"))
            return Response.CANT_CREATE_UNIT_IN_BUILDING;
        if ((type == UnitType.KNIGHT || type == UnitType.HORSE_ARCHER) && building.getOwner().getHorseNumber() < count)
            return Response.NOT_ENOUGH_HORSES;
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), type);
            GameController.currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()).addSoldier(soldier);
            if (type == UnitType.KNIGHT || type == UnitType.HORSE_ARCHER) building.getOwner().takeHorseFromStable();
        }
        building.getOwner().addToWealth(-1 * type.getCost() * count);
        if (type.getWeapon() != null) building.getOwner().useWeaponToCreateUnit(new Weapon(count, type.getWeapon()));
        if (type.getWeapon2() != null) building.getOwner().useWeaponToCreateUnit(new Weapon(count, type.getWeapon()));
        building.getOwner().addToPopulation(count);
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createUnitMercenaryPost(UnitType type, int count) {
        if (!type.isArab()) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), type);
            GameController.currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()).addSoldier(soldier);
        }
        building.getOwner().addToWealth(-1 * type.getCost() * count);
        if (type.getWeapon() != null) building.getOwner().useWeaponToCreateUnit(new Weapon(count, type.getWeapon()));
        if (type.getWeapon2() != null) building.getOwner().useWeaponToCreateUnit(new Weapon(count, type.getWeapon()));
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createUnitEngineerGuild(UnitType type, int count) {
        if (GameController.currentPlayer.getMaxPopulation() - GameController.currentPlayer.getPopulation() - GameController.currentPlayer.getAvailableEngineers() < count)
            return Response.NOT_ENOUGH_PEASANT;
        if (!type.getName().equals("engineer")) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        if (((Storage) building).getCapacity() - ((Storage) building).getStored() < count)
            return Response.NOT_ENOUGH_SPACE;
        building.getOwner().addToWealth(-1 * type.getCost() * count);
        for (int i = 0; i < count; i++) {
            Unit engineer = new Unit(building.getXCoordinate(),building.getYCoordinate(),building.getOwner(),UnitType.ENGINEER);
            GameController.currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()).addUnit(engineer);
        }
        ((Storage) building).addToStored(count);
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createUnitCathedral(UnitType type, int count) {
        if (GameController.currentPlayer.getMaxPopulation() - count < GameController.currentPlayer.getPopulation())
            return Response.NOT_ENOUGH_PEASANT;
        if (!type.getName().equals("black monk")) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), type);
            GameController.currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()).addSoldier(soldier);
        }
        building.getOwner().addToWealth(-1 * type.getCost() * count);
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createUnitOilSmelter(UnitType type, int count) {
        if (GameController.currentPlayer.getMaxPopulation() - count < GameController.currentPlayer.getPopulation())
            return Response.NOT_ENOUGH_PEASANT;
        if (!type.getName().equals("oil engineer")) return Response.CANT_CREATE_UNIT_IN_BUILDING;
        if (building.getOwner().getAvailableEngineers() < count) return Response.NOT_ENOUGH_ENGINEERS;
        if (((Producers) building).getStored() < count) return Response.NOT_ENOUGH_OIL;
        building.getOwner().payEngineer(count);
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), UnitType.OIL_ENGINEER);
            soldier.setHasOil(true);
            GameController.currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()).addSoldier(soldier);
        }
        ((Producers) building).addToStored(-1 * count);
        building.getOwner().addToWealth(-1 * type.getCost() * count);
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    private static Response createWeaponFletcher(WeaponType type, int count) {
        if (type.getBuildingType() != BuildingType.FLETCHER) return Response.WEAPON_BUILDING_MISMATCHING;
        building.getOwner().addAsset(new Weapon(count, type));
        building.getOwner().payResource(new Resources(count * type.getResourcePriceAmount(), type.getResourcesPriceType()));
        return Response.WEAPON_CREATED;
    }

    private static Response createWeaponPoleturner(WeaponType type, int count) {
        if (type.getBuildingType() != BuildingType.POLETURNER) return Response.WEAPON_BUILDING_MISMATCHING;
        building.getOwner().addAsset(new Weapon(count, type));
        building.getOwner().payResource(new Resources(count * type.getResourcePriceAmount(), type.getResourcesPriceType()));
        return Response.WEAPON_CREATED;
    }

    private static Response createWeaponBlacksmith(WeaponType type, int count) {
        if (type.getBuildingType() != BuildingType.BLACKSMITH) return Response.WEAPON_BUILDING_MISMATCHING;
        building.getOwner().addAsset(new Weapon(count, type));
        building.getOwner().payResource(new Resources(count * type.getResourcePriceAmount(), type.getResourcesPriceType()));
        return Response.WEAPON_CREATED;
    }

    private static Response createWeaponArmorer(WeaponType type, int count) {
        if (type.getBuildingType() != BuildingType.ARMORER) return Response.WEAPON_BUILDING_MISMATCHING;
        building.getOwner().addAsset(new Weapon(count, type));
        building.getOwner().payResource(new Resources(count * type.getResourcePriceAmount(), type.getResourcesPriceType()));
        return Response.WEAPON_CREATED;
    }

    private static Response createWeaponDiaryFarmer(WeaponType type, int count) {
        if (type.getBuildingType() != BuildingType.DIARY_FARMER) return Response.WEAPON_BUILDING_MISMATCHING;
        building.getOwner().addAsset(new Weapon(count, type));
        if (!building.getOwner().killCow(count)) return Response.NOT_ENOUGH_COWS;
        return Response.WEAPON_CREATED;
    }

    public static int showBuildingHp() {
        return building.getHitPoint();
    }

    public static boolean isCastleType() {
        if (building.getBuildingType().getResourcesPrice().getType() != ResourcesType.STONE) return false;
        return true;

    }

    public static boolean isCagedWarDogs() {
        return building.getBuildingType() == BuildingType.CAGED_WAR_DOGS;
    }

    public static Response releaseDogs() {
        if (((Storage) building).getStored() == 0) return Response.NO_DOG_RELEASE;
        String direction = "w";
        int x = building.getXCoordinate();
        int y = building.getYCoordinate();
        int destinationX = directionX(x, direction);
        int destinationY = directionY(y, direction);
        Tile destination = GameController.currentGame.getTileByCoordinates(destinationY, destinationX);
        Tile curTile = GameController.currentGame.getTileByCoordinates(y, x);
        if (isDirectionOutOfBoundaries(direction, building.getYCoordinate(), building.getXCoordinate()))
            return Response.OUT_OF_BOUNDARIES;
        if (destination.getBuilding() != null) return Response.CAGE_BLOCKED;
        for (int i = GameController.currentGame.getTileByCoordinates(y, x).getSoldiers().size() - 1; i >= 0; i--) {
            Soldier soldier = GameController.currentGame.getTileByCoordinates(y, x).getSoldiers().get(i);
            curTile.removeSoldier(soldier);
            destination.addSoldier(soldier);
        }
        return Response.DOGS_RELEASED;
    }

    private static boolean isDirectionOutOfBoundaries(String direction, int y, int x) {
        if (direction.equals("n") && y <= 1) return true;
        if (direction.equals("e") && x <= 1) return true;
        if (direction.equals("w") && x + 2 >= GameController.currentGame.getMapWidth()) return true;
        if (direction.equals("s") && y + 2 >= GameController.currentGame.getMapHeight()) return true;
        return false;
    }

    private static int directionX(int x, String direction) {
        if (direction.equals("e")) return x + 2;
        if (direction.equals("w")) return x - 2;
        return x;
    }

    private static int directionY(int y, String direction) {
        if (direction.equals("n")) return y - 2;
        if (direction.equals("s")) return y + 2;
        return y;
    }
}
