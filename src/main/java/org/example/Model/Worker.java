package org.example.Model;

import org.example.Model.BuildingGroups.Building;

public class Worker extends Unit {
    Building workPlace = null;

    public Worker(int XCoordinate, int YCoordinate, Kingdom owner) {
        super(XCoordinate, YCoordinate, owner);
    }
}
