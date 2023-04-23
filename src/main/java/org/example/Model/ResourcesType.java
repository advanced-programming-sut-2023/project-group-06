package org.example.Model;

public enum ResourcesType {
    WHEAT("wheat",3,4),
    FLOUR("flour",4,6),
    HOPS("hops",5,8),
    ALE("ale",7,10),
    STONE("stone",10,16),
    IRON("iron",14,20),
    WOOD("wood",8,12),
    PITCH("pitch",11,15);
    private String name;
    private int sellPrice;
    private int buyPrice;

    ResourcesType(String name, int sellPrice, int buyPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public static ResourcesType getResourcesTypeByString(String name) {
        for(ResourcesType ts : ResourcesType.values()) {
            if (ts.name.equals(name)) return ts;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }
}
