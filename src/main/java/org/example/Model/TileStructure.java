package org.example.Model;

public enum TileStructure {
    EARTH(false, "earth", "48;2;139;69;19", true),
    PEBBLE(false, "pebble", "100;30", true),
    ROCK(false, "rock", "47;30", true),///////
    STONE(false, "stone", "48;2;64;64;64", false),
    IRON(false, "iron", "43;30", true),
    GRASS(false, "grass", "48;2;0;130;0", true),
    MEADOW(false, "meadow", "43;30", true),
    DENSE_MEADOW(false, "denseMeadow", "48;2;255;165;0;30", true),
    OIL(true, "oil", "41", true),////////////
    PLAIN(true, "plain", "48;2;0;80;0", true),
    RIVER(true, "river", "46;30", false),
    SMALL_LAKE(true, "smallLake", "48;2;0;0;139", false),
    BIG_LAKE(true, "bigLake", "48;2;0;0;139", false),
    BEACH(true, "beach", "48;2;173;216;230;30", true),
    SEA(true, "sea", "48;2;0;0;250", false),
    ;

    private boolean isBlue;
    private String name;
    private String colorString;
    private boolean canBeCrossed;

    public boolean isBlue() {
        return isBlue;
    }

    public String getColorNumber() {
        return colorString;
    }

    private TileStructure(boolean isBlue, String name, String colorString, boolean canBeCrossed) {
        this.isBlue = isBlue;
        this.name = name;
        this.colorString = colorString;
        this.canBeCrossed = canBeCrossed;
    }

    public static TileStructure getTileStructureByString(String type){
        TileStructure tileStructure = null;
        for(TileStructure ts : TileStructure.values()) if (ts.name.equals(type)) tileStructure = ts;
        return tileStructure;
    }
}
