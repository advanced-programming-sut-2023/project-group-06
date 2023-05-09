package org.example.Model;

import org.example.Controller.GameControllers.GameController;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Gate;
import org.example.Model.BuildingGroups.Towers;

import java.util.ArrayList;

public class Tile {
    private TileStructure type;
    private Building building;
    private int xCoordinate;
    private int yCoordinate;
    private int height;
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Unit> nonSoldierUnits = new ArrayList<>();
    private ArrayList<Unit> allUnits = new ArrayList<>();
    private boolean isDitch = false;
    private boolean isToFill = false;
    private int ditchDelay = 3;
    private Equipment equipment = null;

    public Tile(TileStructure type, int xCoordinate, int yCoordinate) {
        this.type = type;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        if (!this.type.CanBeCrossed()){
            height = -2;
        }
        else height = 0;
    }

    public boolean isDitch() {
        return isDitch;
    }

    public void setDitchDelay(int ditchDelay) {
        this.ditchDelay = ditchDelay;
    }

    public void subDitchDelay(){
        this.ditchDelay -= 1;
    }

    public void addDitchDelay(){
        this.ditchDelay++;
    }

    public int getDitchDelay() {
        return ditchDelay;
    }

    public void setDitch(boolean ditch) {
        isDitch = ditch;
    }

    public TileStructure getType() {
        return type;
    }

    public Building getBuilding() {
        return building;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public ArrayList<Unit> getAllUnits() {
        return allUnits;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public void setType(TileStructure type) {
        this.type = type;
        if (!type.CanBeCrossed()) height = -2;
        else height = 0;
    }

    public void setBuilding(Building building) {
        this.building = building;
        if(building == null) return;
        if(building.getBuildingType() == BuildingType.BRIDGE || building.getBuildingType() == BuildingType.KILLING_PIT) height = 0;
        if(building.getBuildingType() == BuildingType.OX_TETHER){
            Unit cow = new Unit(building.getXCoordinate(), building.getYCoordinate(), building.getOwner(), UnitType.COW);
            GameController.currentGame.getTileByCoordinates(building.getYCoordinate(), building.getXCoordinate()).getAllUnits().add(cow);
            GameController.currentPlayer.getCows().add(cow);
        }
        if (!type.CanBeCrossed()) return;
        if (building.getBuildingType() == BuildingType.STAIR) height = 1;
        else if (building.getBuildingType() == BuildingType.WALL) height = 2;
        else if (building instanceof Towers || building instanceof Gate) height = 3;
        else if(building.getBuildingType().isCanYouEnterIt()) height = 0;
        else height = -2;
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
        allUnits.add(soldier);
    }

    public void addUnit(Unit unit){
        allUnits.add(unit);
        unit.setXCoordinate(this.getXCoordinate());
        unit.setYCoordinate(this.getYCoordinate());
        if(unit instanceof Soldier)
            soldiers.add((Soldier) unit);
    }

    public void addToNonSoldierUnits(Unit unit) {
        this.nonSoldierUnits.add(unit);
        this.allUnits.add(unit);
    }

    public void removeFromNonSoldierUnits(Unit unit) {
        this.nonSoldierUnits.remove(unit);
    }

    public void removeSoldier(Soldier soldier) {
        this.soldiers.remove(soldier);
        this.allUnits.remove(soldier);
    }

    public void removeUnit(Unit unit){
        this.allUnits.remove(unit);
        if(unit instanceof Soldier)
            this.soldiers.remove((Soldier) unit);
    }

    public boolean existEnemyOnThisTile(Kingdom ourKingdom) {
        for (Soldier soldier : soldiers) {
            if (soldier.getOwner() != ourKingdom) return true;
        }
        return false;
    }

    public boolean checkForVisibleSoldiers(Kingdom kingdom){
        boolean result = false;
        for(Soldier soldier : soldiers){
            if(!(soldier.getUnitType() == UnitType.ASSASSIN && soldier.getOwner() != kingdom)) {
                result = true;
                break;
            }
            else{
                if(GameController.findNearestEnemyTo(soldier, 4) != null) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean checkForCows(){
        for(Unit unit : this.allUnits){
            if(unit.getUnitType() == UnitType.COW)
                return true;
        }
        return false;
    }

    public boolean checkForEngineers(){
        for(Unit unit : this.allUnits){
            if(unit.getUnitType() == UnitType.ENGINEER)
                return true;
        }
        return false;
    }

    public boolean checkForEquipment() {
        for (Unit unit : this.allUnits) {
            if (unit instanceof Equipment) return true;
        }
        return false;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String toString() {
        return "(" + this.getX() + this.getY() + this.getType() + this.getHeight() + ")";
    }
}
