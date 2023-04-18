package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Towers extends Building{
    int fireRange;
    int defendRange;
    boolean isGate;
    int height;

    public Towers(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
    }
}
