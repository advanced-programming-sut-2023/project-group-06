package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;
import org.example.Model.Resources;
import org.example.Model.ResourcesType;
import org.example.Model.TileStructure;
import org.example.View.Response;

public enum BuildingType {
    TREE(100, 0, 1, null, 0, 0, 0, false, "tree",Tree.class),
    SMALL_STONE_GATEHOUSE(1000,0,3,null,0,0,0,true,"small stone gatehouse",Gate.class),
    BIG_STONE_GATEHOUSE(2000,0,5,ResourcesType.STONE,20,0,0,true,"big stone gatehouse", Gate.class),
    KEEP(0,0,7,null,0,0,0,true,"keep", Towers.class), //maghar
    DRAWBRIDGE(0,0,1,ResourcesType.WOOD,10,0,0,false,"drawbridge", Gate.class), //pol moteharek
    LOOKOUT_TOWER(1300,0,3,ResourcesType.STONE,10,0,0,true,"lookout tower", Towers.class), // borje didbani
    PERIMETER_TOWER(1000,0,3,ResourcesType.STONE,10,0,0,true,"perimeter tower", Towers.class), // borj mohiti
    DEFENSE_TURRET(1200,0,3,ResourcesType.STONE,15,0,0,true,"defense turret", Towers.class), //borjak defaei
    SQUARE_TOWER(1600,0,3,ResourcesType.STONE,35,0,0,true,"square tower", Towers.class), // borj moarabei
    ROUND_TOWER(2000,0,3,ResourcesType.STONE,40,0,0,true,"round tower", Towers.class), // borj dayerei
    ARMORY(500,0,3,ResourcesType.WOOD,5,0,0,false,"armory", Producers.class), //aslahekhane
    BARRACKS(500,0,3,ResourcesType.STONE,15,0,0,false,"barracks", Producers.class), // sarbaz khane
    MERCENARY_POST(500,0,3,ResourcesType.WOOD,10,0,0,false,"mercenary post", Producers.class), //sarbaz khane mozdooran
    ENGINEERS_GUILD(500,100,3,ResourcesType.WOOD,10,0,0,false,"engineers guild", Producers.class), // senf mohandesan
    KILLING_PIT(0,0,1,ResourcesType.WOOD,6,0,0,true,"killing pit", Trap.class), //godal koshtar
    INN(300,100,3,ResourcesType.WOOD,20,1,0,false,"inn",Producers.class), //mosafer khane
    MILL(300,0,3,ResourcesType.WOOD,20,3,0,false,"mill", Producers.class), //asyab
    IRON_MINE(100,0,3,ResourcesType.WOOD,20,2,0,false,"iron mine", Producers.class),
    MARKET(300,0,3,ResourcesType.WOOD,5,1,0,false,"market",Building.class),
    OX_TETHER(100,0,1,ResourcesType.WOOD,5,1,0,false,"ox tether", Storage.class), //afsar gav
    PITCH_RIG(100,0,3,ResourcesType.WOOD,20,1,0,false,"pitch rig", Producers.class), //dakal ghir
    QUARRY(300,0,3,ResourcesType.WOOD,20,3,0,false,"quarry", Producers.class), //maadan sang
    STOCKPILE(500,0,3,null,0,0,0,false,"stockpile", Storage.class), //anbar
    WOODCUTTERS(100,0,3,ResourcesType.WOOD,3,1,0,false,"woodcutter",Producers.class),
    HOVEL(100,0,3,ResourcesType.WOOD,6,0,0,false,"hovel",Building.class), //khane
    CHURCH(800,250,3,null,0,0,0,false,"church",Building.class), //kelisa
    CATHEDRAL(1200,1000,5,null,0,0,0,false,"cathedral", Producers.class), //kelisaye jame
    ARMORER(300,100,3,ResourcesType.WOOD,20,1,0,false,"armorer", Producers.class), //zereh sazi
    BLACKSMITH(300,100,3,ResourcesType.WOOD,20,1,0,false,"blacksmith", Producers.class), //sakhteman ahangari
    FLETCHER(300,100,3,ResourcesType.WOOD,20,1,0,false,"fletcher", Producers.class), //kaman sazi
    POLETURNER(300,100,3,ResourcesType.WOOD,10,1,0,false,"poleturner", Producers.class), //neyze sazi
    OIL_SMELTER(300,100,3,ResourcesType.IRON,10,0,1,false,"oil smelter", Producers.class), //karkhane zob
    PITCH_DITCH(300,0,3,ResourcesType.PITCH,10,0,0,true,"pitch ditch", Trap.class), //khandagh ghir
    CAGED_WAR_DOGS(100,100,3,ResourcesType.WOOD,10,0,0,false,"caged war dogs", Trap.class),
    SIEGE_TENT(100,0,1,null,0,0,1,false,"siege tent", Building.class), //chador mohasere
    STABLE(300,400,3,ResourcesType.WOOD,20,0,0,false,"stable", Producers.class),
    APPLE_ORCHARD(100,0,3,ResourcesType.WOOD,5,1,0,false,"apple orchard", Producers.class), //bagh sib
    DIARY_FARMER(100,0,3,ResourcesType.WOOD,10,1,0,false,"diary farmer", Producers.class), //labani
    HOPS_FARMER(100,0,3,ResourcesType.WOOD,15,1,0,false,"hops farmer", Producers.class), //mazrae jo
    HUNTERS_POST(300,0,3,ResourcesType.WOOD,5,1,0,false,"hops farmer", Producers.class),
    WHEAT_FARMER(300,0,3,ResourcesType.WOOD,15,1,0,false,"wheat farmer" , Producers.class),
    BAKERY(300,0,3,ResourcesType.WOOD,10,1,0,false,"bakery", Producers.class),
    BREWER(300,0,3,ResourcesType.WOOD,10,1,0,false,"brewer", Producers.class), //abjo sazi
    GRANARY(500,0,3,ResourcesType.WOOD,5,0,0,false,"granary", Storage.class), //anbar ghaza
    MAIN_CASTLE(0, 0, 3, null, 0, 0, 0, true, "main castle", Towers.class);

    private int size;
    private int direction;//////not all the buildings have direction
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
        if(tileStructure.isBlue())
            return false;
        return true;
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

    public Class<?> getBuildingClass() {
        return BuildingClass;
    }
}
