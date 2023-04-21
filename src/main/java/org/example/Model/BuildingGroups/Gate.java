package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Gate extends Building{
    public Gate(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate, int direction) {
        super(owner, buildingType, xCoordinate, yCoordinate, direction);
    }
}
