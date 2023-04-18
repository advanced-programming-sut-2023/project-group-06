package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.Game;
import org.example.Model.Kingdom;
import org.example.Model.Soldier;
import org.example.Model.Tile;
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
        return null;
        //todo
    }

    public static Response setTextureMultipleTiles(Matcher matcher){
        return null;
        //todo
    }

    public static Response dropBuilding(Matcher matcher){
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        String typeString = matcher.group("type");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        BuildingType buildingtype = BuildingType.getBuildingTypeByString(typeString);
        //check format
        if(!BuildingType.checkGround(buildingtype, currentGame.getTileByCoordinates(y, x).getType()))
            return Response.INVALID_GROUND;
        if(currentGame.getTileByCoordinates(y, x).getBuilding() != null)
            return Response.BUILDING_ALREADY_EXIST;
        if(currentPlayer.getMaxPopulation() - currentPlayer.getPopulation() < buildingtype.getEngineerPrice() + buildingtype.getWorkerPrice())
            return Response.POPULATION_EXCEEDED;
        if(currentPlayer.getWealth() < buildingtype.getGoldPrice())
            return Response.NOT_ENOUGH_MONEY;
        Building building = new Building(currentPlayer, buildingtype, x, y);
        currentGame.getTileByCoordinates(y, x).setBuilding(building);
        currentPlayer.getBuildings().add(building);
        return Response.DROP_BUILDING_SUCCESSFUL;
    }

    public static Response selectBuilding(Matcher matcher){
        return null;
        //todo
    }

    public static Response createUnit(Matcher matcher){
        return null;
        //todo
        //after select building
    }

    public static Response repair(){
        return null;
        //todo
        //after select building
    }

    public static Response selectUnit(Matcher matcher){
        return null;
        //todo
    }

    public static Response moveUnit(Matcher matcher){
        return null;
        //todo
    }

    public static Response patrolUnit(Matcher matcher){
        return null;
        //todo
    }

    public static Response throwLadder(){
        return null;
        //todo
    }

    public static Response digDitch(Matcher matcher){
        return null;
        //todo
    }

    public static Response setUnitPosition(Matcher matcher){
        return null;
        //todo
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

    public static Response enterKingdomMenu(){
        return null;
        //todo
    }

    public static Response enterMapMenu(){
        return null;
        //todo
    }

    public static Response enterShopMenu(){
        return null;
        //todo
    }

    public static Response enterTradeMenu(){
        return null;
        //todo
    }

    public static Response nextTurn(){
        return null;
        //todo
        //computeHappiness
        //computeDamages
        //computeFoods
        //computeFears
        //computeTaxes
        //autoProducing
        //computePopulation

        //changeTurn
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
}
