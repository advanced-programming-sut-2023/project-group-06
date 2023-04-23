package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.View.Response;

import java.util.regex.Matcher;

public class TradeController {
    public static Kingdom currentPlayer;
    public static Response sendTrade(Matcher matcher){
        matcher.find();
        String[] groupNames = {"resourceAmount","resourceType","message","price","username"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        ResourcesType resourcesType = ResourcesType.getResourcesTypeByString(Controller.makeEntryValid(matcher.group("resourceType")));
        int price = Integer.parseInt(Controller.makeEntryValid(matcher.group("price")));
        int resourceAmount = Integer.parseInt(Controller.makeEntryValid(matcher.group("resourceAmount")));
        String message = Controller.makeEntryValid(matcher.group("message"));
        String username = Controller.makeEntryValid(matcher.group("username"));
        if (resourcesType == null) return Response.INVALID_RESOURCE_TYPE;
        if (price < 0) return Response.INVALID_PRICE;
        if (resourceAmount <= 0) return Response.INVALID_AMOUNT;
        if (price > currentPlayer.getWealth()) return Response.NOT_ENOUGH_GOLD_TRADE;
        Resources resource = new Resources(resourceAmount,resourcesType);
        currentPlayer.addToWealth(-1 * price);
        TradeRequest tradeRequest = new TradeRequest(resource,price,message,GameController.currentGame.getATradeRequestId(),currentPlayer,GameController.currentGame.getKingdomByUsername(username),GameController.currentGame.getNumberOfTurns());
        GameController.currentGame.addTradeRequest(tradeRequest);
        return Response.TRADE_REQUEST_CREATED;
    }

    public static String tradeList(){
        String output = "All of trades sent to you which hasn't been accepted yet:";
        for (int i = currentPlayer.getAllTradeRequestsSentToMe().size() - 1; i >= 0; i--) {
            if (output != null) output += '\n' + showTradeRequestsSentToMeByIndex(i);
        }
        return output;
    }

    public static Response acceptTrade(Matcher matcher){
        matcher.find();
        String[] groupNames = {"message","id"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        int id = Integer.parseInt(Controller.makeEntryValid(matcher.group("id")));
        String message = Controller.makeEntryValid(matcher.group("message"));
        if (currentPlayer.getTradeRequestsNotAcceptedByMeById(id) == null) return Response.TRADE_REQUEST_NOT_SENT;
        TradeRequest tradeRequest = currentPlayer.getTradeRequestSentToMeById(id);
        int resourceAmount = tradeRequest.getResources().getAmount();
        ResourcesType resourcesType = tradeRequest.getResources().getType();
        if (currentPlayer.getResourceAmountByType(resourcesType) < resourceAmount) return Response.NOT_ENOUGH_RESOURCE_TRADE;
        currentPlayer.getTradeRequestSentToMeById(id).setAccepted(true);
        int thisTurn = GameController.currentGame.getNumberOfTurns();
        currentPlayer.getTradeRequestSentToMeById(id).setAcceptTurn(thisTurn);
        currentPlayer.getTradeRequestSentToMeById(id).setAcceptMessage(message);
        currentPlayer.addToTradeRequestsAcceptedByMe(currentPlayer.getTradeRequestSentToMeById(id));
        currentPlayer.payResource(new Resources(resourceAmount,resourcesType));
        currentPlayer.getTradeRequestSentToMeById(id).getOwner().addAsset(new Resources(resourceAmount,resourcesType));
        currentPlayer.addToWealth(tradeRequest.getPrice());
        return Response.TRADE_REQUEST_ACCEPTED;
    }

    private static String showTradeRequestsSentByMeByIndex(int index) {
        String output = "";
        int size = currentPlayer.getTradeRequestsSentByMe().size();
        int id = currentPlayer.getTradeRequestsSentByMe().get(index).getId();
        String destinationUsername = currentPlayer.getTradeRequestsSentByMe().get(index).getSentToWho().getOwner().getUsername();
        String resourceType = currentPlayer.getTradeRequestsSentByMe().get(index).getResources().getType().getName();
        int resourceAmount = currentPlayer.getTradeRequestsSentByMe().get(index).getResources().getAmount();
        String message = currentPlayer.getTradeRequestsSentByMe().get(index).getMessage();
        String acceptedMessage = currentPlayer.getTradeRequestsSentByMe().get(index).getAcceptMessage();
        int offerTurn = currentPlayer.getTradeRequestsSentByMe().get(index).getOfferTurn();
        int acceptTurn = currentPlayer.getTradeRequestsSentByMe().get(index).getAcceptTurn();
        String spaces = '\n' + "    ";
        output += (size - index) + "." + spaces + "Id: " + id + spaces + "Sent to who: " + destinationUsername + spaces +
                "Resource Type: " + resourceType + spaces + "Resource amount: " + resourceAmount + spaces + "Message: " + message +
                spaces + "This trade was sent in round " + offerTurn;
        if (!currentPlayer.getTradeRequestsSentByMe().get(index).isAccepted()) output += spaces + "This trade has not been accepted yet!";
        else output += spaces + "This trade has been accepted by kingdom " + destinationUsername + " in round " + acceptTurn + spaces
                + "Accept message: " + acceptedMessage;
        return output;
    }

    private static String showTradeRequestsSentToMeByIndex(int index) {
        String output = "";
        if (currentPlayer.getAllTradeRequestsSentToMe().get(index).isAccepted()) return null;
        int size = currentPlayer.getAllTradeRequestsSentToMe().size();
        int id = currentPlayer.getAllTradeRequestsSentToMe().get(index).getId();
        String sourceUsername = currentPlayer.getAllTradeRequestsSentToMe().get(index).getOwner().getOwner().getUsername();
        String resourceType = currentPlayer.getAllTradeRequestsSentToMe().get(index).getResources().getType().getName();
        int resourceAmount = currentPlayer.getAllTradeRequestsSentToMe().get(index).getResources().getAmount();
        String message = currentPlayer.getAllTradeRequestsSentToMe().get(index).getMessage();
        String acceptedMessage = currentPlayer.getAllTradeRequestsSentToMe().get(index).getAcceptMessage();
        int offerTurn = currentPlayer.getAllTradeRequestsSentToMe().get(index).getOfferTurn();
        int acceptTurn = currentPlayer.getAllTradeRequestsSentToMe().get(index).getAcceptTurn();
        String spaces = '\n' + "    ";
        output += (size - index) + "." + spaces + "Id: " + id + spaces + "Sent by who: " + sourceUsername + spaces +
                "Resource Type: " + resourceType + spaces + "Resource amount: " + resourceAmount + spaces + "Message: " + message +
                spaces + "This trade was sent in round " + offerTurn;
        return output;
    }

    private static String showTradeRequestsAcceptedByMeByIndex(int index) {
        String output = "";
        int size = currentPlayer.getTradeRequestsAcceptedByMe().size();
        int id = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getId();
        String sourceUsername = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getOwner().getOwner().getUsername();
        String resourceType = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getResources().getType().getName();
        int resourceAmount = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getResources().getAmount();
        String message = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getMessage();
        String acceptedMessage = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getAcceptMessage();
        int offerTurn = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getOfferTurn();
        int acceptTurn = currentPlayer.getTradeRequestsAcceptedByMe().get(index).getAcceptTurn();
        String spaces = '\n' + "    ";
        output += (size - index) + "." + spaces + "Id: " + id + spaces + "Sent by who: " + sourceUsername + spaces +
                "Resource Type: " + resourceType + spaces + "Resource amount: " + resourceAmount + spaces + "Message: " + message +
                spaces + "This trade was sent in round " + offerTurn;
        output += spaces + "This trade was accepted in round " + acceptTurn + spaces
                + "Accept message: " + acceptedMessage;
        return output;
    }

    public static String tradeHistory(){
        String output = "Trade requests sent by me:" + '\n';
        for (int i = currentPlayer.getTradeRequestsSentByMe().size() - 1; i >= 0 ; i--) {
            output += showTradeRequestsSentByMeByIndex(i) + '\n';
        }
        output += "Trade requests accepted by me:";
        for (int i = currentPlayer.getTradeRequestsAcceptedByMe().size() - 1; i >= 0; i--) {
            output += '\n' + showTradeRequestsAcceptedByMeByIndex(i);
        }
        return output;
    }

    public static String tradeNotification() {
        String output = "These are new trade requests sent to you:";
        boolean check = false;
        for (int i = currentPlayer.getAllTradeRequestsSentToMe().size() - 1; i >= 0; i--) {
            if (!currentPlayer.getAllTradeRequestsSentToMe().get(i).isNotified()) {
                output += '\n' + showTradeRequestsSentToMeByIndex(i);
                currentPlayer.getAllTradeRequestsSentToMe().get(i).setNotified(true);
                check = true;
            }
        }
        if (!check) return "No new trade request have been sent to you";
        return output;
    }

    public static String showAllPlayers() {
        String output = "These are all of the players you can trade with:";
        String myName = currentPlayer.getOwner().getUsername();
        int counter = 1;
        for (int i = 0; i < GameController.currentGame.getKingdoms().size(); i++) {
            if (!GameController.currentGame.getKingdoms().get(i).getOwner().getUsername().equals(myName)) {
                output += '\n';
                output += counter;
                output += ". " + GameController.currentGame.getKingdoms().get(i).getOwner().getUsername();
                counter++;
            }
        }
        return output;
    }
}
