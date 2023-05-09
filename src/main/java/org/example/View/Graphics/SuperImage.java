package org.example.View.Graphics;

import java.awt.*;

public enum SuperImage {
    BEACH("src/main/resources/Tiles/beach.png", 46, 23),;
    Image image;
    int Xo;
    int Yo;
    SuperImage(String path, int Xo, int Yo){
        image = Toolkit.getDefaultToolkit().getImage(path);
        this.Xo = Xo;
        this.Yo = Yo;
    }
}
