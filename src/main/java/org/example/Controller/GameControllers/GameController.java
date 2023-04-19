package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Tree;
import org.example.Model.BuildingGroups.TreeType;
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

    public static void clearBlock(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null &&
                currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.TREE){
            int xCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getXCoordinate();
            int yCenter = currentGame.getTileByCoordinates(y, x).getBuilding().getYCoordinate();
            int size = currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().getSize();
            for(int i = xCenter - size; i <= xCenter + size; i++){
                for(int j = yCenter - size; j <= yCenter + size; j++){
                    currentGame.getTileByCoordinates(j, i).setBuilding(null);
                }
            }
            currentPlayer.removeBuilding(currentGame.getTileByCoordinates(y, x).getBuilding());
        }
        for(Person person : currentGame.getTileByCoordinates(y, x).getPeople()){
            if(person.getOwner() == currentPlayer) {
                currentGame.getTileByCoordinates(y, x).removePerson(person);
                //remove those people from the kingdom
            }
        }
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
        if(currentGame.getMap()[x][y].getBuilding() != null)
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
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        BuildingType buildingtype = BuildingType.getBuildingTypeByString(type);
        int size = (buildingtype.getSize() - 1) / 2;
        if(buildingtype == null || buildingtype == BuildingType.TREE)
            return Response.INVALID_TYPE;
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
        if(currentPlayer.getMaxPopulation() - currentPlayer.getPopulation() < buildingtype.getWorkerPrice())
            return Response.POPULATION_EXCEEDED;
        if(currentPlayer.getEngineers() < buildingtype.getEngineerPrice())
            return Response.NOT_ENOUGH_ENGINEERS;
        if(currentPlayer.getWealth() < buildingtype.getGoldPrice())
            return Response.NOT_ENOUGH_MONEY;
        if(currentPlayer.getResourceAmountByType(buildingtype.getResourcesPrice().getType()) < buildingtype.getResourcesPrice().getAmount())
            return Response.NOT_ENOUGH_RESOURCES;
        Building building = new Building(currentPlayer, buildingtype, x, y);
        currentPlayer.payResource(buildingtype.getResourcesPrice());
        for(int i = x - size; i <= x + size; i++){
            for(int j = y - size; j <= y + size; j++){
                currentGame.getTileByCoordinates(j, i).setBuilding(building);
            }
        }
        currentPlayer.getBuildings().add(building);
        currentPlayer.addEngineers(-1 * buildingtype.getEngineerPrice());
        currentPlayer.addPopulation(buildingtype.getWorkerPrice());
        return Response.DROP_BUILDING_SUCCESSFUL;
    }

    public static Response putMainCastle(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String color = Controller.makeEntryValid(matcher.group("color"));
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        //todo
        return null;
    }

    public static Response selectBuilding(Matcher matcher){
        return null;
        //todo
    }

    public static Response selectUnit(Matcher matcher){
        return null;
        //todo
    }

    public static Response selectPerson(Matcher matcher){
        return null;
        //todo
    }

    public static Response nextTurn(){
        //computeHappiness
        //computeDamages
        //computeFoods     //check if food is out , foodRate must be set on -2
        //computeFears
        //computeTaxes     //check if you lost all money , taxRate must be set on 0
        //autoProducing
        //computePopulation  //soldiers and engineers
        //check if a king died

        //changeTurn
        currentGame.nextTurn();
        //initialize some fields
        return null;
        //todo
    }

    private static Response computeHappiness(){
        return null;
        //todo
        //religious buildings
    }

    private static Response computeDamages(){
        return null;
        //todo
    }

    private static void computeFoods(){
        float rate = 1 + (currentPlayer.getFoodRate() / 2);
        int totalFoodUsage = (int)(rate * currentPlayer.getPopulation());
        currentPlayer.eatFoods(totalFoodUsage);
        currentPlayer.addToHappiness(currentPlayer.getFoodDiversity() - 1);
        currentPlayer.addToHappiness(currentPlayer.getFoodRate() * 4);
    }

    private static Response computeFears(){
        return null;
        //todo
    }

    private static void computeTaxes(){
        int tax = currentPlayer.getTax();
        double addToWealth = 0;
        int addToHappiness = 0;
        if(tax > 3)
            addToHappiness = -4 * tax + 8;
        else if(tax > 0)
            addToHappiness = -2 * tax;
        else addToHappiness = -2 * tax + 1;
        if(tax > 0)
            addToWealth = 0.2 * tax + 0.4;
        else if(tax < 0)
            addToWealth = -0.2 * tax + 0.4;
        currentPlayer.addToWealth((int)(addToWealth * currentPlayer.getPopulation()));
        currentPlayer.addToHappiness(addToHappiness * currentPlayer.getPopulation());
    }

    private static Response computePopulations(){
        return null;
        //todo
        //not sure if this s necessary
    }

    private static Response autoProducing(){
        return null;
        //todo
    }

    private static Response endGame(){
        return null;
        //todo
        //set all static fields inside controllers equal to zero
    }
}
