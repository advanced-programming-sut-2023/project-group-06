package org.example.Controller.GameControllers;

import org.example.Controller.Controller;
import org.example.Model.*;
import org.example.View.Response;

import java.util.regex.Matcher;

public class ShopController {
    public static Kingdom currentKingdom;

    public static String showPriceList(){
        String output = "";
        output += "Stone buying price: " + ResourcesType.STONE.getBuyPrice() + " ,stone selling price: " + ResourcesType.STONE.getSellPrice() + " ,my stone: " + currentKingdom.getResourceAmountByType(ResourcesType.STONE) + '\n';
        output += "Wood buying price: " + ResourcesType.WOOD.getBuyPrice() + " ,wood selling price: " + ResourcesType.WOOD.getSellPrice() + " ,my wood: " + currentKingdom.getResourceAmountByType(ResourcesType.WOOD) + '\n';
        output += "Wheat buying price: " + ResourcesType.WHEAT.getBuyPrice() + " ,wheat selling price: " + ResourcesType.WHEAT.getSellPrice() + " ,my wheat: " + currentKingdom.getResourceAmountByType(ResourcesType.WHEAT) + '\n';
        output += "Ale buying price: " + ResourcesType.ALE.getBuyPrice() + " ,ale selling price: " + ResourcesType.ALE.getSellPrice() + " ,my ale: " + currentKingdom.getResourceAmountByType(ResourcesType.ALE) + '\n';
        output += "Hops buying price: " + ResourcesType.HOPS.getBuyPrice() + " ,hops selling price: " + ResourcesType.HOPS.getSellPrice() + " ,my hops: " + currentKingdom.getResourceAmountByType(ResourcesType.HOPS) + '\n';
        output += "Iron buying price: " + ResourcesType.IRON.getBuyPrice() + " ,iron selling price: " + ResourcesType.IRON.getSellPrice() + " ,my iron: " + currentKingdom.getResourceAmountByType(ResourcesType.IRON) + '\n';
        output += "Pitch buying price: " + ResourcesType.PITCH.getBuyPrice() + " ,pitch selling price: " + ResourcesType.PITCH.getSellPrice() + " ,my pitch: " + currentKingdom.getResourceAmountByType(ResourcesType.PITCH) + '\n';
        output += "Flour buying price: " + ResourcesType.FLOUR.getBuyPrice() + " ,flour selling price: " + ResourcesType.FLOUR.getSellPrice() + " ,my flour: " + currentKingdom.getResourceAmountByType(ResourcesType.FLOUR);
        return output;
    }

    public static Response buy(Matcher matcher){
        matcher.find();
        String[] groupNames = {"count","type"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        ResourcesType type = ResourcesType.getResourcesTypeByString(Controller.makeEntryValid(matcher.group("type")));
        int count = Integer.parseInt(Controller.makeEntryValid(matcher.group("count")));
        if (count <= 0) return Response.INVALID_COUNT;
        if (type == null) return Response.INVALID_RESOURCE_TYPE;
        if (type == ResourcesType.ALE) return buyAle(count);
        if (currentKingdom.getResourcesCapacity() - currentKingdom.getResourcesAmount() < count) return Response.NOT_ENOUGH_STORAGE;
        if (count * type.getBuyPrice() > currentKingdom.getWealth()) return Response.NOT_ENOUGH_GOLD_BUY;
        currentKingdom.addAsset(new Resources(count,type));
        currentKingdom.addToWealth(-1 * count * type.getBuyPrice());
        return Response.BUY;
    }

    private static Response buyAle(int count) {
        if (currentKingdom.getInnsCapacity() - currentKingdom.getAleAmount() < count) return Response.NOT_ENOUGH_STORAGE;
        if (count * ResourcesType.ALE.getBuyPrice() > currentKingdom.getWealth()) return Response.NOT_ENOUGH_GOLD_BUY;
        currentKingdom.addAsset(new Resources(count,ResourcesType.ALE));
        currentKingdom.addToWealth(-1 * count * ResourcesType.ALE.getBuyPrice());
        return Response.BUY;
    }

    public static Response sell(Matcher matcher){
        matcher.find();
        String[] groupNames = {"count","type"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        ResourcesType type = ResourcesType.getResourcesTypeByString(Controller.makeEntryValid(matcher.group("type")));
        int count = Integer.parseInt(Controller.makeEntryValid(matcher.group("count")));
        if (count <= 0) return Response.INVALID_COUNT;
        if (type == null) return Response.INVALID_RESOURCE_TYPE;
        if (currentKingdom.getResourceAmountByType(type) < count) return Response.NOT_THIS_MUCH_RESOURCES;
        currentKingdom.payResource(new Resources(count,type));
        currentKingdom.addToWealth(count * type.getSellPrice());
        return Response.SELL;
    }
}
