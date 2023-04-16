package org.example.Model;

public class Person {
    int XCoordinate;
    int YCoordinate;
    Kingdom owner;

    public Person(int XCoordinate, int YCoordinate, Kingdom owner) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.owner = owner;
    }
}
