package org.example.Model;

import java.util.ArrayList;

public class TradeRequest {
    private Resources resources;
    private int price;
    private String message;
    private int id;
    private int offerTurn;
    private Kingdom owner;
    private boolean isAccepted = false;
    private Kingdom sentToWho = null;
    private int acceptTurn = -1;
    private String acceptMessage;
    private boolean isNotified = false;

    public TradeRequest(Resources resources, int price, String message, int id, Kingdom owner,
                        Kingdom sentToWho, int offerTurn) {
        this.resources = resources;
        this.price = price;
        this.message = message;
        this.id = id;
        this.owner = owner;
        this.offerTurn = offerTurn;
        this.sentToWho = sentToWho;
        owner.addToTradeRequestsSentByMe(this);
        sentToWho.addToAllTradeRequestsSentToMe(this);
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOfferTurn() {
        return offerTurn;
    }

    public void setOfferTurn(int offerTurn) {
        this.offerTurn = offerTurn;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public void setOwner(Kingdom owner) {
        this.owner = owner;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Kingdom getSentToWho() {
        return sentToWho;
    }

    public void setSentToWho(Kingdom sentToWho) {
        this.sentToWho = sentToWho;
    }

    public int getAcceptTurn() {
        return acceptTurn;
    }

    public void setAcceptTurn(int acceptTurn) {
        this.acceptTurn = acceptTurn;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }
}
