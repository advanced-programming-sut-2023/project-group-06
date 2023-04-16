package org.example.Model;

import java.util.ArrayList;

public class TradeRequest {
    Resources resources;
    int price;
    String message;
    int id;
    Kingdom owner;
    boolean isAccepted = false;
    Kingdom whoAccepted = null;

    public TradeRequest(Resources resources, int price, String message, int id, Kingdom owner,
                        boolean isAccepted, Kingdom whoAccepted) {
        this.resources = resources;
        this.price = price;
        this.message = message;
        this.id = id;
        this.owner = owner;
    }
}
