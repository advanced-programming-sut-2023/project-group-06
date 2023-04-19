package org.example.Model;

public enum TileStructure {
    EARTH(false, "earth", "48;2;139;69;19"),
    PEBBLE(false, "pebble", "100;30"),
    ROCK(false, "rock", "47;30"),
    STONE(false, "stone", "48;2;64;64;64"),
    IRON(false, "iron", "43;30"),
    GRASS(false, "grass", "48;2;0;130;0"),
    MEADOW(false, "meadow", "43;30"),
    DENSE_MEADOW(false, "denseMeadow", "48;2;255;165;0;30"),
    OIL(true, "oil", "41"),
    PLAIN(true, "plain", "48;2;0;80;0"),
    RIVER(true, "river", "46;30"),
    SMALL_LAKE(true, "smallLake", "48;2;0;0;139"),
    BIG_LAKE(true, "bigLake", "48;2;0;0;139"),
    BEACH(true, "beach", "48;2;173;216;230;30"),
    SEA(true, "sea", "48;2;0;0;250"),
    ;

    private boolean isBlue;
    private String name;
    private String colorString;

    public boolean isBlue() {
        return isBlue;
    }

    public String getColorNumber() {
        return colorString;
    }

    private TileStructure(boolean isBlue, String name, String colorString) {
        this.isBlue = isBlue;
        this.name = name;
        this.colorString = colorString;
    }

    public static TileStructure getTileStructureByString(String type){
        TileStructure tileStructure = null;
        for(TileStructure ts : TileStructure.values()) if (ts.name.equals(type)) tileStructure = ts;
        return tileStructure;
    }
}
