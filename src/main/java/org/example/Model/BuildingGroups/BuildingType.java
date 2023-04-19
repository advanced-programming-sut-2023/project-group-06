package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;
import org.example.Model.ResourcesType;
import org.example.Model.TileStructure;
import org.example.View.Response;

public enum BuildingType {
    TREE(100, 0, 1, null, 0, 0, 0, 0, false, "tree"),
    // STAIR and WALL are temporary and need to be fixed !!! todo
    STAIR(100, 0, 1, null, 0, 0, 0, 0, false, "stair"),
    WALL(100, 0, 1, null, 0, 0, 0, 0, false, "wall"),
    ;
    int size;
    int direction;/////////////////////////////////////////not all the buildings have direction
    int hitPoint;
    int goldPrice;
    Resources resourcesPrice;
    int workerPrice;
    int engineerPrice;
    int happinessIncrease;
    boolean canYouEnterIt;
    String name;

    BuildingType(int hitPoint, int goldPrice, int size,
                 ResourcesType resourcesPriceType, int resourcePriceAmount, int workerPrice,
                 int engineerPrice, int happinessIncrease, boolean canYouEnterIt, String name) {
        this.hitPoint = hitPoint;
        this.goldPrice = goldPrice;
        this.resourcesPrice =  new Resources(resourcePriceAmount, resourcesPriceType);
        this.workerPrice = workerPrice;
        this.engineerPrice = engineerPrice;
        this.happinessIncrease = happinessIncrease;
        this.size = size;
        this.canYouEnterIt = canYouEnterIt;
        this.name = name;
    }

    public static BuildingType getBuildingTypeByString(String type){
        BuildingType buildingType = null;
        for(BuildingType ts : BuildingType.values()) if (ts.name.equals(type)) buildingType = ts;
        return buildingType;
    }

    public static boolean checkGround(BuildingType buildingType, TileStructure tileStructure){
        if(tileStructure.isBlue())
            return false;
        return true;
    }

    public int getWorkerPrice() {
        return workerPrice;
    }

    public int getEngineerPrice() {
        return engineerPrice;
    }

    public int getGoldPrice() {
        return goldPrice;
    }

    public Resources getResourcesPrice() {
        return resourcesPrice;
    }

    public int getSize() {
        return size;
    }
}
