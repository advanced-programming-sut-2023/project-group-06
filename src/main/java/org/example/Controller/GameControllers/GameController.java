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
        else if(currentGame.getTileByCoordinates(y, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getOwner() == currentPlayer){
            if(currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.MAIN_CASTLE)
                return Response.CLEAR_MAIN_CASTLE;
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
            int xCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getXCoordinate();
            int yCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getYCoordinate();
            int size = (currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().getSize() - 1) / 2;
            for(int i = xCenter - size; i <= xCenter + size; i++){
                for(int j = yCenter - size; j <= yCenter + size; j++){
                    currentGame.getTileByCoordinates(j, i).setBuilding(null);
                }
            }
        }
        for(Soldier soldier : currentGame.getTileByCoordinates(y, x).getSoldiers()){
            if(soldier.getOwner() == currentPlayer){
                currentGame.getTileByCoordinates(y, x).removeSoldier(soldier);
                currentPlayer.getSoldiers().remove(soldier);
                currentPlayer.addToPopulation(-1);
            }
        }
        //todo remove nonSoldier Units
        return Response.CLEAR_SUCCESSFUL;
    }

    public static Response dropUnit(Matcher matcher){
        return null;
        //todo
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
        if(currentGame.getTileByCoordinates(y, x).getUnits().size() > 0)
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
        if(currentGame.getTileByCoordinates(y, x).getUnits().size() > 0)
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
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(currentGame.getTileByCoordinates(y, x).getSoldiers().size() > 0)
            return Response.TEXTURE_UNDER_UNIT;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.SET_TEXTURE_UNDER_BUILDING;
        currentGame.getTileByCoordinates(y, x).setType(type);
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
        if(type == null)
            return Response.INVALID_TYPE;
        if(x1 > x2 || y1 > y2 || x1 < 0 || y1 < 0 || x2 >= currentGame.getMapWidth() || y2 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        for(int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null)
                    return Response.SET_TEXTURE_UNDER_BUILDING;
                if(currentGame.getTileByCoordinates(j, i).getSoldiers().size() > 0)
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
        if(currentGame.getTileByCoordinates(y, x).getUnits().size() > 0)
            return Response.CANT_PUT_THIS_ON_TROOPS;
        if(buildingtype == null || buildingtype == BuildingType.TREE || buildingtype == BuildingType.ROCK)
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
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null)
                    return Response.BUILDING_ALREADY_EXIST;
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
            }
        }
        else if(Towers.class.equals(buildingtype.getBuildingClass()))
            building = new Towers(currentPlayer, buildingtype, x, y);
        else if(Trap.class.equals(buildingtype.getBuildingClass()))
            building = new Trap(currentPlayer, buildingtype, x, y);
        else building  = new Building(currentPlayer, buildingtype, x, y);
        currentPlayer.payResource(buildingtype.getResourcesPrice());
        for(int i = x - size; i <= x + size; i++){
            for(int j = y - size; j <= y + size; j++){
                currentGame.getTileByCoordinates(j, i).setBuilding(building);
            }
        }
        currentPlayer.getBuildings().add(building);
        currentPlayer.addToWealth(-1 * buildingtype.getGoldPrice());
        currentPlayer.payEngineer(buildingtype.getEngineerPrice());
        currentPlayer.addToPopulation(buildingtype.getWorkerPrice() + buildingtype.getEngineerPrice());
        if(buildingtype == BuildingType.CHURCH)
            currentPlayer.addToHappinessIncrease(2);
        if(buildingtype == BuildingType.CATHEDRAL)
            currentPlayer.addToHappinessIncrease(4);
        if(buildingtype == BuildingType.HOVEL || buildingtype == BuildingType.SMALL_STONE_GATEHOUSE)
            currentPlayer.addToMaxPopulation(8);
        if(buildingtype == BuildingType.BIG_STONE_GATEHOUSE)
            currentPlayer.addToMaxPopulation(10);

        /*if(buildingtype == BuildingType.OX_TETHER) {
            Soldier cow = new Soldier(x, y, currentPlayer, UnitType.COW);
            currentPlayer.getSoldiers().add(cow);
            ////bugs
        }*/
        return Response.DROP_BUILDING_SUCCESSFUL;
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
        if(buildingType == BuildingType.INN || buildingType == BuildingType.ENGINEERS_GUILD || buildingType == BuildingType.STABLE)
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
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for(Soldier soldier : currentGame.getTileByCoordinates(y, x).getSoldiers()){
            if(soldier.getOwner() == currentPlayer && soldier.getUnitType() == type)
                soldiers.add(soldier);
        }
        if(soldiers.size() == 0)
            return Response.NO_UNITS_WITH_THAT_TYPE;
        for(Soldier soldier : soldiers) {
            if(soldier.isSaidToPatrol()) {
                soldier.setSaidToPatrol(false);
                soldier.setWishPlace(currentGame.getTileByCoordinates(soldier.getYCoordinate(), soldier.getXCoordinate()));
            }
        }
        SoldierController.soldiers = soldiers;
        SoldierController.currentGame = currentGame;
        return Response.SELECT_SOLDIER_SUCCESSFUL;
    }

    private static Response openTheGate(Gate gate, int x, int y, int frontX, int frontY){
        currentGame.getTileByCoordinates(y, x).setHeight(0);
        if(gate.getBuildingType() != BuildingType.DRAWBRIDGE) {
            currentGame.getTileByCoordinates(2 * y - frontY, 2 * x - frontX).setHeight(0);
            currentGame.getTileByCoordinates(frontY, frontX).setHeight(0);
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
        if(tile.getType().CanBeCrossed())
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
            computeDamages(); // computeDamages
            destroyDeadBodies(); // destroyDeadBodies
            moveUnits(); // moveUnits
            checkPatrolUnits();
            for(Kingdom kingdom : currentGame.getKingdoms()) {
                kingdom.addToHappiness(kingdom.getHappinessIncrease() - kingdom.getFear());//inn ........
                computeFoods(kingdom);
                armOilEngineers(kingdom);
                resetOilState(kingdom);
                if (kingdom.getTotalFoodAmount() == 0)
                    kingdom.setFoodRate(-2);
                //computeFears
                computeTaxes(kingdom);
                if (kingdom.getWealth() == 0)
                    kingdom.setTax(0);
                autoProducing(kingdom);
                //check if a king died
                //add from quarry to stockpile
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
                if (!s.isKingSaidToMove())
                    if(!computeAttackDamageOfSoldier(s))
                        computeAttackDamageOnBuildings(s);
            }
        }
    }

    private static boolean computeAttackDamageOfSoldier(Soldier s) {
        int x = s.getXCoordinate();
        int y = s.getYCoordinate();
        int fightRange = s.getState() * (s.getUnitType().isArab() ? 7 : 5);
        int attackPower = s.getAttackPower();
        if(s.getUnitType().isArcherType() &&
                currentGame.getTileByCoordinates(y, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y, x).getBuilding() instanceof Towers)
            fightRange += ((Towers) currentGame.getTileByCoordinates(y, x).getBuilding()).getFireRange();
        Soldier enemy = findNearestEnemyTo(s, fightRange);
        if (enemy == null) return false;
        int enemyX = enemy.getXCoordinate();
        int enemyY = enemy.getYCoordinate();
        attackPower += (int) (((double)s.getOwner().getFear() / 20) * attackPower);
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
            enemy.subHealth(attackPower);
            return true;
        }
        s.setWishPlace(currentGame.getMap()[enemyY][enemyX]);
        return true;
    }

    private static void computeAttackDamageOnBuildings(Soldier s){
        //todo
    }

    public static Soldier findNearestEnemyTo(Soldier soldier, int fightRange) {
        int x = soldier.getXCoordinate();
        int y = soldier.getYCoordinate();
        for (Soldier e : currentGame.getMap()[y][x].getSoldiers())
            if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) return e;
        for (int i = 1; i <= fightRange; i++) {
            for (int j = 0; j <= i; j++) { // x+i-j, y+j
                if (x + i - j >= currentGame.getMapWidth() || y + j >= currentGame.getMapHeight()) continue;
                for (Soldier e : currentGame.getMap()[y + j][x + i - j].getSoldiers())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) return e;
            }
            for (int j = 0; j <= i; j++) { // x+i-j, y-j
                if (x + i - j >= currentGame.getMapWidth() || y - j < 0) continue;
                for (Soldier e : currentGame.getMap()[y - j][x + i - j].getSoldiers())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) return e;
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y+j
                if (x - i + j < 0 || y + j >= currentGame.getMapHeight()) continue;
                for (Soldier e : currentGame.getMap()[y + j][x - i + j].getSoldiers())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) return e;
            }
            for (int j = 0; j <= i; j++) { // x-i+j, y-j
                if (x - i + j < 0 || y - j < 0) continue;
                for (Soldier e : currentGame.getMap()[y - j][x - i + j].getSoldiers())
                    if (e.getOwner() != soldier.getOwner() && e.getHealth() > 0) return e;
            }
        }
        return null;
    }

    private static void destroyDeadBodies() {
        for (Kingdom k : currentGame.getKingdoms()) {
            for(int i = 0; i < k.getSoldiers().size(); i++){
                if(k.getSoldiers().get(i).getHealth() <= 0){
                    Soldier soldier = k.getSoldiers().get(i);
                    //todo war caged dogs
                    currentPlayer.addToPopulation(-1);
                    currentGame.getTileByCoordinates(soldier.getYCoordinate(),soldier.getXCoordinate()).removeSoldier(soldier);
                    if(k.getSoldiers().get(i).getUnitType() == UnitType.KING) removeKingdom(k);
                    else k.getSoldiers().remove(i);
                    i--;
                }
            }
        }
    }

    private static void moveUnits() {
        Tile[][] map = currentGame.getMap();
        PathFinder pathFinder = new PathFinder(map);
        for (Kingdom k : currentGame.getKingdoms()) {
            for (Soldier s : k.getSoldiers()) {
                Tile curTile = map[s.getYCoordinate()][s.getXCoordinate()];
                Tile wishPlace = s.getWishPlace();
                Deque<Tile> path = pathFinder.findPath(curTile, wishPlace);
                if (path == null) {
                    s.setKingSaidToMove(false);
                    continue;
                }
                Tile targetTile = curTile;
                for(int i = 0; i <= s.getSpeed() && !path.isEmpty(); i++)
                    targetTile = path.pollFirst();
                if (targetTile == wishPlace){
                    s.setKingSaidToMove(false);
                }
                if (targetTile == curTile) continue;
                curTile.removeSoldier(s);
                targetTile.addSoldier(s);
                s.setXCoordinate(targetTile.getXCoordinate());
                s.setYCoordinate(targetTile.getYCoordinate());
            }
        }
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

    private static void computeFoods(Kingdom player){
        double rate = 1 + ((double)player.getFoodRate() / 2);
        int totalFoodUsage = (int)(rate * player.getMaxPopulation());
        int newFoodDiversity = 0;
        if(player.getFoodAmountByType(FoodType.CHEESE) > 0)
            newFoodDiversity++;
        if(player.getFoodAmountByType(FoodType.MEAT) > 0)
            newFoodDiversity++;
        if(player.getFoodAmountByType(FoodType.BREAD) > 0)
            newFoodDiversity++;
        if(player.getFoodAmountByType(FoodType.APPLES) > 0)
            newFoodDiversity++;
        player.eatFoods(totalFoodUsage);
        player.addToHappiness(newFoodDiversity - 1);
        player.addToHappiness(player.getFoodRate() * 4);
        player.addToHappiness(player.wineUsage());
    }

    private static Response computeFears(Kingdom player){
        return null;
        //todo
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

    private static void computeWishPlacesCows(){

    }

    private static void autoProducing(Kingdom player){
        for(Building building : player.getBuildings()){
            if(/*building.getBuildingType().getBuildingClass() == Producers.class*/building instanceof Producers){
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
                    /*if(building.getBuildingType() == BuildingType.WOODCUTTERS){
                        if(currentGame.getTrees().size() == 0)
                            canPay = false;
                        if(canPay)
                            currentGame.cutTree();
                    }*/
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

    private static Response endGame(){
        return null;
        //todo
        //set all static fields inside controllers equal to zero
    }

    public static Response terminateTheGame(){
        return null;
        //todo
    }

    private static void removeKingdom(Kingdom kingdom){
        for(Soldier soldier : kingdom.getSoldiers())
            currentGame.getTileByCoordinates(soldier.getYCoordinate(),soldier.getXCoordinate()).removeSoldier(soldier);
//        for(Unit unit : kingdom.getNonSoldierUnits())
//            currentGame.getTileByCoordinates(unit.getYCoordinate(),unit.getXCoordinate()).removeFromNonSoldierUnits(unit);
        for(Building building : kingdom.getBuildings()) {
            int xCenter = building.getXCoordinate();
            int yCenter = building.getYCoordinate();
            int size = (building.getBuildingType().getSize() - 1) / 2;
            for (int i = xCenter - size; i <= xCenter + size; i++) {
                for (int j = yCenter - size; j <= yCenter + size; j++)
                    currentGame.getTileByCoordinates(j, i).setBuilding(null);
            }
        }
        currentGame.removeKingdom(kingdom);
    }

    private static void armOilEngineers(Kingdom kingdom) {
        for (Producers oilSmelter : kingdom.getOilSmelter()) {
            for (Soldier soldier : currentGame.getTileByCoordinates(oilSmelter.getYCoordinate(),oilSmelter.getXCoordinate()).getSoldiers()) {
                if (soldier.getUnitType() == UnitType.OIL_ENGINEER && soldier.getOwner() == kingdom && !soldier.isHasOil() && oilSmelter.getStored() > 0) {
                    oilSmelter.addToStored(-1);
                    soldier.setHasOil(true);
                }
            }
        }
    }

    private static void resetOilState(Kingdom kingdom) {
        int difference = 5;
        for (Building building : kingdom.getBuildings()) {
            if (currentGame.getNumberOfTurns() - building.getLastOiledTurn() >= difference) building.setFlammable(false);
        }
        for (Soldier soldier : kingdom.getSoldiers()) {
            if (currentGame.getNumberOfTurns() - soldier.getLastOiledTurn() >= difference) soldier.setFlammable(false);
        }
    }
}
