package org.example.Model;

import org.example.View.Graphics.SuperImage;

public enum TileStructure {
    EARTH(SuperImage.EARTH, false, "earth", "48;2;139;69;19", true, "nothing"),
    PEBBLE(SuperImage.PEBBLE, false, "pebble", "100;30", true, "nothing"),
    ROCK(SuperImage.ROCK, false, "rock", "47;30", true, "stone"),
    STONE(SuperImage.STONE, false, "stone", "48;2;64;64;64", false, "nothing"),
    IRON(SuperImage.IRON, false, "iron", "44;30", true, "iron"),
    GRASS(SuperImage.GRASS, false, "grass", "48;2;0;130;0", true, "nothing"),
    MEADOW(SuperImage.MEADOW, false, "meadow", "43;30", true, "nothing"),
    DENSE_MEADOW(SuperImage.DENSE_MEADOW, false, "denseMeadow", "48;2;255;165;0;30", true, "nothing"),
    OIL(SuperImage.OIL, true, "oil", "41", true, "oil"),
    SHALLOW_WATER(SuperImage.SHALLOW_WATER, true, "shallowWater", "46;30", true, "nothing"),
    PLAIN(SuperImage.PLAIN, true, "plain", "48;2;0;80;0", false, "pitch"),
    RIVER(SuperImage.RIVER, true, "river", "46;30", false, "nothing"),
    SMALL_LAKE(SuperImage.SMALL_LAKE, true, "smallLake", "48;2;0;0;139", false, "nothing"),/////
    BIG_LAKE(SuperImage.BIG_LAKE, true, "bigLake", "48;2;0;0;139", false, "nothing"),/////
    BEACH(SuperImage.BEACH, true, "beach", "48;2;173;216;230;30", true, "nothing"),
    SEA(SuperImage.SEA, true, "sea", "48;2;0;0;250", false, "nothing"),
    ;

    SuperImage superImage;
    private boolean isBlue;
    private String name;
    private String colorString;
    private boolean canBeCrossed;
    private String resource;

    public boolean isBlue() {
        return isBlue;
    }

    public String getColorNumber() {
        return colorString;
    }

    public boolean CanBeCrossed() {
        return canBeCrossed;
    }

    public String getResource() {
        return resource;
    }

    private TileStructure(SuperImage superImage, boolean isBlue, String name, String colorString, boolean canBeCrossed, String resource) {
        this.superImage = superImage;
        this.isBlue = isBlue;
        this.name = name;
        this.colorString = colorString;
        this.canBeCrossed = canBeCrossed;
        this.resource = resource;
    }

    public static TileStructure getTileStructureByString(String type) {
        TileStructure tileStructure = null;
        for (TileStructure ts : TileStructure.values()) if (ts.name.equals(type)) tileStructure = ts;
        return tileStructure;
    }

    public SuperImage getSuperImage() {
        return superImage;
    }

    public void setSuperImage(SuperImage superImage) {
        this.superImage = superImage;
    }
}
