package org.example.Model.BuildingGroups;

import org.example.Model.Asset;
import org.example.Model.Kingdom;

import java.util.ArrayList;

public class Storage extends Building{
    private int capacity;
    private int stored = 0;
    private ArrayList<Asset> assets = new ArrayList<>();

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStored() {
        return stored;
    }

    public void addToStored(int amount){
        stored += amount;
    }

    public Storage(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
        switch (buildingType){
            case ARMORY:
                capacity = 30;
                break;
            case GRANARY:
                capacity = 50;
                break;
            case STOCKPILE:
                capacity = 75;
                break;
        }
    }
}
