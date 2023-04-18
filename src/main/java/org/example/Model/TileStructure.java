package org.example.Model;

public enum TileStructure {
    EARTH(false, "earth"),
    PEBBLE(false, "pebble"),
    ROCK(false, "rock"),
    STONE(false, "stone"),
    IRON(false, "iron"),
    GRASS(false, "grass"),
    MEADOW(false, "meadow"),
    DENSE_MEADOW(false, "denseMeadow"),
    OIL(true, "oil"),
    PLAIN(true, "plain"),
    RIVER(true, "river"),
    SMALL_LAKE(true, "smallLake"),
    BIG_LAKE(true, "bigLake"),
    BEACH(true, "beach"),
    SEA(true, "sea")
    ;

    private boolean isBlue;
    private String name;

    public boolean isBlue() {
        return isBlue;
    }

    private TileStructure(boolean isBlue, String name) {
        this.isBlue = isBlue;
        this.name = name;
    }

    public static TileStructure getTileStructureByString(String type){
        TileStructure tileStructure = null;
        for(TileStructure ts : TileStructure.values()) if (ts.name.equals(type)) tileStructure = ts;
        return tileStructure;
    }
}
