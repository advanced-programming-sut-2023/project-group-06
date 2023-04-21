package org.example.Model;

import org.example.Model.BuildingGroups.BuildingType;

public enum WeaponType {
    BOW(new Resources(2,ResourcesType.WOOD), BuildingType.FLETCHER,"bow"),
    CROSSBOW(new Resources(3,ResourcesType.WOOD), BuildingType.FLETCHER,"crossbow"),
    SPEAR(new Resources(1,ResourcesType.WOOD), BuildingType.FLETCHER,"spear"),
    PIKE(new Resources(2,ResourcesType.WOOD), BuildingType.POLETURNER,"pike"),
    MACE(new Resources(1,ResourcesType.IRON), BuildingType.POLETURNER,"mace"),
    SWORDS(new Resources(1,ResourcesType.IRON), BuildingType.BLACKSMITH,"swords"),
    LEATHER_ARMOR(null, BuildingType.DIARY_FARMER,"leather armor"),
    METAL_ARMOR(new Resources(1,ResourcesType.IRON), BuildingType.ARMORER,"metal armor");
    private Resources resourcePrice;
    private BuildingType buildingType;
    private String name;

    WeaponType(Resources resourcePrice, BuildingType buildingType, String name) {
        this.resourcePrice = resourcePrice;
        this.buildingType = buildingType;
        this.name = name;
    }

    public int getResourcePriceAmount() {
        return resourcePrice.getAmount();
    }

    public ResourcesType getResourcesPriceType() {
        return resourcePrice.getType();
    }

    public Resources getResourcePrice() {
        return resourcePrice;
    }

    public static WeaponType getWeaponTypeByString(String weaponName) {
        WeaponType weaponType = null;
        for (WeaponType ts : WeaponType.values()) {
            if (ts.name.equals(weaponName)) weaponType = ts;
        }
        return weaponType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }
}
