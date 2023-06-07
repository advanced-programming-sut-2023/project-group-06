package org.example.View.Graphics;


import javafx.scene.image.Image;

public enum SuperImage {
    EARTH("Tiles/earth.png", 46, 23),
    PEBBLE("Tiles/pebble.png", 46, 23),
    ROCK("Tiles/rock.png", 46, 23),
    STONE("Tiles/stone.png", 46, 23),
    IRON("Tiles/iron.png", 46, 23),
    GRASS("Tiles/grass.png", 46, 23),
    OIL("Tiles/oil.png", 46, 23),
    SHALLOW_WATER("Tiles/shallow-water.png", 46, 23),
    PLAIN("Tiles/plain.png", 46, 23),
    RIVER("Tiles/river.png", 46, 23),
    SMALL_LAKE("Tiles/small-lake.png", 46, 23),
    BIG_LAKE("Tiles/big-lake.png", 46, 23),
    BEACH("Tiles/beach.png", 46, 23),
    SEA("Tiles/sea.png", 46, 23),
    MEADOW("Tiles/meadow.png",46,33),
    DENSE_MEADOW("Tiles/dense-meadow.png", 46, 33),
    // Buildings
    BARRACKS_0("Building/barracks-0.png", 138, 208),
    TREE("Building/tree-0.png", 46,199),
    ROCK_BUILDING("Building/stone-0.png", 46,75),
    SMALL_STONE_GATEHOUSE("Building/small-stone-gatehouse-0.png", 139,276),
    BIG_STONE_GATEHOUSE("Building/big-stone-gatehouse-0.png", 231,434),
    DRAWBRIDGE("bla bla", 0,0),
    LOOKOUT_TOWER("bla bla", 0,0),
    PERIMETER_TOWER("bla bla", 0,0),
    DEFENSE_TURRET("bla bla", 0,0),
    SQUARE_TOWER("bla bla", 0, 0),
    ROUND_TOWER("bla bla", 0,0),
    ARMORY("bla bla", 0,0),
    MERCENARY_POST("bla bla", 0,0),
    ENGINEERS_GUILD("bla bla", 0,0),
    KILLING_PIT("bla bla", 0,0),
    INN("bla bla", 0,0),
    MILL("bla bla", 0,0),
    IRON_MINE("bla bla", 0,0),
    MARKET("bla bla", 0,0),
    OX_TETHER("bla bla", 0,0),
    PITCH_RIG("bla bla", 0,0),
    QUARRY("bla bla", 0,0),
    STOCKPILE("bla bla", 0,0),
    WOODCUTTERS("bla bla", 0,0),
    HOVEL("bla bla", 0,0),
    CHURCH("bla bla", 0,0),
    CATHEDRAL("bla bla", 0,0),
    ARMORER("bla bla", 0,0),
    BLACKSMITH("bla bla", 0,0),
    FLETCHER("bla bla", 0,0),
    POLETURNER("bla bla", 0,0),
    OIL_SMELTER("bla bla", 0,0),
    PITCH_DITCH("bla bla", 0,0),
    CAGED_WAR_DOGS("bla bla", 0,0),
    SIEGE_TENT("bla bla", 0,0),
    STABLE("bla bla", 0,0),
    APPLE_ORCHARD("bla bla", 0,0),
    DIARY_FARMER("bla bla", 0,0),
    HOPS_FARMER("bla bla", 0,0),
    HUNTERS_POST("bla bla", 0,0),
    WHEAT_FARMER("bla bla", 0,0),
    BAKERY("bla bla", 0,0),
    BREWER("bla bla", 0,0),
    GRANARY("bla bla", 0,0),
    MAIN_CASTLE("bla bla", 0,0),
    STAIR("bla bla", 0,0),
    WALL("bla bla", 0,0),
    BRIDGE("bla bla", 0,0),
    ;
    Image image;
    int Xo;
    int Yo;
    private SuperImage(String path, int Xo, int Yo){
        path = getClass().getResource("")+"../../../../../../src/main/resources/" + path;
        image = new Image(path);
        this.Xo = Xo;
        this.Yo = Yo;
    }
    public Image getImage(){
        return image;
    }
    public int getXo(){
        return Xo;
    }
    public int getYo(){
        return Yo;
    }
}
