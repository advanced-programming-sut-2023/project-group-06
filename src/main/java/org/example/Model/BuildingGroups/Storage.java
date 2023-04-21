package org.example.Model.BuildingGroups;

import org.example.Model.Asset;
import org.example.Model.Kingdom;

import java.util.ArrayList;

public class Storage extends Building{
    int capacity;
    ArrayList<Asset> assets = new ArrayList<>();

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public Storage(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
    }
}
