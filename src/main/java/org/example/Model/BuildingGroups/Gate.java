package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Gate extends Building{
    boolean isOpen = false;

    public boolean isOpen() {
        return isOpen;
    }

    public void setTheDoor(){
        isOpen = !isOpen;
    }

    public Gate(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate, int direction) {
        super(owner, buildingType, xCoordinate, yCoordinate, direction);
    }
}
