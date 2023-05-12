package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Towers extends Building{
    /*
         0
       3 x 1
         2
    haveLather(i) : lather & (1 << i) != 0
    */
    public int lather;
    int fireRange;
    int defendRange;

    public Towers(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate) {
        super(owner, buildingType, xCoordinate, yCoordinate);
        switch(buildingType){
            case ROUND_TOWER:
                fireRange = 3;
                break;
            case PERIMETER_TOWER:
                fireRange = 4;
                break;
            case DEFENSE_TURRET:
                fireRange = 2;
                break;
            case SQUARE_TOWER:
                fireRange = 3;
                break;
            case LOOKOUT_TOWER:
                fireRange = 5;
                break;
            case MAIN_CASTLE:
                fireRange = 0;
                break;
        }
    }

    public Towers(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate, int direction){
        super(owner, buildingType, xCoordinate, yCoordinate, direction);
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getFireRange() {
        return fireRange;
    }
}
