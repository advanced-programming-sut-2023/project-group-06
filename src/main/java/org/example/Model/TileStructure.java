package org.example.Model;

public enum TileStructure {
    EARTH(false),
    PEBBLE(false),
    ROCK(false),
    STONE(false),
    IRON(false),
    GRASS(false),
    MEADOW(false),
    DENSE_MEADOW(false),
    OIL(true),
    PLAIN(true),
    RIVER(true),
    SMALL_LAKE(true),
    BIG_LAKE(true),
    BEACH(true),
    SEA(true)
    ;

    private boolean isBlue;

    public boolean isBlue() {
        return isBlue;
    }

    private TileStructure(boolean isBlue) {
        this.isBlue = isBlue;
    }
}
