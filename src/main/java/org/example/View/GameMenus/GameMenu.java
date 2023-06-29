package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.Data;
import org.example.Model.Tile;
import org.example.Model.TileStructure;

public class GameMenu extends Application {
    static Stage stage;
    static Scene scene;
    public StackPane mainStackPane;
    public BorderPane mainBorderPane;
    public Canvas mainCanvas;
    public HBox bottomHBox;
    public Pane mainPane;
    public Pane canvasPane;
    // map things
    int mapPointerX = 300;
    int mapPointerY = 1000;
    double lastMouseX;
    double lastMouseY;
    //

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        scene = sceneMaker();
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            System.out.println("ininin " + mainCanvas);
            if (e.getCode() == KeyCode.I) zoomIn();
            else if (e.getCode() == KeyCode.O) zoomOut();
        });
        starter();
        stage.show();
    }

    Tile[][] map;
    private void starter() {
        makeMapDraggable();
        map = Data.loadMap("test");
        mainCanvas.setHeight(3000);
        mainCanvas.setWidth(8000);

        for(int i = 0; i < 10 ; i++) for(int j =0; j < 10; j++) map[i][j] = new Tile(TileStructure.DENSE_MEADOW,i,j);
        map[5][0].setBuilding(new Building(null, BuildingType.SIEGE_TENT, 0, 5));
        map[5][1].setBuilding(new Building(null, BuildingType.STOCKPILE, 1, 5));
        map[5][2].setBuilding(new Building(null, BuildingType.OX_TETHER, 2, 5));
        map[5][3].setBuilding(new Building(null, BuildingType.MILL, 3, 5));
        map[5][4].setBuilding(new Building(null, BuildingType.KILLING_PIT, 4, 5));
        map[5][5].setBuilding(new Building(null, BuildingType.TREE, 5, 5));
        map[5][6].setBuilding(new Building(null, BuildingType.ROCK, 6, 5));
        map[5][7].setBuilding(new Building(null, BuildingType.LOOKOUT_TOWER, 7, 5));
        map[5][8].setBuilding(new Building(null, BuildingType.ARMORY, 8, 5));
        map[0][0].setBuilding(new Building(null, BuildingType.PITCH_DITCH, 0, 0));
        map[0][1].setBuilding(new Building(null, BuildingType.GRANARY, 1, 0));



        Building building = new Building(null, BuildingType.INN, 1, 20);
        for(int i = 19; i < 22; i++) for(int j = 0; j < 3; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.ENGINEERS_GUILD, 4, 20);
        for(int i = 19; i < 22; i++) for(int j = 3; j < 6; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.MERCENARY_POST, 7, 20);
        for(int i = 19; i < 22; i++) for(int j = 6; j < 9; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BARRACKS, 10, 20);
        for(int i = 19; i < 22; i++) for(int j = 9; j < 12; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.SMALL_STONE_GATEHOUSE, 13,20);
        for(int i = 19; i < 22; i++) for(int j = 12; j < 15; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BIG_STONE_GATEHOUSE, 17,20);
        for(int i = 18; i < 23; i++) for(int j = 15; j < 20; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.PERIMETER_TOWER, 21,20);
        for(int i = 19; i < 22; i++) for(int j = 20; j < 23; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.DEFENSE_TURRET, 24,20);
        for(int i = 19; i < 22; i++) for(int j = 23; j < 26; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.SQUARE_TOWER, 27,20);
        for(int i = 19; i < 22; i++) for(int j = 26; j < 29; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.ROUND_TOWER, 30,20);
        for(int i = 19; i < 22; i++) for(int j = 29; j < 32; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.IRON_MINE, 33,20);
        for(int i = 19; i < 22; i++) for(int j = 32; j < 35; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.MARKET, 36,20);
        for(int i = 19; i < 22; i++) for(int j = 35; j < 38; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.PITCH_RIG, 39,20);
        for(int i = 19; i < 22; i++) for(int j = 38; j < 41; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.QUARRY, 42,20);
        for(int i = 19; i < 22; i++) for(int j = 41; j < 44; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.WOODCUTTERS, 45,20);
        for(int i = 19; i < 22; i++) for(int j = 44; j < 47; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.HOVEL, 48,20);
        for(int i = 19; i < 22; i++) for(int j = 47; j < 50; j++) map[i][j].setBuilding(building);
        ///////////////////////
        building = new Building(null, BuildingType.CATHEDRAL, 2,12);
        for(int i = 10; i < 15; i++) for(int j = 0; j < 5; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.CHURCH, 6,12);
        for(int i = 11; i < 14; i++) for(int j = 5; j < 8; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.ARMORER, 9,12);
        for(int i = 11; i < 14; i++) for(int j = 8; j < 11; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.FLETCHER, 12,12);
        for(int i = 11; i < 14; i++) for(int j = 11; j < 14; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.POLETURNER, 15,12);
        for(int i = 11; i < 14; i++) for(int j = 14; j < 17; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.OIL_SMELTER, 18,12);
        for(int i = 11; i < 14; i++) for(int j = 17; j < 20; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.STABLE, 21,12);
        for(int i = 11; i < 14; i++) for(int j = 20; j < 23; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BLACKSMITH, 24,12);
        for(int i = 11; i < 14; i++) for(int j = 23; j < 26; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.APPLE_ORCHARD, 27,12);
        for(int i = 11; i < 14; i++) for(int j = 26; j < 29; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.DIARY_FARMER, 30,12);
        for(int i = 11; i < 14; i++) for(int j = 29; j < 32; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.HOPS_FARMER, 33,12);
        for(int i = 11; i < 14; i++) for(int j = 32; j < 35; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.HUNTERS_POST, 36,12);
        for(int i = 11; i < 14; i++) for(int j = 35; j < 38; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.WHEAT_FARMER, 39,12);
        for(int i = 11; i < 14; i++) for(int j = 38; j < 41; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BAKERY, 42,12);
        for(int i = 11; i < 14; i++) for(int j = 41; j < 44; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BREWER, 45,12);
        for(int i = 11; i < 14; i++) for(int j = 44; j < 47; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.MAIN_CASTLE, 48,12);
        for(int i = 11; i < 14; i++) for(int j = 47; j < 50; j++) map[i][j].setBuilding(building);





        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
    }

    private void makeMapDraggable() {
        mainPane.setOnMousePressed(e -> {
            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();
        });
        mainPane.setOnMouseDragged(e -> {
            mapPointerX += e.getSceneX() - lastMouseX;
            mapPointerY += e.getSceneY() - lastMouseY;
            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        });
    }


    private void zoomOut() {
        if (mainCanvas.getScaleX() > 0.5) {
            mainCanvas.setScaleX(mainCanvas.getScaleX() / 1.5);
            mainCanvas.setScaleY(mainCanvas.getScaleY() / 1.5);
        }
        fitCanvasToCenter();
    }

    private void zoomIn() {
        if (mainCanvas.getScaleX() < 4) {
            mainCanvas.setScaleX(mainCanvas.getScaleX() * 1.5);
            mainCanvas.setScaleY(mainCanvas.getScaleY() * 1.5);
        }
        fitCanvasToCenter();
    }
    private void fitCanvasToCenter() {
        System.out.println(mainCanvas.getWidth());
        mainCanvas.setLayoutX(canvasPane.getWidth()/2 - mainCanvas.getWidth()/2);
        mainCanvas.setLayoutY(canvasPane.getHeight()/2 - mainCanvas.getHeight()/2);
    }

    private Scene sceneMaker() throws Exception {
        mainStackPane = FXMLLoader.load(GameMenu.class.getResource("/FXML/GameMenu.fxml"));
        mainBorderPane = (BorderPane) mainStackPane.getChildren().get(0);
        mainPane = (Pane) mainStackPane.getChildren().get(1);
        canvasPane = (Pane) mainBorderPane.getCenter();
        bottomHBox = (HBox) mainBorderPane.getBottom();
        mainCanvas = (Canvas) canvasPane.getChildren().get(0);
        Scene scene = new Scene(mainStackPane);
        return scene;
    }

//    public void initialize() {
//        Tile[][] map = Data.loadMap("test");
//        mainCanvas.setHeight(800);
//        mainCanvas.setWidth(1000);
//        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
//    }
}
