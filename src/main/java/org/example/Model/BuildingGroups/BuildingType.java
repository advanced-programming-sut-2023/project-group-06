package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;
import org.example.Model.ResourcesType;
import org.example.Model.TileStructure;
import org.example.View.Response;

public enum BuildingType {
    TREE(100, 0, 1, null, 0, 0, 0, false, "tree",Tree.class),
    ROCK(100, 0, 1, null, 0, 0, 0, false, "rock",Gate.class),
    SMALL_STONE_GATEHOUSE(1000,0,3,null,0,0,0,true,"small stone gatehouse",Gate.class),
    BIG_STONE_GATEHOUSE(2000,0,5,ResourcesType.STONE,20,0,0,true,"big stone gatehouse", Gate.class),
    KEEP(0,0,7,null,0,0,0,true,"keep", Towers.class), //maghar
    DRAWBRIDGE(0,0,1,ResourcesType.WOOD,10,0,0,true,"drawbridge", Gate.class), //pol moteharek
    LOOKOUT_TOWER(1300,0,3,ResourcesType.STONE,10,0,0,true,"lookout tower", Towers.class), // borje didbani
    PERIMETER_TOWER(1000,0,3,ResourcesType.STONE,10,0,0,true,"perimeter tower", Towers.class), // borj mohiti
    DEFENSE_TURRET(1200,0,3,ResourcesType.STONE,15,0,0,true,"defense turret", Towers.class), //borjak defaei
    SQUARE_TOWER(1600,0,3,ResourcesType.STONE,35,0,0,true,"square tower", Towers.class), // borj moarabei
    ROUND_TOWER(2000,0,3,ResourcesType.STONE,40,0,0,true,"round tower", Towers.class), // borj dayerei
    ARMORY(500,0,1,ResourcesType.WOOD,5,0,0,false,"armory", Storage.class), //aslahekhane
    BARRACKS(500,0,3,ResourcesType.STONE,15,0,0,true,"barracks", Building.class), // sarbaz khane
    MERCENARY_POST(500,0,3,ResourcesType.WOOD,10,0,0,true,"mercenary post", Building.class), //sarbaz khane mozdooran
    ENGINEERS_GUILD(500,100,3,ResourcesType.WOOD,10,0,0,true,"engineers guild", Storage.class), // senf mohandesan
    KILLING_PIT(0,0,1,ResourcesType.WOOD,6,0,0,true,"killing pit", Trap.class), //godal koshtar
    INN(300,100,3,ResourcesType.WOOD,20,1,0,false,"inn",Producers.class), //mosafer khane
    MILL(300,0,3,ResourcesType.WOOD,20,3,0,false,"mill", Producers.class), //asyab
    IRON_MINE(100,0,3,ResourcesType.WOOD,20,2,0,false,"iron mine", Producers.class),
    MARKET(300,0,3,ResourcesType.WOOD,5,1,0,false,"market",Building.class),
    OX_TETHER(100,0,1,ResourcesType.WOOD,5,1,0,true,"ox tether", Producers.class), //afsar gav
    PITCH_RIG(100,0,3,ResourcesType.WOOD,20,1,0,false,"pitch rig", Producers.class), //dakal ghir
    QUARRY(300,0,3,ResourcesType.WOOD,20,3,0,true,"quarry", Producers.class), //maadan sang
    STOCKPILE(500,0,1,null,0,0,0,true,"stockpile", Storage.class), //anbar
    WOODCUTTERS(100,0,3,ResourcesType.WOOD,3,1,0,false,"woodcutter",Producers.class),
    HOVEL(100,0,3,ResourcesType.WOOD,6,0,0,false,"hovel",Building.class), //khane
    CHURCH(800,250,3,null,0,0,0,false,"church",Building.class), //kelisa
    CATHEDRAL(1200,1000,5,null,0,0,0,true,"cathedral", Building.class), //kelisaye jame
    ARMORER(300,100,3,ResourcesType.WOOD,20,1,0,false,"armorer", Building.class), //zereh sazi
    BLACKSMITH(300,100,3,ResourcesType.WOOD,20,1,0,false,"blacksmith", Building.class), //sakhteman ahangari
    FLETCHER(300,100,3,ResourcesType.WOOD,20,1,0,false,"fletcher", Building.class), //kaman sazi
    POLETURNER(300,100,3,ResourcesType.WOOD,10,1,0,false,"poleturner", Building.class), //neyze sazi
    OIL_SMELTER(300,100,3,ResourcesType.IRON,10,0,1,true,"oil smelter", Producers.class), //karkhane zob
    PITCH_DITCH(300,0,3,ResourcesType.PITCH,10,0,0,true,"pitch ditch", Trap.class), //khandagh ghir
    CAGED_WAR_DOGS(100,100,3,ResourcesType.WOOD,10,0,0,false,"caged war dogs", Storage.class),
    SIEGE_TENT(100,0,1,null,0,0,1,false,"siege tent", Building.class), //chador mohasere
    STABLE(300,400,3,ResourcesType.WOOD,20,0,0,true,"stable", Storage.class),
    APPLE_ORCHARD(100,0,3,ResourcesType.WOOD,5,1,0,false,"apple orchard", Producers.class), //bagh sib
    DIARY_FARMER(100,0,3,ResourcesType.WOOD,10,1,0,false,"diary farmer", Producers.class), //labani
    HOPS_FARMER(100,0,3,ResourcesType.WOOD,15,1,0,false,"hops farmer", Producers.class), //mazrae jo
    HUNTERS_POST(300,0,3,ResourcesType.WOOD,5,1,0,false,"hunter post", Producers.class),
    WHEAT_FARMER(300,0,3,ResourcesType.WOOD,15,1,0,false,"wheat farmer" , Producers.class),
    BAKERY(300,0,3,ResourcesType.WOOD,10,1,0,false,"bakery", Producers.class),
    BREWER(300,0,3,ResourcesType.WOOD,10,1,0,false,"brewer", Producers.class), //abjo sazi
    GRANARY(500,0,1,ResourcesType.WOOD,5,0,0,false,"granary", Storage.class), //anbar ghaza
    MAIN_CASTLE(0, 0, 3, null, 0, 0, 0, true, "main castle", Towers.class),
    // STAIR and WALL are temporary and need to be fixed !!! todo
    STAIR(100, 0, 1, null, 0, 0, 0, false, "stair", null),
    WALL(100, 0, 1, null, 0, 0, 0, false, "wall", null),
    BRIDGE(1, 0, 1, null, 0, 0, 0, true, "bridge", null),
    ;
    private int size;
    private int height;
    private int hitPoint;
    private int goldPrice;
    private Resources resourcesPrice;
    private int workerPrice;
    private int engineerPrice;
    private boolean canYouEnterIt;

