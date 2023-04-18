package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Trap extends Building{
    int damage;

    public Trap(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
    }
}
