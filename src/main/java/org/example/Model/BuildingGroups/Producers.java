package org.example.Model.BuildingGroups;

import org.example.Model.Asset;
import org.example.Model.Kingdom;
import org.example.Model.Resources;
import org.example.View.Menu;
import org.example.View.MenuType;

public class Producers extends Building{
    Resources resourcesInput;
    Asset assetOutput;
    Asset storage;
    boolean isClickable;
    MenuType menuType = null;

    public Producers(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
    }
}
