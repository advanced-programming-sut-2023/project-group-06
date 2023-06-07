package org.example.View.Graphics;


import javafx.scene.image.Image;

import java.net.URL;

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
