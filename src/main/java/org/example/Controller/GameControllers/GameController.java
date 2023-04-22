package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.Model.BuildingGroups.*;
import org.example.View.Response;

import java.util.ArrayList;
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

    public static void setDefaultMap(Tile[][] defaultMap, int defaultMapWidth, int defaultMapHeight){
        currentGame.setMap(defaultMap, defaultMapWidth, defaultMapHeight);
    }

    public static Response clearBlock(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        else if(currentGame.getTileByCoordinates(y, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.TREE &&
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
            int xCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getXCoordinate();
            int yCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getYCoordinate();
            int size = (currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().getSize() - 1) / 2;
            for(int i = xCenter - size; i <= xCenter + size; i++){
                for(int j = yCenter - size; j <= yCenter + size; j++){
                    currentGame.getTileByCoordinates(j, i).setBuilding(null);
                }
            }
        }
        for(Unit unit : currentGame.getTileByCoordinates(y, x).getUnits()){
            if(unit.getOwner() == currentPlayer) {
                currentGame.getTileByCoordinates(y, x).removePerson(unit);
                //remove those people from the kingdom
                //if person instanceof soldier remove from soldiers as well
            }
        }
        //todo
        return Response.CLEAR_SUCCESSFUL;
    }

    public static Response dropUnit(Matcher matcher){
        return null;
        //todo
    }

    public static Response dropRuck(Matcher matcher){
        return null;
        //todo
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
        Tree tree = new Tree(x, y, type);
        currentGame.getTileByCoordinates(y, x).setBuilding(tree);
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
        //check for troops or other things on the tile
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.SET_TEXTURE_UNDER_BUILDING;////what if there is a tree here?
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
        //check for troops or other things on the tiles
        for(int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                if(currentGame.getTileByCoordinates(j, i).getBuilding() != null)
                    return Response.SET_TEXTURE_UNDER_BUILDING;////what if there is a tree here?
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
        String direction = Controller.makeEntryValid("direction");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        BuildingType buildingtype = BuildingType.getBuildingTypeByString(type);
        //check for troops
        if(buildingtype == null || buildingtype == BuildingType.TREE)
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
        if (Producers.class.equals(buildingtype.getBuildingClass()))
            building = new Producers(currentPlayer, buildingtype, x, y);
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
        currentPlayer.addAvailableEngineers(-1 * buildingtype.getEngineerPrice());
        currentPlayer.addToPopulation(buildingtype.getWorkerPrice() + buildingtype.getEngineerPrice());
        if(buildingtype == BuildingType.CHURCH)
            currentPlayer.addToHappinessIncrease(2);
        if(buildingtype == BuildingType.CATHEDRAL)
            currentPlayer.addToHappinessIncrease(4);
        if(buildingtype == BuildingType.HOVEL || buildingtype == BuildingType.SMALL_STONE_GATEHOUSE)
            currentPlayer.addToMaxPopulation(8);
        if(buildingtype == BuildingType.BIG_STONE_GATEHOUSE)
            currentPlayer.addToMaxPopulation(10);
        if(buildingtype == BuildingType.STABLE)
            currentPlayer.addToHorseNumber(4);
        //if(building type == INN) ...
        //if(building type == ...) ...
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
        if(x < 0 || y < 0 || x >= currentGame.getMapWidth() || y >= currentGame.getMapHeight())
            return Response.INVALID_INPUT;
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for(Soldier soldier : currentGame.getTileByCoordinates(y, x).getSoldiers()){
            if(soldier.getOwner() == currentPlayer)
                soldiers.add(soldier);
        }
        if(soldiers.size() == 0)
            return Response.NO_SOLDIER_ON_THE_TILE;
        SoldierController.soldiers = soldiers;
        return Response.SELECT_SOLDIER_SUCCESSFUL;
    }

    public static Response nextTurn(){
        if(currentGame.getTurnIndex() == currentGame.getNumberOfPlayers() - 1){
            for(Kingdom kingdom : currentGame.getKingdoms()) {
                //computeDamages
                kingdom.addToHappiness(kingdom.getHappinessIncrease());//inn ........
                computeFoods(kingdom);
                if (kingdom.getTotalFoodAmount() == 0)
                    kingdom.setFoodRate(-2);
                //computeFears
                computeTaxes(kingdom);
                if (kingdom.getWealth() == 0)
                    kingdom.setTax(0);
                autoProducing(kingdom);
                //computePopulation  //soldiers and engineers
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

    private static Response computeDamages(){
        return null;
        //todo
    }

    private static void computeFoods(Kingdom player){
        float rate = 1 + (player.getFoodRate() / 2);
        int totalFoodUsage = (int)(rate * player.getMaxPopulation());
        player.eatFoods(totalFoodUsage);
        player.addToHappiness(player.getFoodDiversity() - 1);
        player.addToHappiness(player.getFoodRate() * 4);
    }

    private static Response computeFears(Kingdom player){
        return null;
        //todo
    }

    private static void computeTaxes(Kingdom player){
        int tax = player.getTax();
        double addToWealth = 0;
        int addToHappiness;
        if(tax > 3)
            addToHappiness = -4 * tax + 8;
        else if(tax > 0)
            addToHappiness = -2 * tax;
        else addToHappiness = -2 * tax + 1;
        if(tax > 0)
            addToWealth = 0.2 * tax + 0.4;
        else if(tax < 0)
            addToWealth = -0.2 * tax + 0.4;
        player.addToWealth((int)(addToWealth * player.getMaxPopulation()));
        player.addToHappiness(addToHappiness * player.getMaxPopulation());
    }

    private static void autoTeleportByCow(Kingdom player){
        //todo
    }

    private static void autoProducing(Kingdom player){
        for(Building building : player.getBuildings()){
            if(/*building.getBuildingType().getBuildingClass() == Producers.class*/building instanceof Producers){
                ResourcesType InputType = ((Producers) building).getResourcesInput().getType();
                int InputAmount = ((Producers) building).getResourcesInput().getAmount();
                Asset output1 = ((Producers) building).getAssetOutput();
                Asset output2 = ((Producers) building).getAssetOutput2();
                //if(building.getBuildingType() == BuildingType.INN)
                if(building.getBuildingType() == BuildingType.OIL_SMELTER || building.getBuildingType() == BuildingType.QUARRY)
                    ((Producers) building).addToStored(Math.min(((Producers) building).getCapacity() - ((Producers) building).getStored(), output1.getAmount()));
                //if(building.getBuildingType() == BuildingType....)
                else {
                    boolean canPay = true;
                    if (InputAmount != 0) {
                        if (player.getResourceAmountByType(InputType) < InputAmount)
                            canPay = false;
                        if(canPay)
                            player.payResource(((Producers) building).getResourcesInput());
                    }
                    //what if storages are full
                    if(canPay) {
                        player.addAsset(output1);
                        if (output2 != null)
                            player.addAsset(output2);
                    }
                }
            }
        }
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
}
