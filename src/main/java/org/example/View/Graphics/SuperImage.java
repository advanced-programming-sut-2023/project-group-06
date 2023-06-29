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
    MEADOW("Tiles/meadow.png", 46, 33),
    DENSE_MEADOW("Tiles/dense-meadow.png", 46, 33),
    // Buildings
    BARRACKS_0("Building/barracks-0.png", 138, 208),
    TREE("Building/tree-0.png", 46, 199),
    ROCK_BUILDING("Building/stone-0.png", 46, 75),
    SMALL_STONE_GATEHOUSE("Building/small-stone-gatehouse-0.png", 139, 276),
    BIG_STONE_GATEHOUSE("Building/big-stone-gatehouse-0.png", 231, 434),
    DRAWBRIDGE("bla bla", 0, 0),
    LOOKOUT_TOWER("Building/lookout-tower.png", 46, 261),
    PERIMETER_TOWER("Building/perimeter-tower.png", 139, 409),
    DEFENSE_TURRET("Building/defense-turret.png", 139, 369),
    SQUARE_TOWER("Building/square-tower.png", 139, 369),
    ROUND_TOWER("Building/round-tower.png", 139, 369),
    ARMORY("Building/armory.png", 46, 88),
    MERCENARY_POST("Building/mercenary-post.png", 139, 102),
    ENGINEERS_GUILD("Building/engineers-guild.png", 139, 184),
    KILLING_PIT("Building/killing-pit.png", 46, 23),
    INN("Building/inn.png", 139, 257),
    MILL("Building/mill.png", 46, 195),
    IRON_MINE("Building/iron-mine.png", 139, 149),
    MARKET("Building/market.png", 139, 189),
    OX_TETHER("Building/ox-tether.png", 46, 59),
    PITCH_RIG("Building/pitch-rig.png", 139, 98),
    QUARRY("Building/quarry.png", 139, 205),
    STOCKPILE("Building/stockpile.png", 46, 27),
    WOODCUTTERS("Building/woodcutter.png", 139, 69),
    HOVEL("Building/hovel.png", 139, 170),
    CHURCH("Building/church.png", 139, 170),
    CATHEDRAL("Building/cathedral.png", 230, 288),
    ARMORER("Building/armorer.png", 139, 244),
    BLACKSMITH("Building/blacksmith.png", 139, 206),
    FLETCHER("Building/fletcher.png", 139, 206),
    POLETURNER("Building/poleturner.png", 139, 206),
    OIL_SMELTER("Building/oil-smelter.png", 139, 206),
    PITCH_DITCH("Building/pitch-ditch.png", 46, 23),
    CAGED_WAR_DOGS("bla bla", 0, 0), //
    SIEGE_TENT("Building/siege-tent.png", 46, 69),
    STABLE("Building/stable.png", 139, 159),
    APPLE_ORCHARD("Building/apple-orchard.png", 139, 178),
    DIARY_FARMER("Building/diary-farmer.png", 139, 178),
    HOPS_FARMER("Building/hops-farmer.png", 139, 178),
    HUNTERS_POST("Building/hunter-post.png", 139, 178),
    WHEAT_FARMER("Building/wheat-farmer.png", 139, 178),
    BAKERY("Building/bakery.png", 139, 214),
    BREWER("Building/brewer.png", 139, 245),
    GRANARY("Building/granary.png", 46, 78),
    MAIN_CASTLE("Building/main-castle.png", 139, 265),
    STAIR("bla bla", 0, 0),
    WALL("bla bla", 0, 0),
    BRIDGE("bla bla", 0, 0),
    ;
    Image image;
    int Xo;
    int Yo;

    private SuperImage(String path, int Xo, int Yo) {
        path = getClass().getResource("") + "../../../../../../src/main/resources/" + path;
        image = new Image(path);
        this.Xo = Xo;
        this.Yo = Yo;
    }

    public Image getImage() {
        return image;
    }

    public int getXo() {
        return Xo;
    }

    public int getYo() {
        return Yo;
    }
}