    private String name;
    private Class<?> BuildingClass;
    BuildingType(int hitPoint, int goldPrice, int size,
                 ResourcesType resourcesPriceType, int resourcePriceAmount, int workerPrice,
                 int engineerPrice/*,int height*/, boolean canYouEnterIt, String name, Class<?> buildingClass) {
        this.hitPoint = hitPoint;
        this.goldPrice = goldPrice;
        this.resourcesPrice =  new Resources(resourcePriceAmount, resourcesPriceType);
        this.workerPrice = workerPrice;
        this.engineerPrice = engineerPrice;
        this.size = size;
        this.canYouEnterIt = canYouEnterIt;
        this.name = name;
        this.BuildingClass = buildingClass;
        /*this.height = height;*/
    }

    public static BuildingType getBuildingTypeByString(String type){
        BuildingType buildingType = null;
        for(BuildingType ts : BuildingType.values()) if (ts.name.equals(type)) buildingType = ts;
        return buildingType;
    }

    public static boolean checkGround(BuildingType buildingType, TileStructure tileStructure){
        if(buildingType == QUARRY)
            return tileStructure == TileStructure.ROCK;
        else if(buildingType == PITCH_RIG)
            return tileStructure == TileStructure.PLAIN;
        else if(buildingType == IRON_MINE)
            return tileStructure == TileStructure.IRON;
        else if(buildingType == OIL_SMELTER)
            return tileStructure == TileStructure.OIL;
        else if(tileStructure.isBlue())
            return false;
        else if(buildingType == APPLE_ORCHARD || buildingType == HOPS_FARMER || buildingType == WHEAT_FARMER)
            return tileStructure == TileStructure.GRASS || tileStructure == TileStructure.DENSE_MEADOW;
        else if(tileStructure == TileStructure.EARTH || tileStructure == TileStructure.GRASS ||
                tileStructure == TileStructure.MEADOW)
            return true;
        else if(buildingType == MILL)
            return false;
        else if(buildingType == TREE)
            return tileStructure == TileStructure.DENSE_MEADOW;
        else if(buildingType == ROCK)
            return tileStructure == TileStructure.ROCK || tileStructure == TileStructure.STONE;
        else if(buildingType.getBuildingClass() == Towers.class)
            return tileStructure != TileStructure.ROCK && tileStructure != TileStructure.STONE;
        else return tileStructure == TileStructure.PEBBLE ||
                    tileStructure == TileStructure.DENSE_MEADOW;
    }

    public int getWorkerPrice() {
        return workerPrice;
    }

    public int getEngineerPrice() {
        return engineerPrice;
    }

    public int getGoldPrice() {
        return goldPrice;
    }

    public Resources getResourcesPrice() {
        return resourcesPrice;
    }

    public int getSize() {
        return size;
    }

    public int getHeight() {
        return height;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public boolean isCanYouEnterIt() {
        return canYouEnterIt;
    }

    public String getName() {
        return name;
    }

    public Class<?> getBuildingClass() {
        return BuildingClass;
    }
}
