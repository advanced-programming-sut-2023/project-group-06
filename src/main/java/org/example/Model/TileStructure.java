package org.example.Model;

public enum TileStructure {
    EARTH(false, "EA"),
    PEBBLE(false, "PE"),
    ROCK(false, "RO"),
    STONE(false, "ST"),
    IRON(false, "IR"),
    GRASS(false, "GR"),
    MEADOW(false, "ME"),
    DENSE_MEADOW(false, "DM"),
    OIL(true, "BL"),
    PLAIN(true, "BL"),
    RIVER(true, "BL"),
    SMALL_LAKE(true, "BL"),
    BIG_LAKE(true, "BL"),
    BEACH(true, "BL"),
    SEA(true, "BL")
    ;

    private boolean isBlue;
    private String abbreviation;

    public boolean isBlue() {
        return isBlue;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    private TileStructure(boolean isBlue, String abbreviation) {
        this.isBlue = isBlue;
        this.abbreviation = abbreviation;
    }

    public static TileStructure getTileStructureByString(String type){
        return null;
        //todo
        //is there any better idea than 15 if statements?
    }
}
