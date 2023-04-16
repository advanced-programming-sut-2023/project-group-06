package org.example.Model.BuildingGroups;

import org.example.Model.Asset;
import org.example.Model.Kingdom;

public class Storage extends Building{
    int capacity;
    Asset asset;

    public Storage(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
    }
}
