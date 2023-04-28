package org.example.Model;

public enum TileStructure {
    EARTH(false, "earth", "48;2;139;69;19", true, "nothing"),
    PEBBLE(false, "pebble", "100;30", true, "nothing"),
    ROCK(false, "rock", "47;30", true, "stone"),
    STONE(false, "stone", "48;2;64;64;64", false, "nothing"),
    IRON(false, "iron", "44;30", true, "iron"),
    GRASS(false, "grass", "48;2;0;130;0", true, "nothing"),
    MEADOW(false, "meadow", "43;30", true, "nothing"),
    DENSE_MEADOW(false, "denseMeadow", "48;2;255;165;0;30", true, "nothing"),
    OIL(true, "oil", "41", true, "oil"),
    SHALLOW_WATER(true, "shallowWater", "46;30", true, "nothing"),
    PLAIN(true, "plain", "48;2;0;80;0", false, "pitch"),
    RIVER(true, "river", "46;30", false, "nothing"),
    SMALL_LAKE(true, "smallLake", "48;2;0;0;139", false, "nothing"),/////
    BIG_LAKE(true, "bigLake", "48;2;0;0;139", false, "nothing"),/////
    BEACH(true, "beach", "48;2;173;216;230;30", true, "nothing"),
    SEA(true, "sea", "48;2;0;0;250", false, "nothing"),
    ;

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

    private TileStructure(boolean isBlue, String name, String colorString, boolean canBeCrossed, String resource) {
        this.isBlue = isBlue;
        this.name = name;
        this.colorString = colorString;
        this.canBeCrossed = canBeCrossed;
        this.resource = resource;
    }

    public static TileStructure getTileStructureByString(String type){
        TileStructure tileStructure = null;
        for(TileStructure ts : TileStructure.values()) if (ts.name.equals(type)) tileStructure = ts;
        return tileStructure;
    }
}
