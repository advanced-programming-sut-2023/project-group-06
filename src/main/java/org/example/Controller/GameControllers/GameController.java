package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Controller.PathFinder;
import org.example.Model.*;
import org.example.Model.BuildingGroups.*;
import org.example.View.Response;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Matcher;

public class GameController {
    public static Game currentGame;
    public static Kingdom currentPlayer;
    //use make entry valid everywhere

    public static Response initializeMap(Matcher matcher){
        String widthString = matcher.group("width");
        String heightString = matcher.group("height");
        int width = Integer.parseInt(widthString);
        int height = Integer.parseInt(heightString);
        currentGame.initializeMap(width, height);
        return Response.INITIALIZE_MAP_SUCCESSFUL;
    }

    public static Response setDefaultMap(String mapName){
        Tile[][] defaultMap = Data.loadMap(mapName);
        if(defaultMap == null)
            return Response.NO_SUCH_MAP;
        int defaultMapWidth = Objects.requireNonNull(Data.loadMap(mapName)).length;
        int defaultMapHeight = Objects.requireNonNull(Data.loadMap(mapName))[0].length;
        currentGame.setMap(defaultMap, defaultMapWidth, defaultMapHeight);
        return Response.MAP_IS_SET;
    }

    public static Response clearBlock(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getOwner() == currentPlayer){
            if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.MAIN_CASTLE)
                return Response.CLEAR_MAIN_CASTLE;
            if(x == currentPlayer.getKing().getXCoordinate() && y == currentPlayer.getKing().getYCoordinate())
                return Response.CANT_REMOVE_THE_KING;
            currentPlayer.removeBuilding(currentGame.getTileByCoordinates(y, x).getBuilding());
            if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.GRANARY)
                currentPlayer.getFoods().remove((Storage) (currentGame.getTileByCoordinates(y, x).getBuilding()));
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.ARMORY)
                currentPlayer.getWeapons().remove((Storage) (currentGame.getTileByCoordinates(y, x).getBuilding()));
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.STOCKPILE)
                currentPlayer.getResources().remove((Storage) (currentGame.getTileByCoordinates(y, x).getBuilding()));
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.CHURCH)
                currentPlayer.addToHappinessIncrease(-2);
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.CATHEDRAL)
                currentPlayer.addToHappinessIncrease(-4);
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.DRAWBRIDGE &&
                    ((Gate) currentGame.getTileByCoordinates(y, x).getBuilding()).isOpen())
                return Response.CLOSE_THE_GATE_FIRST;
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.QUARRY)
                currentPlayer.getQuarries().remove((Producers) currentGame.getTileByCoordinates(y, x).getBuilding());
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.INN)
                currentPlayer.getInns().remove((Producers) currentGame.getTileByCoordinates(y, x).getBuilding());
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.ENGINEERS_GUILD)
                currentPlayer.getEngineerGuilds().remove((Storage) currentGame.getTileByCoordinates(y, x).getBuilding());
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.STABLE)
                currentPlayer.getStables().remove((Storage) currentGame.getTileByCoordinates(y, x).getBuilding());
            else if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.OIL_SMELTER)
                currentPlayer.getOilSmelter().remove((Producers) currentGame.getTileByCoordinates(y, x).getBuilding());
            int xCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getXCoordinate();
            int yCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getYCoordinate();
            int size = (currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().getSize() - 1) / 2;
            for(int i = xCenter - size; i <= xCenter + size; i++){
                for(int j = yCenter - size; j <= yCenter + size; j++){
                    currentGame.getTileByCoordinates(j, i).setBuilding(null);
                    setGroundBack(currentGame.getTileByCoordinates(j, i));
                }
            }
        }
        for(int i = 0; i < currentGame.getTileByCoordinates(y, x).getAllUnits().size(); i++){
            Unit unit = currentGame.getTileByCoordinates(y, x).getAllUnits().get(i);
            if(unit.getOwner() == currentPlayer){
                currentGame.getTileByCoordinates(y, x).removeUnit(unit);
                currentPlayer.removeUnit(unit);
                currentPlayer.addToPopulation(-1);
            }
        }
        return Response.CLEAR_SUCCESSFUL;
    }

    public static Response dropUnit(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String typeString = Controller.makeEntryValid(matcher.group("type"));
        int count = Integer.parseInt(matcher.group("count"));
        UnitType type = UnitType.getSoldierTypeByString(typeString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(type == null)
            return Response.INVALID_TYPE;
        if (count <= 0)
            return Response.INVALID_COUNT;
        if(!currentGame.getTileByCoordinates(y, x).getType().CanBeCrossed())
            return Response.INVALID_GROUND;
        if (type.getCost() * count > GameController.currentPlayer.getWealth())
            return Response.NOT_ENOUGH_GOLD_UNIT;
        int numberOfWeapons = (type.getWeapon() != null) ? GameController.currentPlayer.getWeaponAmountByType(type.getWeapon()) : 0;
        int numberOfWeaponsNeeded = (type.getWeapon() != null) ? count : 0;
        int numberOfWeapons2 = (type.getWeapon2() != null) ? GameController.currentPlayer.getWeaponAmountByType(type.getWeapon2()) : 0;
        int numberOfWeapons2Needed = (type.getWeapon2() != null) ? count : 0;
        if ((numberOfWeaponsNeeded > numberOfWeapons || numberOfWeapons2Needed > numberOfWeapons2) && type != UnitType.OIL_ENGINEER)
            return Response.NOT_ENOUGH_WEAPON_UNIT;
        if (GameController.currentPlayer.getMaxPopulation() - GameController.currentPlayer.getPopulation() - GameController.currentPlayer.getAvailableEngineers() < count)
            return Response.NOT_ENOUGH_PEASANT;
        if ((type == UnitType.KNIGHT || type == UnitType.HORSE_ARCHER) && currentPlayer.getHorseNumber() < count) return Response.NOT_ENOUGH_HORSES;
        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(x, y, currentPlayer, type);
            GameController.currentGame.getTileByCoordinates(y, x).addSoldier(soldier);
            if (type == UnitType.KNIGHT || type == UnitType.HORSE_ARCHER) currentPlayer.takeHorseFromStable();
        }
        currentPlayer.addToWealth(-1 * type.getCost() * count);
        if (type.getWeapon() != null) currentPlayer.useWeaponToCreateUnit(new Weapon(numberOfWeaponsNeeded,type.getWeapon()));
        if (type.getWeapon2() != null) currentPlayer.useWeaponToCreateUnit(new Weapon(numberOfWeapons2Needed,type.getWeapon2()));
        if(!type.isArab())
            currentPlayer.addToPopulation(count);
        return Response.UNIT_CREATED_SUCCESSFULLY;
    }

    public static Response dropRuck(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String directionString = matcher.group("direction");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int direction = getDirection(directionString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(!BuildingType.checkGround(BuildingType.ROCK, currentGame.getTileByCoordinates(y, x).getType()))
            return Response.INVALID_GROUND;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.BUILDING_ALREADY_EXIST;
        if(currentGame.getTileByCoordinates(y, x).getAllUnits().size() > 0)
            return Response.CANT_PUT_THIS_ON_TROOPS;
        Building rock = new Building(null, BuildingType.ROCK, x, y, direction);
        currentGame.getTileByCoordinates(y, x).setBuilding(rock);
        return Response.DROP_ROCK_SUCCESSFUL;
    }

    public static Response dropTree(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String typeString = matcher.group("type");
        String treeType = Controller.makeEntryValid(typeString);
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        TreeType type = TreeType.getTreeTypeByString(treeType);
        if(type == null)
            return Response.INVALID_TYPE;
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(!BuildingType.checkGround(BuildingType.TREE, currentGame.getTileByCoordinates(y, x).getType()))
            return Response.INVALID_GROUND;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.BUILDING_ALREADY_EXIST;
        if(currentGame.getTileByCoordinates(y, x).getAllUnits().size() > 0)
            return Response.CANT_PUT_THIS_ON_TROOPS;
        Tree tree = new Tree(x, y, type);
        currentGame.getTileByCoordinates(y, x).setBuilding(tree);
        currentGame.getTrees().add(tree);
        return Response.DROP_TREE_SUCCESSFUL;
    }

    public static Response setTextureOneTile(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String typeString = matcher.group("type");
        String tileType = Controller.makeEntryValid(typeString);
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        TileStructure type = TileStructure.getTileStructureByString(tileType);
        if(type == null)
            return Response.INVALID_TYPE;
        int size = 0;
        if(type == TileStructure.SMALL_LAKE)
            size = 1;
        else if(type == TileStructure.BIG_LAKE)
            size = 2;
        if(x - size < 0 || x + size >= currentGame.getMapWidth() || y - size < 0 || y + size >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        for(int i = x - size; i <= x + size; i++){
            for(int j = y - size; j <= y + size; j++){
                if(currentGame.getTileByCoordinates(j, i).getAllUnits().size() > 0)
                    return Response.TEXTURE_UNDER_UNIT;
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null) {
                    if(currentGame.getTileByCoordinates(j, i).getBuilding().getBuildingType() == BuildingType.TREE)
                        return Response.SET_TEXTURE_UNDER_TREE;
                    if(currentGame.getTileByCoordinates(j, i).getBuilding().getBuildingType() == BuildingType.ROCK)
                        return Response.SET_TEXTURE_UNDER_ROCK;
                    return Response.SET_TEXTURE_UNDER_BUILDING;
                }
                currentGame.getTileByCoordinates(j, i).setType(type);
            }
        }
        return Response.SET_TEXTURE_SUCCESSFUL;
    }

    public static Response setTextureMultipleTiles(Matcher matcher){
        String x1String = matcher.group("x1");
        String x2String = matcher.group("x2");
        String y1String = matcher.group("y1");
        String y2String = matcher.group("y2");
        String typeString = matcher.group("type");
        String tileType = Controller.makeEntryValid(typeString);
        int x1 = Integer.parseInt(x1String);
        int x2 = Integer.parseInt(x2String);
        int y1 = Integer.parseInt(y1String);
        int y2 = Integer.parseInt(y2String);
        TileStructure type = TileStructure.getTileStructureByString(tileType);
        if(type == null || type == TileStructure.SMALL_LAKE || type == TileStructure.BIG_LAKE)
            return Response.INVALID_TYPE;
        if(x1 > x2 || y1 > y2 || x1 < 0 || y1 < 0 || x2 >= currentGame.getMapWidth() || y2 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        for(int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null) {
                    if(currentGame.getTileByCoordinates(j, i).getBuilding().getBuildingType() == BuildingType.TREE)
                        return Response.SET_TEXTURE_UNDER_TREE;
                    if(currentGame.getTileByCoordinates(j, i).getBuilding().getBuildingType() == BuildingType.ROCK)
                        return Response.SET_TEXTURE_UNDER_ROCK;
                    return Response.SET_TEXTURE_UNDER_BUILDING;
                }
                if(currentGame.getTileByCoordinates(j, i).getAllUnits().size() > 0)
                    return Response.TEXTURE_UNDER_UNIT;
            }
        }
        for(int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                currentGame.getTileByCoordinates(j, i).setType(type);
            }
        }
        return Response.SET_TEXTURE_SUCCESSFUL;
    }

    public static Response dropBuilding(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String typeString = matcher.group("type");
        String type = Controller.makeEntryValid(typeString);
        String direction = Controller.makeEntryValid(matcher.group("direction"));
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        BuildingType buildingtype = BuildingType.getBuildingTypeByString(type);
        if(buildingtype == null || buildingtype == BuildingType.TREE ||
                buildingtype == BuildingType.ROCK || buildingtype == BuildingType.STAIR)
            return Response.INVALID_TYPE;
        if(buildingtype.getBuildingClass() == Gate.class && direction == null)
            return Response.ENTER_DIRECTION;
        int size = (buildingtype.getSize() - 1) / 2;
        if(x - size < 0 || x + size >= currentGame.getMapWidth() || y - size < 0 || y + size >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        for(int i = x - size; i <= x + size; i++){
            for(int j = y - size; j <= y + size; j++){
                if(!BuildingType.checkGround(buildingtype, currentGame.getTileByCoordinates(j, i).getType()))
                    return Response.INVALID_GROUND;
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null) {
                    if (currentGame.getTileByCoordinates(j, i).getBuilding() instanceof Trap &&
                            currentGame.getTileByCoordinates(j, i).getBuilding().getOwner() != currentPlayer)
                        ((Trap) currentGame.getTileByCoordinates(j, i).getBuilding()).setCanBeSeenByEnemy(true);
                    return Response.BUILDING_ALREADY_EXIST;
                }
                if(currentGame.getTileByCoordinates(j, i).getAllUnits().size() > 0)
                    return Response.CANT_PUT_THIS_ON_TROOPS;
            }
        }
        if(currentPlayer.getMaxPopulation() - currentPlayer.getPopulation() - currentPlayer.getAvailableEngineers() < buildingtype.getWorkerPrice())
            return Response.NOT_ENOUGH_PEASANT;
        if(currentPlayer.getAvailableEngineers() < buildingtype.getEngineerPrice())
            return Response.NOT_ENOUGH_ENGINEERS;
        if(currentPlayer.getWealth() < buildingtype.getGoldPrice())
            return Response.NOT_ENOUGH_MONEY;
        if(currentPlayer.getResourceAmountByType(buildingtype.getResourcesPrice().getType()) < buildingtype.getResourcesPrice().getAmount())
            return Response.NOT_ENOUGH_RESOURCES;
        Building building = null;
        if (Producers.class.equals(buildingtype.getBuildingClass())) {
            building = new Producers(currentPlayer, buildingtype, x, y);
            switch (buildingtype) {
                case QUARRY:
                    currentPlayer.getQuarries().add((Producers) building);
                    break;
                case OIL_SMELTER:
                    currentPlayer.getOilSmelter().add((Producers) building);
                    break;
                case INN:
                    currentPlayer.getInns().add((Producers) building);
                    break;
            }
        }
        else if(Gate.class.equals(buildingtype.getBuildingClass()))
            building = new Gate(currentPlayer, buildingtype, x, y, getDirection(direction));
        else if(Storage.class.equals(buildingtype.getBuildingClass())) {
            building = new Storage(currentPlayer, buildingtype, x, y);
            if(!IsAdjacentToStorages(x, y, buildingtype))
                return Response.ADJACENT_STORAGES;
            switch (buildingtype){
                case GRANARY:
                    currentPlayer.getFoods().add((Storage) building);
                    break;
                case STOCKPILE:
                    currentPlayer.getResources().add((Storage) building);
                    break;
                case ARMORY:
                    currentPlayer.getWeapons().add((Storage) building);
                    break;
                case STABLE:
                    currentPlayer.getStables().add((Storage) building);
                    break;
                case ENGINEERS_GUILD:
                    currentPlayer.getEngineerGuilds().add((Storage) building);
                    break;
            }
        }
        else if(Towers.class.equals(buildingtype.getBuildingClass()))
            building = new Towers(currentPlayer, buildingtype, x, y);
        else if(Trap.class.equals(buildingtype.getBuildingClass()))
            building = new Trap(currentPlayer, buildingtype, x, y);
        else building  = new Building(currentPlayer, buildingtype, x, y);
        currentPlayer.payResource(buildingtype.getResourcesPrice());
        if (building.getBuildingType() == BuildingType.CAGED_WAR_DOGS) {
            for (int i = 0; i < 4; i++) {
                Soldier soldier = new Soldier(x,y,currentPlayer,UnitType.DOG);
                currentGame.getTileByCoordinates(y,x).addSoldier(soldier);
            }
        }
        for(int i = x - size; i <= x + size; i++){
            for(int j = y - size; j <= y + size; j++){
                currentGame.getTileByCoordinates(j, i).setBuilding(building);
            }
        }
        currentPlayer.getBuildings().add(building);
        currentPlayer.addToWealth(-1 * buildingtype.getGoldPrice());
        currentPlayer.payEngineer(buildingtype.getEngineerPrice(), building.getYCoordinate(), building.getXCoordinate());
        currentPlayer.addToPopulation(buildingtype.getWorkerPrice() + buildingtype.getEngineerPrice());
        if(buildingtype == BuildingType.CHURCH)
            currentPlayer.addToHappinessIncrease(2);
        if(buildingtype == BuildingType.CATHEDRAL)
            currentPlayer.addToHappinessIncrease(4);
        if(buildingtype == BuildingType.HOVEL || buildingtype == BuildingType.SMALL_STONE_GATEHOUSE) {
            currentPlayer.addToMaxPopulation(8);
            if(buildingtype == BuildingType.SMALL_STONE_GATEHOUSE && currentPlayer.getTax() < 8)
                currentPlayer.setTax(currentPlayer.getTax() + 1);
        }
        if(buildingtype == BuildingType.BIG_STONE_GATEHOUSE)
            currentPlayer.addToMaxPopulation(10);
        return Response.DROP_BUILDING_SUCCESSFUL;
    }

    public static Response dropStair(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String direction = Controller.makeEntryValid(matcher.group("direction"));
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(direction == null)
            return Response.ENTER_DIRECTION;
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(!BuildingType.checkGround(BuildingType.STAIR, currentGame.getTileByCoordinates(y, x).getType()))
            return Response.INVALID_GROUND;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null) {
            if (currentGame.getTileByCoordinates(y, x).getBuilding() instanceof Trap &&
                    currentGame.getTileByCoordinates(y, x).getBuilding().getOwner() != currentPlayer)
                ((Trap) currentGame.getTileByCoordinates(y, x).getBuilding()).setCanBeSeenByEnemy(true);
            return Response.BUILDING_ALREADY_EXIST;
        }
        if(currentGame.getTileByCoordinates(y, x).getAllUnits().size() > 0)
            return Response.CANT_PUT_THIS_ON_TROOPS;
        int stairDirection = getDirection(direction);
        int frontX = x;
        int frontY = y;
        if(stairDirection % 2 == 0) frontY += stairDirection - 1;
        else frontX += 2 - stairDirection;
        if(currentGame.getTileByCoordinates(frontY, frontX).getBuilding() == null ||
                currentGame.getTileByCoordinates(frontY, frontX).getBuilding().getBuildingType() == BuildingType.DRAWBRIDGE ||
                (currentGame.getTileByCoordinates(frontY, frontX).getBuilding().getBuildingType() != BuildingType.WALL &&
                currentGame.getTileByCoordinates(frontY, frontX).getBuilding().getClass() != Gate.class))
            return Response.BUILD_STAIR_NEAR_WALL;
        Building stair = new Building(currentPlayer, BuildingType.STAIR, x, y, stairDirection);
        for(int i = x ; i <= x ; i++){
            for(int j = y ; j <= y ; j++){
                currentGame.getTileByCoordinates(j, i).setBuilding(stair);
            }
        }
        currentPlayer.getBuildings().add(stair);
        return Response.DROP_STAIR_SUCCESSFUL;
    }

    public static Response putMainCastle(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String color = Controller.makeEntryValid(matcher.group("color"));
        String directionString = Controller.makeEntryValid(matcher.group("direction"));
        if(directionString == null)
            return Response.ENTER_DIRECTION;
        int direction = getDirection(directionString);
        //check the colors
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(x - 1 < 0 || x + 1 >= currentGame.getMapWidth() || y - 1 < 0 || y + 1 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                if(!BuildingType.checkGround(BuildingType.MAIN_CASTLE, currentGame.getTileByCoordinates(j, i).getType()))
                    return Response.INVALID_GROUND;
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null)
                    return Response.BUILDING_ALREADY_EXIST;
            }
        }
        int newDirection = (direction + 1) % 4;
        int numberOfLoops = 0;
        while(checkDirection(x, y, newDirection) == null) {
            newDirection = (newDirection + 1) % 4;
            numberOfLoops++;
            if(numberOfLoops == 6)
                return Response.CANT_PUT_STOCKPILE;
        }
        Building mainCastle = new Building(currentPlayer, BuildingType.MAIN_CASTLE, x, y, direction);
        currentPlayer.setMainCastle(mainCastle);
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                currentGame.getTileByCoordinates(j, i).setBuilding(mainCastle);
            }
        }
        currentPlayer.getBuildings().add(mainCastle);
        Soldier king = new Soldier(x, y, currentPlayer, UnitType.KING);
        currentGame.getTileByCoordinates(y,x).addSoldier(king);
        currentPlayer.setKing(king);
        int newX = checkDirection(x, y, newDirection).get(0);
        int newY = checkDirection(x, y, newDirection).get(1);
        Storage stockPile = new Storage(currentPlayer, BuildingType.STOCKPILE, newX, newY);
        stockPile.getAssets().add(new Resources(15, ResourcesType.WOOD));
        stockPile.getAssets().add(new Resources(10, ResourcesType.STONE));
        stockPile.addToStored(25);
        currentGame.getTileByCoordinates(newY, newX).setBuilding(stockPile);
        currentPlayer.getBuildings().add(stockPile);
        currentPlayer.getResources().add(stockPile);
        return Response.DROP_MAIN_CASTLE_SUCCESSFUL;
    }

    private static ArrayList<Integer> checkDirection(int x, int y, int direction){
        switch (direction){
            case 0:
                y -= 2;
                break;
            case 1:
                x += 2;
                break;
            case 2:
                y += 2;
                break;
            case 3:
                x -= 2;
                break;
        }
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight() ||
                currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return null;
        else{
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(x);
            arrayList.add(y);
            return arrayList;
        }
    }

    private static boolean IsAdjacentToStorages(int x, int y, BuildingType buildingType){
        if(buildingType == BuildingType.INN || buildingType == BuildingType.ENGINEERS_GUILD || buildingType == BuildingType.STABLE || buildingType == BuildingType.CAGED_WAR_DOGS)
            return true;
        if(buildingType == BuildingType.GRANARY && currentPlayer.getFoods().size() == 0)
            return true;
        if(buildingType == BuildingType.STOCKPILE && currentPlayer.getResources().size() == 0)
            return true;
        if(buildingType == BuildingType.ARMORY && currentPlayer.getWeapons().size() == 0)
            return true;
        return (y < currentGame.getMapHeight() - 1 &&
                currentGame.getTileByCoordinates(y + 1, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y + 1, x).getBuilding().getBuildingType() == buildingType &&
                currentGame.getTileByCoordinates(y + 1, x).getBuilding().getOwner() == currentPlayer) ||
                (y > 0 && currentGame.getTileByCoordinates(y - 1, x).getBuilding() != null &&
                        currentGame.getTileByCoordinates(y - 1, x).getBuilding().getBuildingType() == buildingType &&
                        currentGame.getTileByCoordinates(y - 1, x).getBuilding().getOwner() == currentPlayer) ||
                (x < currentGame.getMapWidth() - 1 &&
                        currentGame.getTileByCoordinates(y, x + 1).getBuilding() != null &&
                        currentGame.getTileByCoordinates(y, x + 1).getBuilding().getBuildingType() == buildingType &&
                        currentGame.getTileByCoordinates(y, x + 1).getBuilding().getOwner() == currentPlayer) ||
                (x > 0 && currentGame.getTileByCoordinates(y, x - 1).getBuilding() != null &&
                        currentGame.getTileByCoordinates(y, x - 1).getBuilding().getBuildingType() == buildingType &&
                        currentGame.getTileByCoordinates(y, x - 1).getBuilding().getOwner() == currentPlayer);
    }

    private static int getDirection(String directionString){
        int direction = 0;
        switch (directionString){
            case "e":
                direction = 1;
                break;
            case "s":
                direction = 2;
                break;
            case "w":
                direction = 3;
                break;
            case "r":
                direction = (int)(Math.random() * 10) % 4;
                break;
        }
        return direction;
    }

    public static Response selectBuilding(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(x < 0 || y < 0 || x >= currentGame.getMapWidth() || y >= currentGame.getMapHeight())
            return Response.INVALID_INPUT;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() == null)
            return Response.THERE_IS_NO_BUILDING_HERE;
        if(currentGame.getTileByCoordinates(y, x).getBuilding().getOwner() != currentPlayer)
            return Response.YOU_CANT_SELECT_THIS_BUILDING;
        if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.MARKET) {
            BuildingController.building = currentGame.getTileByCoordinates(y, x).getBuilding();
            return Response.SELECT_BUILDING_SUCCESSFUL;
        }
        else{
            ShopController.currentKingdom = currentPlayer;
            return Response.ENTER_SHOP_MENU;
        }
    }

    public static Response selectUnit(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String typeString = Controller.makeEntryValid(matcher.group("type"));
        if(x < 0 || y < 0 || x >= currentGame.getMapWidth() || y >= currentGame.getMapHeight())
            return Response.INVALID_INPUT;
        UnitType type = UnitType.getSoldierTypeByString(typeString);
        if(type == null || type == UnitType.COW || type == UnitType.DOG)
            return Response.INVALID_TYPE;
        ArrayList<Unit> units = new ArrayList<>();
        for(Unit unit : currentGame.getTileByCoordinates(y, x).getAllUnits()){
            if(unit.getOwner() == currentPlayer && unit.getUnitType() == type)
                units.add(unit);
        }
        if(units.size() == 0)
            return Response.NO_UNITS_WITH_THAT_TYPE;
        /*for(Unit unit : units) {
            if(unit instanceof Soldier && ((Soldier) unit).isSaidToPatrol()) {
                ((Soldier) unit).setSaidToPatrol(false);
                unit.setWishPlace(currentGame.getTileByCoordinates(unit.getYCoordinate(), unit.getXCoordinate()));
            }
        }*/
        SoldierController.soldiers = units;
        SoldierController.currentGame = currentGame;
        return Response.SELECT_SOLDIER_SUCCESSFUL;
    }

    private static Response openTheGate(Gate gate, int x, int y, int frontX, int frontY){
        currentGame.getTileByCoordinates(y, x).setHeight(0);
        if(gate.getBuildingType() != BuildingType.DRAWBRIDGE) {
            currentGame.getTileByCoordinates(2 * y - frontY, 2 * x - frontX).setHeight(0);
            currentGame.getTileByCoordinates(frontY, frontX).setHeight(0);
            ArrayList<Soldier> soldiersOnGate = new ArrayList<>();
            if(gate.getBuildingType() == BuildingType.BIG_STONE_GATEHOUSE){
                currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).setHeight(0);
                currentGame.getTileByCoordinates(3 * y - frontY, 3 * x - frontX).setHeight(0);
                soldiersOnGate.addAll(currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).getSoldiers());
                soldiersOnGate.addAll(currentGame.getTileByCoordinates(3 * y - frontY, 3 * x - frontX).getSoldiers());
            }
            //frontY - y + x0
            //frontX - x + y0
            soldiersOnGate.addAll(currentGame.getTileByCoordinates(y, x).getSoldiers());
            soldiersOnGate.addAll(currentGame.getTileByCoordinates(2 * y - frontY, 2 * x - frontX).getSoldiers());
            soldiersOnGate.addAll(currentGame.getTileByCoordinates(frontY, frontX).getSoldiers());
            for(Soldier soldier : soldiersOnGate){
                Tile place = currentGame.getTileByCoordinates(soldier.getYCoordinate(), soldier.getXCoordinate());
                Tile target = currentGame.getTileByCoordinates(soldier.getYCoordinate() + frontX - x,
                        soldier.getXCoordinate() + frontY - y);
                place.removeSoldier(soldier);
                target.addSoldier(soldier);
                soldier.setYCoordinate(target.getYCoordinate());
                soldier.setXCoordinate(target.getXCoordinate());
            }
        }
        else {
            if (3 * frontX - 2 * x < 0 || 3 * frontX - 2 * x >= currentGame.getMapWidth() ||
                    3 * frontY - 2 * y < 0 || 3 * frontY - 2 * y >= currentGame.getMapHeight() ||
                    currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).getSoldiers().size() > 0 ||
                    currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).getBuilding() != null ||
                    currentGame.getTileByCoordinates(3 * frontY - 2 * y, 3 * frontX - 2 * x).getSoldiers().size() > 0 ||
                    currentGame.getTileByCoordinates(3 * frontY - 2 * y, 3 * frontX - 2 * x).getBuilding() != null ||
                    currentGame.getTileByCoordinates(frontY , frontX).getSoldiers().size() > 0 ||
                    currentGame.getTileByCoordinates(frontY , frontX).getBuilding() != null)
                return Response.CANT_OPEN;
            currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).setBuilding(
                    new Building(currentPlayer, BuildingType.BRIDGE, 2 * frontX - x, 2 * frontY - y)
            );
            currentGame.getTileByCoordinates(3 * frontY - 2 * y, 3 * frontX - 2 * x).setBuilding(
                    new Building(currentPlayer, BuildingType.BRIDGE, 3 * frontX - 2 * x, 3 * frontY - 2 * y)
            );
            currentGame.getTileByCoordinates(frontY , frontX).setBuilding(
                    new Building(currentPlayer, BuildingType.BRIDGE, frontX, frontY)
            );
        }
        gate.setTheDoor();
        return Response.GATE_OPEN;
    }

    private static Response closeTheGate(Gate gate, int x, int y, int frontX, int frontY){
        currentGame.getTileByCoordinates(y, x).setHeight(3);
        if(gate.getBuildingType() != BuildingType.DRAWBRIDGE) {
            currentGame.getTileByCoordinates(2 * y - frontY, 2 * x - frontX).setHeight(3);
            currentGame.getTileByCoordinates(frontY, frontX).setHeight(3);
            if(gate.getBuildingType() == BuildingType.BIG_STONE_GATEHOUSE){
                currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).setHeight(3);
                currentGame.getTileByCoordinates(3 * y - frontY, 3 * x - frontX).setHeight(3);
            }
        }
        else{
            if(currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).getSoldiers().size() > 0 ||
                    currentGame.getTileByCoordinates(3 * frontY - 2 * y, 3 * frontX - 2 * x).getSoldiers().size() > 0 ||
                    currentGame.getTileByCoordinates(frontY, frontX).getSoldiers().size() > 0)
                return Response.CANT_CLOSE;
            setGroundBack(currentGame.getTileByCoordinates(frontY, frontX));
            currentGame.getTileByCoordinates(frontY, frontX).setBuilding(null);
            setGroundBack(currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x));
            currentGame.getTileByCoordinates(2 * frontY - y, 2 * frontX - x).setBuilding(null);
            setGroundBack(currentGame.getTileByCoordinates(3 * frontY - 2 * y, 3 * frontX - 2 * x));
            currentGame.getTileByCoordinates(3 * frontY - 2 * y, 3 * frontX - 2 * x).setBuilding(null);
            //getTileByCoordinates(frontX, frontY)
        }
        gate.setTheDoor();
        return Response.GATE_CLOSE;
    }

    private static void setGroundBack(Tile tile){
        if(tile.isDitch())
            tile.setHeight(-4);
        else if(tile.getType().CanBeCrossed())
            tile.setHeight(0);
        else tile.setHeight(-2);
    }

    public static Response setTheGate(Matcher matcher){
        int xCommand = Integer.parseInt(matcher.group("x"));
        int yCommand = Integer.parseInt(matcher.group("y"));
        String command = matcher.group("command");
        boolean open = Objects.equals(command, "open");
        if(xCommand - 1 < 0 || xCommand + 1 >= currentGame.getMapWidth() || yCommand - 1 < 0 || yCommand + 1 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(currentGame.getTileByCoordinates(yCommand, xCommand).getBuilding() == null || currentGame.getTileByCoordinates(yCommand, xCommand).getBuilding().getBuildingType().getBuildingClass() != Gate.class)
            return Response.NO_GATE_HERE;
        Gate gate = (Gate) currentGame.getTileByCoordinates(yCommand, xCommand).getBuilding();
        if(open && !gate.isOpen() || !open && gate.isOpen()){
            int x = gate.getXCoordinate();
            int y = gate.getYCoordinate();
            int frontX = x;
            int frontY = y;
            if(gate.getDirection() % 2 == 0) frontY += gate.getDirection() - 1;
            else frontX += 2 - gate.getDirection();
            if(open) return openTheGate(gate, x, y, frontX, frontY);
            else return closeTheGate(gate, x, y, frontX, frontY);
        }
        else if(open)
            return Response.ALREADY_OPEN;
        else return Response.ALREADY_CLOSE;
    }

    public static Response nextTurn(){
        if(currentGame.getTurnIndex() == currentGame.getNumberOfPlayers() - 1){
            armOilEngineers();
            resetOilState();
            checkFireDamage();
            computeDamages(); // computeDamages
            destroyDeadBodies(); // destroyDeadBodies  //destroy dead buildings
            destroyDeadBuildings();
            if(currentGame.getNumberOfPlayers() == 1) return Response.WINNER;
            moveUnits(); // moveUnits
            checkPatrolUnits();
            checkCows();
            checkDitches();
            checkToFillDitches();
            for(Kingdom kingdom : currentGame.getKingdoms()) {
                kingdom.addToHappiness(kingdom.getHappinessIncrease() - kingdom.getFear());
                computeFoods(kingdom);
                checkDelays(kingdom);
                if (kingdom.getTotalFoodAmount() == 0)
                    kingdom.setFoodRate(-2);
                //computeFears
                computeTaxes(kingdom);
                if (kingdom.getWealth() == 0)
                    kingdom.setTax(0);
                autoProducing(kingdom);
                //check if a king died
            }
        }
        currentGame.nextTurn();
        currentPlayer = currentGame.currentPlayer();
        //initialize some fields
        return Response.NEXT_TURN;
        //todo
    }

    private static void computeDamages() {
        // compute unit attack damages
        for (Kingdom k : currentGame.getKingdoms()) {
            for (Soldier s : k.getSoldiers()) {
                if (!s.isKingSaidToMove()) {
                    if (!computeAttackDamageOfSoldier(s))
                        computeAttackDamageOnBuildings(s);
                }
            }
            for (Equipment equipment : k.getEquipments()) {
                if (!equipment.isKingSaidToMove()) {
                    computeAttackDamageOfEquipment(equipment);
                }
            }
        }
    }

    private static boolean computeAttackDamageOfSoldier(Soldier s) {
        int x = s.getXCoordinate();
        int y = s.getYCoordinate();
        int fightRange = s.getState() * (s.getUnitType().isArab() ? 7 : 5);
        int attackPower = s.getAttackPower();
        if (s.getUnitType() == UnitType.OIL_ENGINEER && !checkOilEngineerAttack(s)) return false;
        if(s.getUnitType().isArcherType() &&
                currentGame.getTileByCoordinates(y, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y, x).getBuilding() instanceof Towers)
            fightRange += ((Towers) currentGame.getTileByCoordinates(y, x).getBuilding()).getFireRange();
        Unit enemy = findNearestEnemyTo(s, fightRange);
        if (enemy == null) return false;
        int enemyX = enemy.getXCoordinate();
        int enemyY = enemy.getYCoordinate();
        attackPower += (int) (((double)s.getOwner().getFear() / 20) * attackPower);
        if (currentGame.getTileByCoordinates(enemyY,enemyX).getEquipment() != null) {
            int attackPowerToShield = Math.min(currentGame.getTileByCoordinates(enemyY,enemyX).getEquipment().getHealth(),attackPower);
            currentGame.getTileByCoordinates(enemyY,enemyX).getEquipment().subHealth(attackPowerToShield);
            attackPower -= attackPowerToShield;
        }
        attackPower -= (int) ((double)attackPower * enemy.getUnitType().getDefensePower());
        if(Math.random() < s.getUnitType().getPrecision())
            attackPower = 20;
        //check for the enemy's defenses ( like portable shield?)
        //defend range of towers
        //what if the enemy is on a tower and the soldier on ground?
        int squareOfDistance = (x - enemyX) * (x - enemyX) + (y - enemyY) * (y - enemyY);
        if (squareOfDistance < s.getSecondRange() * s.getSecondRange()) return false;
        if (squareOfDistance <= s.getRange() * s.getRange()) {
            s.setWishPlace(currentGame.getMap()[y][x]);
            if (s.getUnitType() == UnitType.OIL_ENGINEER) {
                for (Unit unit : currentGame.getTileByCoordinates(enemyY,enemyX).getAllUnits()) {
                    if (!(unit instanceof Equipment) && unit.getOwner() != s.getOwner())
                        unit.setFlammable(true);
                }
                s.setHasOil(false);
                return true;
            } else if (s.getUnitType() == UnitType.SLAVE || s.getUnitType() == UnitType.FIRE_THROWER) {
                for (Unit unit : currentGame.getTileByCoordinates(enemyY,enemyX).getAllUnits()) {
                    if (!(unit instanceof Equipment) && unit.isFlammable() && s.getOwner() != unit.getOwner())
                        unit.addToFireDamageEachTurn(s.getUnitType().getAttackPower());
                }
                return true;
            }
            enemy.subHealth(attackPower);
            return true;
        }
        s.setWishPlace(currentGame.getMap()[enemyY][enemyX]);
        return true;
    }

    private static boolean computeAttackDamageOfEquipment(Equipment e) {
        int x = e.getXCoordinate();
        int y = e.getYCoordinate();
        int fightRange = 15;
        int attackPower = e.getDamage();
        if (e.getEquipmentType() == EquipmentType.FIRE_BALLISTA) return getAttackDamageOfFireBallista(e);
        Building enemy = findNearestEnemyToBuilding(e, fightRange); //todo
        if (enemy == null) return false;
        int enemyX = enemy.getXCoordinate();
        int enemyY = enemy.getYCoordinate();
        attackPower += (int) (((double)e.getOwner().getFear() / 20) * attackPower);
        if(Math.random() < e.getUnitType().getPrecision())
            attackPower = 20;
        //check for the enemy's defenses ( like portable shield?)
        //defend range of towers
        //what if the enemy is on a tower and the soldier on ground?
        int squareOfDistance = (x - enemyX) * (x - enemyX) + (y - enemyY) * (y - enemyY);
        if (squareOfDistance <= e.getRange() * e.getRange()) {
            e.setWishPlace(currentGame.getMap()[y][x]);
            enemy.addHitPoint(-1 * attackPower);
            return true;
        }
        e.setWishPlace(currentGame.getMap()[enemyY][enemyX]);
        return true;
    }

    private static boolean getAttackDamageOfFireBallista(Equipment e) {
        int x = e.getXCoordinate();
        int y = e.getYCoordinate();
        int fightRange = 15;
        int attackPower = e.getDamage();
        Unit enemy = findNearestEnemyTo(e, fightRange);
        if (enemy == null) return false;
        int enemyX = enemy.getXCoordinate();
        int enemyY = enemy.getYCoordinate();
        for (Unit unit : currentGame.getTileByCoordinates(enemyY,enemyX).getAllUnits()) {
            if (unit.isFlammable() && !(unit instanceof Equipment) && unit.getOwner() != e.getOwner()) {
                unit.addToFireDamageEachTurn(e.getDamage());
            }
        }
        return true;
    }



    private static boolean checkOilEngineerAttack(Soldier s) {
        int x = s.getXCoordinate();
        int y = s.getYCoordinate();
        int fightRange = 2;
        if ((s.getState() == 0 && getNumberOfEnemiesInRange(s,fightRange) < 1) ||
                (s.getState() == 1 && getNumberOfEnemiesInRange(s,fightRange) < 2) ||
                (s.getState() == 2 && getNumberOfEnemiesInRange(s,fightRange) < 3)) return false;
        if (!s.isHasOil()) return false;
        return true;
    }

    private static int getNumberOfEnemiesInRange(Soldier soldier, int fightRange) {
        int x = soldier.getXCoordinate();
        int y = soldier.getYCoordinate();
        Kingdom owner = soldier.getOwner();
        int amount = 0;
        for (int i = 1; i <= fightRange; i++) {
            for (int j = 0; j <= i; j++) { // x+i-j, y+j
                if (x + i - j >= currentGame.getMapWidth() || y + j >= currentGame.getMapHeight()) continue;
                for (Unit e : currentGame.getMap()[y + j][x + i - j].getAllUnits())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) amount++;
            }
            for (int j = 0; j <= i; j++) { // x+i-j, y-j
                if (x + i - j >= currentGame.getMapWidth() || y - j < 0) continue;
                for (Unit e : currentGame.getMap()[y - j][x + i - j].getAllUnits())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) amount++;
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y+j
                if (x - i + j < 0 || y + j >= currentGame.getMapHeight()) continue;
                for (Unit e : currentGame.getMap()[y + j][x - i + j].getAllUnits())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) amount++;
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y-j
                if (x - i + j < 0 || y - j < 0) continue;
                for (Unit e : currentGame.getMap()[y - j][x - i + j].getAllUnits())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) amount++;
            }
        }
        return amount;
    }

    private static void computeAttackDamageOnBuildings(Soldier s){
        if (s.getUnitType() != UnitType.FIRE_THROWER && s.getUnitType() != UnitType.SLAVE) return;
        int fightRange = s.getState() * (s.getUnitType().isArab() ? 7 : 5);
        Building enemy = findNearestEnemyToBuilding(s, fightRange);
        if (enemy == null) return;
        if (enemy.isFlammable()) enemy.addToFireDamageEachTurn(s.getAttackPower());
    }

    public static Unit findNearestEnemyTo(Unit s, int fightRange) {
        int x = s.getXCoordinate();
        int y = s.getYCoordinate();
        Kingdom owner = s.getOwner();
        Unit enemy = null;
        PathFinder pathFinder = new PathFinder(currentGame.getMap());
        for (Unit e : currentGame.getMap()[y][x].getAllUnits()) {
            if (enemy == null && e.getOwner() != owner && e.getHealth() > 0) {
                enemy = e;
                if ((s instanceof Equipment || !s.getUnitType().isArcherType()) && pathFinder.findPath(currentGame.getTileByCoordinates(y,x),
                        currentGame.getTileByCoordinates(e.getYCoordinate(),e.getXCoordinate())) == null) enemy = null;
            }
        }
        for (int i = 1; i <= fightRange; i++) {
            if (enemy != null) break;
            for (int j = 0; j <= i; j++) { // x+i-j, y+j
                if (x + i - j >= currentGame.getMapWidth() || y + j >= currentGame.getMapHeight()) continue;
                for (Unit e : currentGame.getMap()[y + j][x + i - j].getAllUnits()) {
                    if (e.getOwner() != owner && e.getHealth() > 0) {
                        enemy = e;
                        if ((s instanceof Equipment || !s.getUnitType().isArcherType()) && pathFinder.findPath(currentGame.getTileByCoordinates(y,x),
                                currentGame.getTileByCoordinates(e.getYCoordinate(),e.getXCoordinate())) == null) enemy = null;
                    }
                }
            }
            for (int j = 0; j <= i; j++) { // x+i-j, y-j
                if (x + i - j >= currentGame.getMapWidth() || y - j < 0) continue;
                for (Unit e : currentGame.getMap()[y - j][x + i - j].getAllUnits()) {
                    if (e.getOwner() != owner && e.getHealth() > 0) {
                        enemy = e;
                        if ((s instanceof Equipment || !s.getUnitType().isArcherType()) && pathFinder.findPath(currentGame.getTileByCoordinates(y,x),
                                currentGame.getTileByCoordinates(e.getYCoordinate(),e.getXCoordinate())) == null) enemy = null;
                    }
                }
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y+j
                if (x - i + j < 0 || y + j >= currentGame.getMapHeight()) continue;
                for (Unit e : currentGame.getMap()[y + j][x - i + j].getAllUnits()) {
                    if (e.getOwner() != owner && e.getHealth() > 0) {
                        enemy = e;
                        if ((s instanceof Equipment || !s.getUnitType().isArcherType()) && pathFinder.findPath(currentGame.getTileByCoordinates(y,x),
                                currentGame.getTileByCoordinates(e.getYCoordinate(),e.getXCoordinate())) == null) enemy = null;
                    }
                }
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y-j
                if (x - i + j < 0 || y - j < 0) continue;
                for (Unit e : currentGame.getMap()[y - j][x - i + j].getAllUnits()) {
                    if (e.getOwner() != owner && e.getHealth() > 0) {
                        enemy = e;
                        if ((s instanceof Equipment || !s.getUnitType().isArcherType()) && pathFinder.findPath(currentGame.getTileByCoordinates(y,x),
                                currentGame.getTileByCoordinates(e.getYCoordinate(),e.getXCoordinate())) == null) enemy = null;
                    }
                }
            }
        }
        return null;
    }

    public static Building findNearestEnemyToBuilding(Unit unit, int fightRange) {
        int x = unit.getXCoordinate();
        int y = unit.getYCoordinate();
        Kingdom owner = unit.getOwner();
        Building building = currentGame.getTileByCoordinates(y, x).getBuilding();
        if (building != null && building.getOwner() != owner && building.getHitPoint() > 0) return building;
        for (int i = 1; i <= fightRange; i++) {
            for (int j = 0; j <= i; j++) { // x+i-j, y+j
                if (x + i - j >= currentGame.getMapWidth() || y + j >= currentGame.getMapHeight()) continue;
                building = currentGame.getTileByCoordinates(y + j, x + i - j).getBuilding();
                if (building != null && building.getOwner() != owner && building.getHitPoint() > 0) return building;
            }
            for (int j = 0; j <= i; j++) { // x+i-j, y-j
                if (x + i - j >= currentGame.getMapWidth() || y - j < 0) continue;
                building = currentGame.getTileByCoordinates(y - j, x + i - j).getBuilding();
                if (building != null && building.getOwner() != owner && building.getHitPoint() > 0) return building;
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y+j
                if (x - i + j < 0 || y + j >= currentGame.getMapHeight()) continue;
                building = currentGame.getTileByCoordinates(y + j, x - i + j).getBuilding();
                if (building != null && building.getOwner() != owner && building.getHitPoint() > 0) return building;
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y-j
                if (x - i + j < 0 || y - j < 0) continue;
                building = currentGame.getTileByCoordinates(y - j, x - i + j).getBuilding();
                if (building != null && building.getOwner() != owner && building.getHitPoint() > 0) return building;
            }
        }
        return null;
    }

    private static void destroyDeadBodies() {
        for (int j = 0; j < currentGame.getKingdoms().size(); j++) {
            Kingdom k = currentGame.getKingdoms().get(j);
            boolean isKingDead = false;
            for(int i = 0; i < k.getUnits().size(); i++){
                if(k.getUnits().get(i).getHealth() <= 0){
                    Unit unit = k.getUnits().get(i);
                    //todo war caged dogs
                    currentPlayer.addToPopulation(-1);
                    currentGame.getTileByCoordinates(unit.getYCoordinate(),unit.getXCoordinate()).removeUnit(unit);
                    if(unit.getUnitType() == UnitType.KING) isKingDead = true;
                    k.removeUnit(unit);
                    i--;
                }
            }
            if(isKingDead) {
                removeKingdom(k);
                j--;
                if(currentGame.getNumberOfPlayers() == 1) return;
            }
        }
    }

    private static void destroyDeadBuildings(){
        for (Kingdom kingdom : currentGame.getKingdoms()) {
            for (int i = kingdom.getBuildings().size() - 1; i >= 0; i--) {
                Building building = kingdom.getBuildings().get(i);
                if (building.getHitPoint() < 0) {
                    //todo destroy from tile
                    kingdom.removeBuilding(building);
                }
            }
        }
        //todo setGroundBack
    }

    private static void moveUnits() {
        Tile[][] map = currentGame.getMap();
        PathFinder pathFinder = new PathFinder(map);
        for (Kingdom k : currentGame.getKingdoms()) {
            for (int j = k.getUnits().size() - 1; j >= 0; j--) {
                Unit s = k.getUnits().get(j);
                Tile curTile = map[s.getYCoordinate()][s.getXCoordinate()];
                Tile wishPlace = s.getWishPlace();
                Deque<Tile> path = pathFinder.findPath(curTile, wishPlace);
                if (path == null) {
                    s.setKingSaidToMove(false);
                    continue;
                }
                Tile targetTile = curTile;
                boolean check = false;
                for(int i = 0; i <= s.getSpeed() && !path.isEmpty(); i++) {
                    targetTile = path.pollFirst();
                    if (isTrapWorking(targetTile, s, k)) {
                        check = true;
                    }
                }
                if (targetTile == wishPlace){
                    s.setKingSaidToMove(false);
                }
                if (targetTile == curTile) continue;
                curTile.removeUnit(s);
                if (!check) targetTile.addUnit(s);
                s.setXCoordinate(targetTile.getXCoordinate());
                s.setYCoordinate(targetTile.getYCoordinate());
            }
        }
    }

    private static boolean isTrapWorking(Tile tile, Unit unit, Kingdom kingdom) {
        Building building = tile.getBuilding();
        if (building == null) return false;
        if (building.getBuildingType() == BuildingType.KILLING_PIT) {
            if (unit instanceof Soldier) kingdom.getSoldiers().remove(unit);
            if (unit instanceof Equipment) kingdom.getEquipments().remove(unit);
            kingdom.getUnits().remove(unit); //kill unit
            ((Trap)building).setCanBeSeenByEnemy(true);
            return true;
        }
        return false;
    }

    private static void checkPatrolUnits(){
        for(Kingdom kingdom : currentGame.getKingdoms()){
            for(Soldier soldier : kingdom.getSoldiers()){
                if(soldier.isSaidToPatrol()){
                    if(soldier.getXCoordinate() == soldier.getPatrolWishPlace1().getXCoordinate() &&
                            soldier.getYCoordinate() == soldier.getPatrolWishPlace1().getYCoordinate()) {
                        soldier.switchPatrolPlaces();
                        soldier.setWishPlace(soldier.getPatrolWishPlace1());
                    }
                }
            }
        }
    }

    private static void checkCows(){
        for(Kingdom kingdom : currentGame.getKingdoms()){
            for(Unit cow : kingdom.getCows()){
                Tile tile = cow.getWishPlace();
                if(cow.getXCoordinate() == tile.getXCoordinate() && cow.getYCoordinate() == tile.getYCoordinate() &&
                        tile.getBuilding() != null && tile.getBuilding().getBuildingType() != BuildingType.OX_TETHER) {
                    if (tile.getBuilding().getBuildingType() == BuildingType.STOCKPILE) {
                        kingdom.addStoneToStockpile(cow, (Storage) tile.getBuilding());
                        Building building;
                        if (cow.getCowStored() == 0) building = kingdom.getRandomQuarry();
                        else building = kingdom.getRandomStockpile();
                        if (building == null) return;
                        cow.setWishPlace(currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()));
                    }
                    else if (tile.getBuilding().getBuildingType() == BuildingType.QUARRY) {
                        int cost = Math.min(((Producers) tile.getBuilding()).getStored(), 12 - cow.getCowStored());
                        ((Producers) tile.getBuilding()).addToStored(-1 * cost);
                        cow.addToStored(cost);
                        Building building;
                        if (cost == 0) building = kingdom.getRandomQuarry();
                        else building = kingdom.getRandomStockpile();
                        if (building == null) return;
                        cow.setWishPlace(currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()));
                    }
                }
                else if(cow.getXCoordinate() == tile.getXCoordinate() && cow.getYCoordinate() == tile.getYCoordinate()){
                    Building building1 = kingdom.getRandomStockpile();
                    Building building2 = kingdom.getRandomQuarry();
                    if(cow.getCowStored() == 0){
                        if(building2 == null) return;
                        cow.setWishPlace(currentGame.getTileByCoordinates(building2.getYCoordinate(), building2.getXCoordinate()));
                    }
                    else if(cow.getCowStored() == 12){
                        if(building1 == null) return;
                        cow.setWishPlace(currentGame.getTileByCoordinates(building1.getYCoordinate(), building1.getXCoordinate()));
                    }
                    else if(building1 != null)
                        cow.setWishPlace(currentGame.getTileByCoordinates(building1.getYCoordinate(), building1.getXCoordinate()));
                    else if(building2 != null)
                        cow.setWishPlace(currentGame.getTileByCoordinates(building2.getYCoordinate(), building2.getXCoordinate()));
                }
            }
        }
    }

    private static void computeFoods(Kingdom player){
        double rate = 1 + ((double)player.getFoodRate() / 2);
        int totalFoodUsage = (int)(rate * player.getMaxPopulation());
        int newFoodDiversity = player.getFoodDiversity();
        player.eatFoods(totalFoodUsage);
        player.addToHappiness(newFoodDiversity - 1);
        player.addToHappiness(player.getFoodRate() * 4);
        player.addToHappiness(player.wineUsage());
    }

    private static void computeTaxes(Kingdom player){
        int tax = player.getTax();
        double addToWealth = 0;
        int addToHappiness = currentPlayer.taxEffectOnHappiness(tax);
        if(tax > 0)
            addToWealth = 0.2 * tax + 0.4;
        else if(tax < 0)
            addToWealth = 0.2 * tax - 0.4;
        player.addToWealth((int)(addToWealth * player.getMaxPopulation()));
        player.addToHappiness(addToHappiness);
    }

    private static void autoProducing(Kingdom player){
        for(Building building : player.getBuildings()){
            if(building instanceof Producers){
                ResourcesType InputType = ((Producers) building).getResourcesInput().getType();
                int InputAmount = ((Producers) building).getResourcesInput().getAmount();
                Asset output1 = ((Producers) building).getAssetOutput();
                if(building.getBuildingType() == BuildingType.OIL_SMELTER || building.getBuildingType() == BuildingType.QUARRY)
                    ((Producers) building).addToStored(Math.min(((Producers) building).getCapacity() - ((Producers) building).getStored(), output1.getAmount()));
                else {
                    boolean canPay = true;
                    if (InputAmount != 0) {
                        if (player.getResourceAmountByType(InputType) < InputAmount)
                            canPay = false;
                        if(canPay)
                            player.payResource(((Producers) building).getResourcesInput());
                    }
//                    if(building.getBuildingType() == BuildingType.WOODCUTTERS){
//                        if(currentGame.getTrees().size() == 0)
//                            canPay = false;
//                        if(canPay)
//                            currentGame.cutTree();
//                    }
                    // todo uncomment
                    if(canPay) {
                        if(output1.getAmount() != 0) {
                            player.addAsset(output1);
                        }
                    }
                }
            }
        }
    }

    public static void initializeTradeController() {
        TradeController.currentPlayer = currentPlayer;
    }

    public static Response terminateTheGame(){
        return null;
        //todo
    }

    private static void removeKingdom(Kingdom kingdom){
        for(Unit unit : kingdom.getUnits())
            currentGame.getTileByCoordinates(unit.getYCoordinate(),unit.getXCoordinate()).removeUnit(unit);
        for(Building building : kingdom.getBuildings()) {
            int xCenter = building.getXCoordinate();
            int yCenter = building.getYCoordinate();
            int size = (building.getBuildingType().getSize() - 1) / 2;
            for (int i = xCenter - size; i <= xCenter + size; i++) {
                for (int j = yCenter - size; j <= yCenter + size; j++) {
                    currentGame.getTileByCoordinates(j, i).setBuilding(null);
                }
            }
        }
        int highScore = kingdom.getMaxPopulation() + kingdom.getHappiness();
        kingdom.getOwner().addHighScore(highScore);
        currentGame.removeKingdom(kingdom);
    }

    private static void armOilEngineers() {
        for (Kingdom kingdom : currentGame.getKingdoms()) {
            for (Producers oilSmelter : kingdom.getOilSmelter()) {
                for (Soldier soldier : currentGame.getTileByCoordinates(oilSmelter.getYCoordinate(), oilSmelter.getXCoordinate()).getSoldiers()) {
                    if (soldier.getUnitType() == UnitType.OIL_ENGINEER && soldier.getOwner() == kingdom && !soldier.isHasOil() && oilSmelter.getStored() > 0) {
                        oilSmelter.addToStored(-1);
                        soldier.setHasOil(true);
                    }
                }
            }
        }
    }

    private static void checkDelays(Kingdom kingdom) {
        for (int i = kingdom.getBuildings().size() - 1; i >= 0; i--) {
            Building siegeTent = kingdom.getBuildings().get(i);
            if (siegeTent.getBuildingType() == BuildingType.SIEGE_TENT) {
                if (siegeTent.getDelay() == 0) {
                    int x = siegeTent.getXCoordinate();
                    int y = siegeTent.getYCoordinate();
                    EquipmentType equipmentType = siegeTent.getEquipmentType();
                    if (equipmentType == null) continue;
                    Tile tile = currentGame.getTileByCoordinates(y, x);
                    tile.setBuilding(null);
                    int cnt = 0;
                    for (Unit unit : currentGame.getTileByCoordinates(siegeTent.getYCoordinate(),siegeTent.getXCoordinate()).getAllUnits()) {
                        if (cnt == equipmentType.getEngineerPrice()) break;
                        if (unit.getUnitType() == UnitType.ENGINEER && !unit.isAvailable()) {
                            unit.setAvailable(true);
                            cnt++;
                        }
                    }
                    kingdom.removeBuilding(siegeTent);
                    Equipment equipment = new Equipment(equipmentType, kingdom, x, y);
                    applyEquipmentAbility(equipment);
                } else siegeTent.subDelay();
            }
        }
    }

    private static void applyEquipmentAbility(Equipment equipment) {
        int y = equipment.getYCoordinate();
        int x = equipment.getXCoordinate();
        if (equipment.getEquipmentType() == EquipmentType.SIEGE_TOWER)
            currentGame.getTileByCoordinates(y,x).setHeight(1);
        else if (equipment.getEquipmentType() == EquipmentType.PORTABLE_SHIELD)
            currentGame.getTileByCoordinates(y,x).setEquipment(equipment);
    }

    private static void checkDitches(){
        for(Kingdom kingdom : currentGame.getKingdoms()){
            for(Tile ditch : kingdom.getDitches()){
                if(!ditch.isDitch()) {
                    if (ditch.getDitchDelay() > 0) {
                        boolean isSomeOneDigging = false;
                        for (Soldier soldier : ditch.getSoldiers()) {
                            if (soldier.getDitch() == ditch) {
                                isSomeOneDigging = true;
                                break;
                            }
                        }
                        if (isSomeOneDigging) ditch.subDitchDelay();
                    }
                    else {
                        ditch.setDitch(true);
                        ditch.setHeight(-4);
                        currentGame.getDitches().add(ditch);
                        for(Soldier soldier : kingdom.getSoldiers()) {
                            if (soldier.getDitch() == ditch) {
                                soldier.setDitch(null);
                                if (soldier.getYCoordinate() == ditch.getYCoordinate() && soldier.getXCoordinate() == ditch.getXCoordinate()) {
                                    Tile adjacent = getAdjacentCell(ditch, null);
                                    ditch.removeSoldier(soldier);
                                    if (adjacent != null) {
                                        adjacent.addSoldier(soldier);
                                        soldier.setXCoordinate(adjacent.getXCoordinate());
                                        soldier.setYCoordinate(adjacent.getYCoordinate());
                                    }
                                    else kingdom.removeUnit(soldier);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void checkToFillDitches(){
        for(int i = 0; i < currentGame.getDitches().size(); i++){
            Tile ditch = currentGame.getDitches().get(i);
            if(ditch.getDitchDelay() < 3) {
                int x = ditch.getXCoordinate();
                int y = ditch.getYCoordinate();
                boolean isToFill = false;
                ArrayList<Soldier> allSoldiersAround = new ArrayList<>();
                if (y > 0)
                    allSoldiersAround.addAll(currentGame.getTileByCoordinates(y - 1, x).getSoldiers());
                if (y < currentGame.getMapHeight() - 1)
                    allSoldiersAround.addAll(currentGame.getTileByCoordinates(y + 1, x).getSoldiers());
                if (x > 0)
                    allSoldiersAround.addAll(currentGame.getTileByCoordinates(y, x - 1).getSoldiers());
                if (x < currentGame.getMapWidth() - 1)
                    allSoldiersAround.addAll(currentGame.getTileByCoordinates(y, x + 1).getSoldiers());
                for (Soldier soldier : allSoldiersAround) {
                    if (soldier.getFill() == ditch) {
                        isToFill = true;
                        break;
                    }
                }
                if (isToFill) ditch.addDitchDelay();
            }
            else{
                ditch.setDitch(false);
                setGroundBack(ditch);
                currentGame.getDitches().remove(ditch);
                for(Kingdom kingdom : currentGame.getKingdoms()){
                    kingdom.getDitches().remove(ditch);
                    for(Soldier soldier : kingdom.getSoldiers()){
                        if(soldier.getFill() == ditch){
                            soldier.setFill(null);
                        }
                    }
                }
            }
        }
    }

    public static Response removeDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(!currentPlayer.getDitches().contains(currentGame.getTileByCoordinates(y, x)))
            return Response.YOU_HAVE_NO_DITCH_THERE;
        currentGame.getTileByCoordinates(y, x).setDitch(false);
        setGroundBack(currentGame.getTileByCoordinates(y, x));
        currentGame.getDitches().remove(currentGame.getTileByCoordinates(y, x));
        currentPlayer.getDitches().remove(currentGame.getTileByCoordinates(y, x));
        currentGame.getTileByCoordinates(y, x).setDitchDelay(3);
        return Response.DITCH_REMOVED;
    }

    public static Tile getAdjacentCell(Tile tile, Unit soldier){
        int x = tile.getXCoordinate();
        int y = tile.getYCoordinate();
        if(tile.isDitch()){
            if(y > 0 && checkTile(currentGame.getTileByCoordinates(y - 1, x), soldier))
                return currentGame.getTileByCoordinates(y - 1, x);
            if(y < currentGame.getMapHeight() - 1 && checkTile(currentGame.getTileByCoordinates(y + 1, x), soldier))
                return currentGame.getTileByCoordinates(y + 1, x);
            if(x > 0 && checkTile(currentGame.getTileByCoordinates(y, x - 1), soldier))
                return currentGame.getTileByCoordinates(y, x - 1);
            if(x < currentGame.getMapWidth() - 1 && checkTile(currentGame.getTileByCoordinates(y, x + 1), soldier))
                return currentGame.getTileByCoordinates(y, x + 1);
        }
        else if(tile.getBuilding() != null){
            int size = (tile.getBuilding().getBuildingType().getSize() - 1) / 2;
            if(y > size){
                for(int i = x - size; i <= x + size; i++){
                    if(checkTile(currentGame.getTileByCoordinates(y - size - 1, i), soldier))
                        return currentGame.getTileByCoordinates(y - size - 1, i);
                }
            }
            if(y < currentGame.getMapHeight() - size - 1){
                for(int i = x - size; i <= x + size; i++){
                    if(checkTile(currentGame.getTileByCoordinates(y + size + 1, i), soldier))
                        return currentGame.getTileByCoordinates(y + size + 1, i);
                }
            }
            if(x > size){
                for(int i = y - size; i <= y + size; i++){
                    if(checkTile(currentGame.getTileByCoordinates(i, x - size - 1), soldier))
                        return currentGame.getTileByCoordinates(i, x - size - 1);
                }
            }
            if(x < currentGame.getMapWidth() - size - 1){
                for(int i = y - size; i <= y + size; i++){
                    if(checkTile(currentGame.getTileByCoordinates(i, y + size + 1), soldier))
                        return currentGame.getTileByCoordinates(i, y + size + 1);
                }
            }
        }
        return null;
    }

    private static boolean checkTile(Tile tile, Unit soldier){
        if(soldier != null){
            PathFinder pathFinder = new PathFinder(GameController.currentGame.getMap());
            Deque<Tile> path = null;
            path = pathFinder.findPath(currentGame.getTileByCoordinates(soldier.getYCoordinate(), soldier.getXCoordinate()), tile);
            if (path == null) return false;
        }
        return true;
    }

    private static void resetOilState() {
        for (Kingdom kingdom : currentGame.getKingdoms()) {
            int difference = 5;
            for (Building building : kingdom.getBuildings()) {
                if (currentGame.getNumberOfTurns() - building.getLastOiledTurn() >= difference)
                    building.setFlammable(false);
            }
            for (Unit unit : kingdom.getUnits()) {
                if (!(unit instanceof Equipment) && currentGame.getNumberOfTurns() - unit.getLastOiledTurn() >= difference)
                    unit.setFlammable(false);
            }
        }
    }

    private static void checkFireDamage() {
        for (Kingdom kingdom : currentGame.getKingdoms()) {
            for (Unit unit : kingdom.getUnits()) {
                unit.subHealth(unit.getFireDamageEachTurn());
            }
        }

        //todo   har zarbe mard atishi hey in ziad tar mishe damage atishesh
    }
}
