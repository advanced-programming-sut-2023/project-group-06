package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.View.Response;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {
    public static Game currentGame;
    public static Kingdom currentPlayer;

    public static Response initializeMap(Matcher matcher){
        String widthString = matcher.group("width");
        String heightString = matcher.group("height");
        int width = Integer.parseInt(widthString);
        int height = Integer.parseInt(heightString);
        currentGame.initializeMap(width, height);
        return null;
        //todo
    }

    public static void setDefaultMap(Tile[][] defaultMap, int defaultMapWidth, int defaultMapHeight){
        for(int i = 0; i < defaultMapWidth; i++){
            for(int j = 0; j < defaultMapHeight; j++){
                currentGame.getMap()[i][j] = defaultMap[i][j];
            }
        }
    }

    public static Response clearBlock(Matcher matcher){
        return null;
        //todo
    }

    public static Response dropRuck(Matcher matcher){
        return null;
        //todo
    }

    public static Response dropTree(Matcher matcher){
        return null;
        //todo
    }

    public static Response setTextureOneTile(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String typeString = matcher.group("type");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        TileStructure type = TileStructure.getTileStructureByString(typeString);
        if(type == null)
            return Response.INVALID_TYPE;
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        //check for troops or other things on the tile
        if(currentGame.getMap()[x][y].getBuilding() != null)
            return Response.SET_TEXTURE_UNDER_BUILDING;
        currentGame.getMap()[x][y] = new Tile(type, x, y);
        return null;//there shouldn't be any massage here
    }

    public static Response setTextureMultipleTiles(Matcher matcher){
        String x1String = matcher.group("x1");
        String x2String = matcher.group("x2");
        String y1String = matcher.group("y1");
        String y2String = matcher.group("y2");
        String typeString = matcher.group("type");
        int x1 = Integer.parseInt(x1String);
        int x2 = Integer.parseInt(x2String);
        int y1 = Integer.parseInt(y1String);
        int y2 = Integer.parseInt(y2String);
        TileStructure type = TileStructure.getTileStructureByString(typeString);
        if(type == null)
            return Response.INVALID_TYPE;
        if(x1 > x2 || y1 > y2 || x1 < 0 || y1 < 0 || x2 >= currentGame.getMapWidth() || y2 >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        //check for troops or other things on the tiles
        for(int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                currentGame.getMap()[i][j] = new Tile(type, i, j);
            }
        }
        return null;//there shouldn't be any massage here
    }

    public static Response dropBuilding(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String typeString = matcher.group("type");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        BuildingType buildingtype = BuildingType.getBuildingTypeByString(typeString);
        if(buildingtype == null)
            return Response.INVALID_TYPE;
        if(x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return Response.INVALID_COORDINATES;
        if(!BuildingType.checkGround(buildingtype, currentGame.getTileByCoordinates(y, x).getType()))
            return Response.INVALID_GROUND;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.BUILDING_ALREADY_EXIST;
        if(currentPlayer.getMaxPopulation() - currentPlayer.getPopulation() < buildingtype.getEngineerPrice() + buildingtype.getWorkerPrice())
            return Response.POPULATION_EXCEEDED;/////////////////////I need to recheck this
        if(currentPlayer.getWealth() < buildingtype.getGoldPrice())
            return Response.NOT_ENOUGH_MONEY;
        if(currentPlayer.getResourceAmountByType(buildingtype.getResourcesPrice().getType()) < buildingtype.getResourcesPrice().getAmount())
            return Response.NOT_ENOUGH_RESOURCES;
        Building building = new Building(currentPlayer, buildingtype, x, y);
        currentPlayer.payResource(buildingtype.getResourcesPrice());
        currentGame.getTileByCoordinates(y, x).setBuilding(building);
        currentPlayer.getBuildings().add(building);
        currentPlayer.addEngineers(-1 * buildingtype.getEngineerPrice());
        currentPlayer.addPopulation(buildingtype.getWorkerPrice());
        return Response.DROP_BUILDING_SUCCESSFUL;
    }

    public static Response selectBuilding(Matcher matcher){
        return null;
        //todo
    }

    public static Response selectUnit(Matcher matcher){
        return null;
        //todo
    }

    public static Response moveUnit(Matcher matcher){
        return null;
        //todo
        //after select unit
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

    public static Response selectPerson(Matcher matcher){
        return null;
        //todo
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

    public static Response nextTurn(){
        return null;
        //todo
        //computeHappiness
        //computeDamages
        //computeFoods     //check if food is out , foodRate must be set on -2
        //computeFears
        //computeTaxes     //check if you lost all money , taxRate must be set on 0
        //autoProducing
        //computePopulation

        //changeTurn
        //initialize some fields
    }

    private static Response computeHappiness(){
        return null;
        //todo
    }

    private static Response computeDamages(){
        return null;
        //todo
    }

    private static Response computeFoods(){
        return null;
        //todo
    }

    private static Response computeFears(){
        return null;
        //todo
    }

    private static Response computeTaxes(){
        return null;
        //todo
    }

    private static Response computePopulations(){
        return null;
        //todo
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
