package org.example.Model;

public class Unit {
    int XCoordinate;
    int YCoordinate;
    private Kingdom owner;
    private boolean isEngineer;

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
    }

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner, boolean isEngineer) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
        this.isEngineer = isEngineer;
    }

    public Kingdom getOwner() {
        return owner;
    }
}
