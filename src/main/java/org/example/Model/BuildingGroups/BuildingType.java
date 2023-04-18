package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;
import org.example.Model.ResourcesType;
import org.example.Model.TileStructure;
import org.example.View.Response;

public enum BuildingType {
    ;
    int size;
    int direction;
    int hitPoint;
    int goldPrice;
    Kingdom owner;
    Resources resourcesPrice;
    int workerPrice;
    int engineerPrice;
    int happinessIncrease;
    String name;

    BuildingType(int direction, int hitPoint, int goldPrice, Kingdom owner, int size,
                 ResourcesType resourcesPriceType, int resourcePriceAmount, int workerPrice, int engineerPrice, int happinessIncrease) {
        this.direction = direction;
        this.hitPoint = hitPoint;
        this.goldPrice = goldPrice;
        this.owner = owner;
        this.resourcesPrice =  new Resources(resourcePriceAmount, resourcesPriceType);
        this.workerPrice = workerPrice;
        this.engineerPrice = engineerPrice;
        this.happinessIncrease = happinessIncrease;
        this.size = size;
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
}
