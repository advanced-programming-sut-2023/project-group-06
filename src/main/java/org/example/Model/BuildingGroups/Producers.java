package org.example.Model.BuildingGroups;

import org.example.Model.*;
import org.example.View.Menu;
import org.example.View.MenuType;

public class Producers extends Building{
    Resources resourcesInput;
    double resourceUsageRate;
    Asset assetOutput;
    Asset assetOutput2;
    Asset storage;
    int capacity;
    int stored;
    boolean isClickable;
    MenuType menuType = null;

    public Resources getResourcesInput() {
        return resourcesInput;
    }

    public double getResourceUsageRate() {
        return resourceUsageRate;
    }

    public Asset getAssetOutput2() {
        return assetOutput2;
    }

    public Asset getAssetOutput() {
        return assetOutput;
    }

    public Producers(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
        switch (buildingType){
            case MILL:
                resourcesInput = new Resources(8, ResourcesType.WHEAT);
                assetOutput = new Resources(5, ResourcesType.FLOUR);
                isClickable = false;
                break;
            case QUARRY:
                resourcesInput = new Resources(0, null);
                assetOutput = new Resources(10, ResourcesType.STONE);
                isClickable = false;
                capacity = 60;
                break;
            case IRON_MINE:
                resourcesInput = new Resources(0, null);
                assetOutput = new Resources(6, ResourcesType.IRON);
                isClickable = false;
                break;
            case PITCH_RIG:
                resourcesInput = new Resources(0, null);
                assetOutput = new Resources(3, ResourcesType.PITCH);
                isClickable = false;
                break;
            case WOODCUTTERS:
                resourcesInput = new Resources(0, null);
                assetOutput = new Resources(9, ResourcesType.WOOD);
                break;
            case ARMORER:
                resourcesInput = new Resources(6, ResourcesType.IRON);
                assetOutput = new Weapon(1, WeaponType.METAL_ARMOR);
                isClickable = false;
                break;
            case FLETCHER:
                resourcesInput = new Resources(5, ResourcesType.WOOD);
                assetOutput = new Weapon(2, WeaponType.BOW);
                isClickable = false;
                break;
            case POLETURNER:
                resourcesInput = new Resources(7, ResourcesType.WOOD);
                assetOutput = new Weapon(1, WeaponType.SPEAR);
                assetOutput2 = new Weapon(1, WeaponType.PIKE);
                isClickable = false;
                break;
            case OIL_SMELTER:
                //todo
                break;
            case HOPS_FARMER:
                resourcesInput = new Resources(0, null);
                assetOutput = new Resources(4, ResourcesType.HOPS);
                isClickable = false;
                break;
            case APPLE_ORCHARD:
                resourcesInput = new Resources(0, null);
                assetOutput = new Food(6, FoodType.APPLES);
                isClickable = false;
                break;
            case DIARY_FARMER:
                resourcesInput = new Resources(0, null);
                assetOutput = new Food(3, FoodType.CHEESE);
                break;
            case HUNTERS_POST:
                resourcesInput = new Resources(0, null);
                assetOutput = new Food(3, FoodType.MEAT);
                isClickable = false;
                break;
            case WHEAT_FARMER:
                resourcesInput = new Resources(0, null);
                assetOutput = new Resources(6, ResourcesType.WHEAT);
                isClickable = false;
                break;
            case BAKERY:
                resourcesInput = new Resources(7, ResourcesType.FLOUR);
                assetOutput = new Food(4, FoodType.BREAD);
                isClickable = false;
                break;
            case BREWER:
                resourcesInput = new Resources(6, ResourcesType.HOPS);
                assetOutput = new Resources(4, ResourcesType.ALE);
                isClickable = false;
                break;
            case INN:
                //todo
                break;
        }
    }
}
