package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Trap extends Building{
    private boolean canBeSeenByEnemy = false;
    public Trap(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
    }

    public boolean canBeSeenByEnemy() {
        return canBeSeenByEnemy;
    }

    public void setCanBeSeenByEnemy(boolean canBeSeenByEnemy) {
        this.canBeSeenByEnemy = canBeSeenByEnemy;
    }
}
