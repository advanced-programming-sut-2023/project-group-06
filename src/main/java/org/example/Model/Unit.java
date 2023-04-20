package org.example.Model;

public class Unit {
    int XCoordinate;
    int YCoordinate;
    private Kingdom owner;

    public Unit(int XCoordinate, int YCoordinate, Kingdom owner) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
    }

    public Kingdom getOwner() {
        return owner;
    }
}
