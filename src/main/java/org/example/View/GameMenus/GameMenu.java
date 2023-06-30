package org.example.View.GameMenus;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;

import java.util.ArrayList;

public class GameMenu extends Application {
    static Stage stage;
    static Scene scene;
    public BorderPane mainBorderPane;
    public StackPane mainStackPane;
    public Canvas mainCanvas;
    public HBox bottomHBox;
    public Pane UIPane;
    public Pane canvasPane;
    // map things
    int mapPointerX = 5000;
    int mapPointerY = 3000;
    double lastMouseX;
    double lastMouseY;
    double mouseX;
    double mouseY;
    private ArrayList<String> buildingIcons = new ArrayList<>();
    private int[] buildingGroup = new int[6];
    private int buildingIndex = 0;
    private HBox buildingHBox, popularityHBox, buildingGroupHBox;
    private BorderPane buildingBorderPane;
    private VBox actionVBox;
    private long mouseLastChangeTime = System.currentTimeMillis();
    private boolean isShowingInformation = false;
    private Label informationLabel;

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        scene = sceneMaker();
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            System.out.println("ininin " + mainCanvas);
            if (e.getCode() == KeyCode.I) zoomIn();
            else if (e.getCode() == KeyCode.O) zoomOut();
            else if (e.getCode() == KeyCode.RIGHT) setBuildingIndex(buildingIndex + 1);
            else if (e.getCode() == KeyCode.LEFT) setBuildingIndex(buildingIndex - 1);
        });
        starter();
        stage.show();
        zoomOut();
        zoomIn();
    }


    Tile[][] map;

    private void starter() {
        setMouseActions();
        map = Data.loadMap("test");
        mainCanvas.setHeight(3000);
        mainCanvas.setWidth(8000);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) map[i][j] = new Tile(TileStructure.DENSE_MEADOW, i, j);
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

        Soldier soldier = new Soldier(5, 0, null, UnitType.ARCHER);
        map[0][5].addSoldier(soldier);
        soldier = new Soldier(5, 0, null, UnitType.SWORDS_MAN);
        map[0][5].addSoldier(soldier);
        soldier = new Soldier(5, 0, null, UnitType.SPEAR_MAN);
        map[0][5].addSoldier(soldier);
        soldier = new Soldier(5, 0, null, UnitType.KNIGHT);
        map[0][5].addSoldier(soldier);

        Soldier archer;
        for (int i = 19; i < 22; i++)
            for (int j = 26; j < 29; j++)
                for (int k = 0; k < 4; k++) {
                    archer = new Soldier(j, i, null, UnitType.ARCHER);
                    map[i][j].addSoldier(archer);
                }


        Building building = new Building(null, BuildingType.INN, 1, 20);
        for (int i = 19; i < 22; i++) for (int j = 0; j < 3; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.ENGINEERS_GUILD, 4, 20);
        for (int i = 19; i < 22; i++) for (int j = 3; j < 6; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.MERCENARY_POST, 7, 20);
        for (int i = 19; i < 22; i++) for (int j = 6; j < 9; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BARRACKS, 10, 20);
        for (int i = 19; i < 22; i++) for (int j = 9; j < 12; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.SMALL_STONE_GATEHOUSE, 13, 20);
        for (int i = 19; i < 22; i++) for (int j = 12; j < 15; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BIG_STONE_GATEHOUSE, 17, 20);
        for (int i = 18; i < 23; i++) for (int j = 15; j < 20; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.PERIMETER_TOWER, 21, 20);
        for (int i = 19; i < 22; i++) for (int j = 20; j < 23; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.DEFENSE_TURRET, 24, 20);
        for (int i = 19; i < 22; i++) for (int j = 23; j < 26; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.SQUARE_TOWER, 27, 20);
        for (int i = 19; i < 22; i++) for (int j = 26; j < 29; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.ROUND_TOWER, 30, 20);
        for (int i = 19; i < 22; i++) for (int j = 29; j < 32; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.IRON_MINE, 33, 20);
        for (int i = 19; i < 22; i++) for (int j = 32; j < 35; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.MARKET, 36, 20);
        for (int i = 19; i < 22; i++) for (int j = 35; j < 38; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.PITCH_RIG, 39, 20);
        for (int i = 19; i < 22; i++) for (int j = 38; j < 41; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.QUARRY, 42, 20);
        for (int i = 19; i < 22; i++) for (int j = 41; j < 44; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.WOODCUTTERS, 45, 20);
        for (int i = 19; i < 22; i++) for (int j = 44; j < 47; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.HOVEL, 48, 20);
        for (int i = 19; i < 22; i++) for (int j = 47; j < 50; j++) map[i][j].setBuilding(building);
        ///////////////////////
        building = new Building(null, BuildingType.CATHEDRAL, 2, 12);
        for (int i = 10; i < 15; i++) for (int j = 0; j < 5; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.CHURCH, 6, 12);
        for (int i = 11; i < 14; i++) for (int j = 5; j < 8; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.ARMORER, 9, 12);
        for (int i = 11; i < 14; i++) for (int j = 8; j < 11; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.FLETCHER, 12, 12);
        for (int i = 11; i < 14; i++) for (int j = 11; j < 14; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.POLETURNER, 15, 12);
        for (int i = 11; i < 14; i++) for (int j = 14; j < 17; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.OIL_SMELTER, 18, 12);
        for (int i = 11; i < 14; i++) for (int j = 17; j < 20; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.STABLE, 21, 12);
        for (int i = 11; i < 14; i++) for (int j = 20; j < 23; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BLACKSMITH, 24, 12);
        for (int i = 11; i < 14; i++) for (int j = 23; j < 26; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.APPLE_ORCHARD, 27, 12);
        for (int i = 11; i < 14; i++) for (int j = 26; j < 29; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.DIARY_FARMER, 30, 12);
        for (int i = 11; i < 14; i++) for (int j = 29; j < 32; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.HOPS_FARMER, 33, 12);
        for (int i = 11; i < 14; i++) for (int j = 32; j < 35; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.HUNTERS_POST, 36, 12);
        for (int i = 11; i < 14; i++) for (int j = 35; j < 38; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.WHEAT_FARMER, 39, 12);
        for (int i = 11; i < 14; i++) for (int j = 38; j < 41; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BAKERY, 42, 12);
        for (int i = 11; i < 14; i++) for (int j = 41; j < 44; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.BREWER, 45, 12);
        for (int i = 11; i < 14; i++) for (int j = 44; j < 47; j++) map[i][j].setBuilding(building);
        building = new Building(null, BuildingType.MAIN_CASTLE, 48, 12);
        for (int i = 11; i < 14; i++) for (int j = 47; j < 50; j++) map[i][j].setBuilding(building);


        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);

    }

    private void setMouseActions() {
        UIPane.setOnMouseClicked(e -> {
            clickedAt(e.getSceneX(), e.getSceneY());
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        });
        UIPane.setOnMouseMoved(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            mouseLastChangeTime = System.currentTimeMillis();
            isShowingInformation = false;
            informationLabel.setVisible(false);
        });
        UIPane.setOnMousePressed(e -> {
            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();
        });
        UIPane.setOnMouseDragged(e -> {
            mapPointerX += e.getSceneX() - lastMouseX;
            mapPointerY += e.getSceneY() - lastMouseY;
            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            fitCanvasToCenter();
            if (System.currentTimeMillis() - mouseLastChangeTime > 500 && !isShowingInformation) {
                String information = getInfoOfPoint(mouseX, mouseY);
                if (information != null) {
                    informationLabel.setText(information);
                    informationLabel.setVisible(true);
                    informationLabel.setLayoutX(mouseX - informationLabel.getWidth());
                    informationLabel.setLayoutY(mouseY);
                }
                isShowingInformation = true;
            }
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void clickedAt(double x, double y) {
        int xx = (int) ((mainCanvas.getWidth() * mainCanvas.getScaleX() / 2 - canvasPane.getWidth() / 2 + x) / mainCanvas.getScaleX());
        int yy = (int) ((mainCanvas.getHeight() * mainCanvas.getScaleX() / 2 - canvasPane.getHeight() / 2 + y) / mainCanvas.getScaleY());
        Unit unit = MapController.getUnitAt(xx, yy);
        if (unit != null) {
            MapController.selectedUnits.clear();
            MapController.selectedUnits.add(unit);
            return;
        }
        Building building = MapController.getBuildingAt(xx, yy);
        if (building != null) if (MapController.selectedBuilding == building) MapController.selectedBuilding = null;
        else MapController.selectedBuilding = building;
    }

    private String getInfoOfPoint(double x, double y) {
        int xx = (int) ((mainCanvas.getWidth() * mainCanvas.getScaleX() / 2 - canvasPane.getWidth() / 2 + x) / mainCanvas.getScaleX());
        int yy = (int) ((mainCanvas.getHeight() * mainCanvas.getScaleX() / 2 - canvasPane.getHeight() / 2 + y) / mainCanvas.getScaleY());
        Unit unit = MapController.getUnitAt(xx, yy);
        if (unit != null) return unit.toString();
        Building building = MapController.getBuildingAt(xx, yy);
        if (building != null) return building.toString();
        return null;
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
        mainCanvas.setLayoutX(canvasPane.getWidth() / 2 - mainCanvas.getWidth() / 2);
        mainCanvas.setLayoutY(canvasPane.getHeight() / 2 - mainCanvas.getHeight() / 2);
    }

    private Scene sceneMaker() throws Exception {
        mainBorderPane = FXMLLoader.load(GameMenu.class.getResource("/FXML/GameMenu.fxml"));
        mainStackPane = (StackPane) mainBorderPane.getCenter();
        UIPane = (Pane) mainStackPane.getChildren().get(1);
        canvasPane = (Pane) mainStackPane.getChildren().get(0);
        bottomHBox = (HBox) mainBorderPane.getBottom();
        kingdomTape();
        mainCanvas = (Canvas) canvasPane.getChildren().get(0);
        Scene scene = new Scene(mainBorderPane);
        informationLabel = new Label("");  // information label
        informationLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-font-size: 10px; -fx-text-fill: white;");
        informationLabel.setVisible(false);
        UIPane.getChildren().add(informationLabel);
        return scene;
    }

    private void setBuildingIndex(int i) {
        if (i >= buildingIcons.size() - 4 || i < 0) return;
        buildingIndex = i;
        showBuildings();
    }

    private void showBuildings() {
        buildingHBox.getChildren().clear();
        buildingHBox.setSpacing(10);
        for (int i = 0; i < 4; i++) {
            ImageView buildingImage = new ImageView(buildingIcons.get(buildingIndex + i));
            buildingImage.setFitHeight(100);
            buildingImage.setFitWidth(100);
            buildingHBox.getChildren().add(buildingImage);
        }
    }

    public void kingdomTape() {
        bottomHBox.setMinHeight(230);
        initBuildingsArray();
        popularityHBox = new HBox();
        buildingBorderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(buildingBorderPane, popularityHBox);
        buildingHBox = new HBox();
        buildingBorderPane.setCenter(buildingHBox);
        buildingGroupHBox = makeGroupHBox();
        buildingBorderPane.setBottom(buildingGroupHBox);
        makeActionVBox();
        buildingBorderPane.setRight(actionVBox);
        bottomHBox.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(GameMenu.class.getResource("/Images/Game/menu.png").toExternalForm())), null, null)));
        bottomHBox.setFocusTraversable(true);
        bottomHBox.getChildren().addAll(stackPane);
        stackPane.setMinHeight(140);
        stackPane.setMaxHeight(140);
        stackPane.setTranslateX(-170);
        stackPane.setMinWidth(520);
        stackPane.setMaxWidth(520);
        setBuildingIndex(0);
        popularityHBox.setVisible(false);
        bottomHBox.setAlignment(Pos.BOTTOM_CENTER);
        popularityHBox.setStyle("-fx-background-color: BLUE");
    }

    private void makeActionVBox() {
        actionVBox = new VBox();
        String address = GameMenu.class.getResource("/Images/Icons/undo.png").toExternalForm();
        ImageView icon = new ImageView(address);
        icon.setOnMouseClicked(mouseEvent -> {
            undo();
        });
        icon.setFitHeight(30);
        icon.setFitWidth(30);
        actionVBox.getChildren().add(icon);
        address = GameMenu.class.getResource("/Images/Icons/briefing.png").toExternalForm();
        icon = new ImageView(address);
        icon.setOnMouseClicked(mouseEvent -> {
            briefing();
        });
        icon.setFitHeight(30);
        icon.setFitWidth(30);
        actionVBox.getChildren().add(icon);
        address = GameMenu.class.getResource("/Images/Icons/delete.png").toExternalForm();
        icon = new ImageView(address);
        icon.setOnMouseClicked(mouseEvent -> {
            delete();
        });
        icon.setFitHeight(30);
        icon.setFitWidth(30);
        actionVBox.getChildren().add(icon);
        address = GameMenu.class.getResource("/Images/Icons/settings.png").toExternalForm();
        icon = new ImageView(address);
        icon.setOnMouseClicked(mouseEvent -> {
            options();
        });
        icon.setFitHeight(30);
        icon.setFitWidth(30);
        actionVBox.getChildren().add(icon);
    }

    private void briefing() {
    }

    private void delete() {

    }

    private void options() {

    }

    private void undo() {

    }

    private HBox makeGroupHBox() {
        //todo make this right
        HBox hBox = new HBox();
        hBox.setSpacing(2);
        for (int i = 0; i < 6; i++) {
            String address = GameMenu.class.getResource("/Images/Game/Buildings/BuildingGroupIcons/building (" + (i + 1) + ").png").toExternalForm();
            ImageView buildingIcon = new ImageView(address);
            buildingIcon.setFitHeight(20);
            buildingIcon.setPreserveRatio(true);
            int finalI = i;
            buildingIcon.setOnMouseClicked(mouseEvent -> {
                buildingIndex = buildingGroup[finalI];
            });
            hBox.getChildren().add(buildingIcon);
        }
        return hBox;
    }

    private void initBuildingsArray() {
        //todo correct this
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                String address = GameMenu.class.getResource("/Images/Game/Buildings/building (" + (j + 1) + ").png").toExternalForm();
                buildingIcons.add(address);
            }
        }
        for (int i = 0; i < 6; i++) {
            buildingGroup[i] = i;
        }
    }

//    public void initialize() {
//        Tile[][] map = Data.loadMap("test");
//        mainCanvas.setHeight(800);
//        mainCanvas.setWidth(1000);
//        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
//    }
}
