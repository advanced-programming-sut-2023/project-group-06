package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;

public enum BuildingType {
    ;
    int direction;
    int hitPoint;
    int goldPrice;
    Kingdom owner;
    Resources resourcesPrice;
    int workerPrice;
    int engineerPrice;
    int happinessIncrease;

    BuildingType(int direction, int hitPoint, int goldPrice, Kingdom owner,
                 Resources resourcesPrice, int workerPrice, int engineerPrice, int happinessIncrease) {
        this.direction = direction;
        this.hitPoint = hitPoint;
        this.goldPrice = goldPrice;
        this.owner = owner;
        this.resourcesPrice = resourcesPrice;
        this.workerPrice = workerPrice;
        this.engineerPrice = engineerPrice;
        this.happinessIncrease = happinessIncrease;
    }
}
