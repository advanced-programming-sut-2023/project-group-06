package org.example.View.GameMenus;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.View.Commands;
import org.example.View.ProfileMenu;
import org.example.View.Response;
import org.example.View.Graphics.SuperImage;
import org.example.View.MainMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;

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
    private ArrayList<Image> buildingIcons = new ArrayList<>();
    private ArrayList<Image> tileIcons = new ArrayList<>();
    private int[] buildingGroup = new int[6];
    private int buildingIndex = 0;
    private int tileIndex = 0;
    private HBox buildingHBox, popularityHBox, buildingGroupHBox, TilesHBox;
    private BorderPane buildingBorderPane;
    private VBox actionVBox;
    Text currentPlayer;
    Timeline timeline;
    private long mouseLastChangeTime = System.currentTimeMillis();
    private boolean isShowingInformation = false;
    private Label informationLabel;
    private Rectangle selectionRectangle;
    private double SRStartX, SRStartY;
    public Building draggedBuilding;
    // todo final drop position of dragged building on canvas
    private ImageView draggedBuildingImageView;
    private Canvas miniMapCanvas;
    private ArrayList<BuildingType> allBuildingTypes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        scene = sceneMaker();
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.I) zoomIn();
            else if (e.getCode() == KeyCode.O) zoomOut();
            else if (e.getCode() == KeyCode.C) copySelectedBuilding();
            else if (e.getCode() == KeyCode.V) pasteToDraggingBuilding();
            else if (e.getCode() == KeyCode.F) {
                try {
                    nextTurn();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if (e.getCode() == KeyCode.RIGHT) setBuildingIndex(buildingIndex + 1);
            else if (e.getCode() == KeyCode.LEFT) setBuildingIndex(buildingIndex - 1);
            else if(e.getCode() == KeyCode.D) setTileIndex(tileIndex + 1);
            else if(e.getCode() == KeyCode.A) setTileIndex(tileIndex - 1);
        });
        starter();
        stage.show();
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        ChatRoom chatRoom = new ChatRoom(GameController.currentGame.getPlayers(),
                GameController.currentGame.getPlayers().get(0).getUsername());
        /*zoomOut();
        zoomIn();*/
    }

    private void pasteToDraggingBuilding() {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        BuildingType bt = null;
        try {
            System.out.println(cb.getData(DataFlavor.stringFlavor));
            bt = BuildingType.getBuildingTypeByString(cb.getData(DataFlavor.stringFlavor).toString());
            System.out.println(bt);
        } catch (UnsupportedFlavorException | IOException ignored) {
        }
        if (bt == null) return;
        draggedBuilding = new Building(GameController.currentPlayer, bt, 0, 0);
        draggedBuildingImageView.setImage(draggedBuilding.getImg().getImage());
        draggedBuildingImageView.setVisible(true);
        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        MapController.minimapGraphicProcessor(miniMapCanvas, map);
    }

    private void copySelectedBuilding() {
        if (MapController.selectedBuilding == null) return;
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(MapController.selectedBuilding.getBuildingType().getName());
        cb.setContents(strSel, null);
    }


    Tile[][] map;
    private void starter() throws IOException {
        setMouseActions();
        if (GameController.currentGame != null) {
            map = Data.loadMap("test");
            GameController.currentGame.setMap(map, map[0].length, map.length);
            System.out.println(GameController.currentGame.getPlayers().size());
            for (int i = 0; i < GameController.currentGame.getPlayers().size(); i++) {
                //  Kingdom kingdom = GameController.currentGame.getKingdoms().get(i);
                int x = i % 2 == 0 ? 10 : map[0].length - 11, y = i / 2 == 0 ? 10 : map.length - 11;

                //Building building = new Building(kingdom, BuildingType.MAIN_CASTLE, x, y);
                //  map[y][x].setBuilding(building);
                String validColors[] = {"red", "blue", "green", "yellow"};
                String cmd = "main castle -x " + x + " -y " + y + " -color " + validColors[i] + " -d n";
                GameController.putMainCastle(Commands.getMatcher(cmd, Commands.PUT_MAIN_CASTLE));
                GameController.nextTurn();
            }

            Soldier archer;
//            for (int i = 19; i < 22; i++)
//                for (int j = 26; j < 29; j++)
//                    for (int k = 0; k < 4; k++) {
//                        archer = new Soldier(j, i, null, UnitType.ARCHER);
//                        archer.addToFireDamageEachTurn(2);
//                        map[i][j].addSoldier(archer);
//                    }
            Kingdom kingdom = GameController.currentPlayer;
            archer = new Soldier(0, 0, kingdom, UnitType.ARCHER);
            map[0][0].addSoldier(archer);


            Building building = new Building(kingdom, BuildingType.SQUARE_TOWER, 27, 20);
            building.addToFireDamageEachTurn(2);
            for (int i = 19; i < 22; i++) for (int j = 26; j < 29; j++) map[i][j].setBuilding(building);

            for (int i = 0; i < 20; i++) {
                building = new Building(kingdom, BuildingType.WALL, 20, i);
                map[i][20].setBuilding(building);
            }
            building = new Building(kingdom, BuildingType.STAIR, 19, 10);
            map[10][19].setBuilding(building);

            map[1][1].sick = true;
        } else {
            map = Data.loadMap("test");


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
        }


        mainCanvas.setHeight(3000);
        mainCanvas.setWidth(8000);

//        draggedBuilding = new Building(null, BuildingType.HOVEL, 0, 0);
//        draggedBuildingImageView.setImage(draggedBuilding.getImg().getImage());
//        draggedBuildingImageView.setVisible(true);

        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        MapController.minimapGraphicProcessor(miniMapCanvas, map);
    }

    private void setMouseActions() {
        UIPane.setOnMouseClicked(e -> onMouseClickedFunction(e));
        UIPane.setOnMouseMoved(e -> onMouseMovedFunction(e));
        UIPane.setOnMousePressed(e -> onMousePressedFunction(e));
        UIPane.setOnMouseDragged(e -> onMouseDraggedFunction(e));
        UIPane.setOnMouseReleased(e -> {
            selectionRectangle.setVisible(false);
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            timeLineFunction();
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void onMouseClickedFunction(MouseEvent e) {
        if (draggedBuilding == null) {
            if (e.getButton() == MouseButton.PRIMARY) {
                clickedAt(e.getSceneX(), e.getSceneY());
                MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
            }
        } else {

        }
    }

    private void onMouseMovedFunction(MouseEvent e) {
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
        mouseLastChangeTime = System.currentTimeMillis();
        isShowingInformation = false;
        informationLabel.setVisible(false);
        if (draggedBuilding != null) {
            draggedBuildingImageView.setLayoutX(mouseX - 140);
            draggedBuildingImageView.setLayoutY(mouseY - 100);
        }
    }

    private void onMousePressedFunction(MouseEvent e) {
        if (draggedBuilding == null) {
            if (e.getButton() == MouseButton.PRIMARY) {
                SRStartX = e.getSceneX();
                SRStartY = e.getSceneY();
                selectionRectangle.setWidth(0);
                selectionRectangle.setHeight(0);
                selectionRectangle.setVisible(true);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                lastMouseX = e.getSceneX();
                lastMouseY = e.getSceneY();
            }
        } else {
            if (e.getButton() == MouseButton.PRIMARY) {
                dropBuildingFunction();
            } else if (e.getButton() == MouseButton.SECONDARY) {
            }
            draggedBuilding = null;
            draggedBuildingImageView.setVisible(false);
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        }
    }

    private void dropBuildingFunction() {
        int xx = (int) ((mainCanvas.getWidth() * mainCanvas.getScaleX() / 2 - canvasPane.getWidth() / 2 + mouseX) / mainCanvas.getScaleX());
        int yy = (int) ((mainCanvas.getHeight() * mainCanvas.getScaleX() / 2 - canvasPane.getHeight() / 2 + mouseY) / mainCanvas.getScaleY());

        double X = xx - mapPointerX;
        double Y = yy - mapPointerY;
        double I = (X + 2 * Y) / 92;
        double J = I - X / 46;
        int i = -(int) Math.floor(I + 0.5);
        int j = -(int) Math.floor(J + 0.5);

        String cmd = "dropbuilding -x " + i + " -y " + j + " -type \"" + draggedBuilding.getBuildingType().getName() + "\" -d n";
        Response response = GameController.dropBuilding(Commands.getMatcher(cmd, Commands.DROP_BUILDING));
        System.out.println(response);
        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        MapController.minimapGraphicProcessor(miniMapCanvas, map);
    }

    private void onMouseDraggedFunction(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            double x = e.getSceneX(), y = e.getSceneY();
            selectionRectangle.setLayoutX(Math.min(SRStartX, x));
            selectionRectangle.setLayoutY(Math.min(SRStartY, y));
            selectionRectangle.setWidth(Math.abs(SRStartX - x));
            selectionRectangle.setHeight(Math.abs(SRStartY - y));
            updateSelectedUnits();
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        } else if (e.getButton() == MouseButton.SECONDARY) {
            mapPointerX += e.getSceneX() - lastMouseX;
            mapPointerY += e.getSceneY() - lastMouseY;
            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        }
    }

    private void timeLineFunction() {
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
    }

    private void updateSelectedUnits() {
        double x = selectionRectangle.getLayoutX(), y = selectionRectangle.getLayoutY();
        double w = selectionRectangle.getWidth(), h = selectionRectangle.getHeight();
        int xx = (int) ((mainCanvas.getWidth() * mainCanvas.getScaleX() / 2 - canvasPane.getWidth() / 2 + x) / mainCanvas.getScaleX());
        int yy = (int) ((mainCanvas.getHeight() * mainCanvas.getScaleX() / 2 - canvasPane.getHeight() / 2 + y) / mainCanvas.getScaleY());
        int ww = (int) (w / mainCanvas.getScaleX());
        int hh = (int) (h / mainCanvas.getScaleY());
        MapController.selectUnitsIn(xx, yy, ww, hh);
    }

    private void clickedAt(double x, double y) {
        int xx = (int) ((mainCanvas.getWidth() * mainCanvas.getScaleX() / 2 - canvasPane.getWidth() / 2 + x) / mainCanvas.getScaleX());
        int yy = (int) ((mainCanvas.getHeight() * mainCanvas.getScaleX() / 2 - canvasPane.getHeight() / 2 + y) / mainCanvas.getScaleY());

        if (MapController.selectedUnits.isEmpty()) {
            Unit unit = MapController.getUnitAt(xx, yy);
            if (unit != null) {
                MapController.selectedUnits.clear();
                MapController.selectedUnits.add(unit);
                return;
            }
            Building building = MapController.getBuildingAt(xx, yy);
            if (building != null) if (MapController.selectedBuilding == building) MapController.selectedBuilding = null;
            else MapController.selectedBuilding = building;

            Tile tile = MapController.getTileAt(xx, yy);
            if (tile != null) if (MapController.selectedTile == tile) MapController.selectedTile = null;
            else MapController.selectedTile = tile;
        } else {
            MapController.allGoTo(xx, yy);
        }
    }

    private String getInfoOfPoint(double x, double y) {
        int xx = (int) ((mainCanvas.getWidth() * mainCanvas.getScaleX() / 2 - canvasPane.getWidth() / 2 + x) / mainCanvas.getScaleX());
        int yy = (int) ((mainCanvas.getHeight() * mainCanvas.getScaleX() / 2 - canvasPane.getHeight() / 2 + y) / mainCanvas.getScaleY());
        Unit unit = MapController.getUnitAt(xx, yy);
        if (unit != null) return unit.toString();
        Building building = MapController.getBuildingAt(xx, yy);
        if (building != null) return building.toString();
        Tile tile = MapController.getTileAt(xx, yy);
        if (tile != null) return tile.toZtring();
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
        currentPlayer = new Text("current player: " + GameController.currentPlayer.getOwner().getUsername());
        mainBorderPane.getChildren().add(currentPlayer);
        currentPlayer.setX(20);
        currentPlayer.setY(25);
        currentPlayer.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20;");
        currentPlayer.setFill(Color.WHITE);
        mainCanvas = (Canvas) canvasPane.getChildren().get(0);
        Scene scene = new Scene(mainBorderPane);
        informationLabel = new Label("");  // information label
        informationLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-font-size: 10px; -fx-text-fill: white;");
        informationLabel.setVisible(false);
        UIPane.getChildren().add(informationLabel);
        selectionRectangle = new Rectangle(0, 0, 0, 0);  // selection rectangle
        selectionRectangle.setFill(Color.CYAN);
        selectionRectangle.setOpacity(0.4);
        selectionRectangle.setVisible(false);
        UIPane.getChildren().add(selectionRectangle);
        draggedBuildingImageView = new ImageView();  // dragged building image view
        draggedBuildingImageView.setScaleX(0.3);
        draggedBuildingImageView.setScaleY(0.3);
        draggedBuildingImageView.setVisible(false);
        UIPane.getChildren().add(draggedBuildingImageView);
        miniMapCanvas = new Canvas(200, 200);  // mini map canvas
        UIPane.getChildren().add(miniMapCanvas);
        miniMapCanvas.setLayoutX(10);
        miniMapCanvas.setLayoutY(10);
        miniMapCanvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            double w = miniMapCanvas.getWidth();
            double h = miniMapCanvas.getHeight();
            int X = (int) (x / w * map[0].length);
            int Y = (int) (y / h * map.length);
            mapPointerX = ((int) mainCanvas.getWidth()) / 2 + (X - Y) * 46; // mapPtrX  = mainCanvas.width/2 + (x-y) * 46
            mapPointerY = ((int) mainCanvas.getHeight()) / 2 + (X + Y) * 23;
            MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        });
        return scene;
    }

    private void setBuildingIndex(int i) {
        if (i >= buildingIcons.size() - 4 || i < 0) return;
        buildingIndex = i;
        showBuildings();
    }

    private void setTileIndex(int i){
        if(i >= tileIcons.size() - 4 || i < 0) return;
        tileIndex = i;
        showTiles();
    }

    private void showBuildings() {
        buildingHBox.getChildren().clear();
        buildingHBox.setSpacing(10);
        for (int i = 0; i < 4; i++) {
            ImageView buildingImage = new ImageView(buildingIcons.get(buildingIndex + i));
            int finalI = i;
            buildingImage.setOnMouseClicked(mouseEvent -> {
                //todo doctor
                handleClicked(buildingIndex + finalI);
            });
            buildingImage.setFitHeight(100);
            buildingImage.setFitWidth(100);
            buildingHBox.getChildren().add(buildingImage);
        }
    }

    private void handleClicked(int index) {
        if (index >= allBuildingTypes.size()) return;
        Building building = new Building(null, allBuildingTypes.get(index), 0, 0);
        draggedBuilding = building;
        draggedBuildingImageView.setVisible(true);
        draggedBuildingImageView.setImage(building.getImg().getImage());
    }

    private void showTiles() {
        TilesHBox.getChildren().clear();
        TilesHBox.setSpacing(15);
        for(int i = 0; i < 4; i++){
            ImageView tileImage = new ImageView(tileIcons.get(tileIndex + i));
            tileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    setTileTexture(tileImage);
                }
            });
            tileImage.setFitWidth(100);
            tileImage.setFitHeight(50);
            TilesHBox.getChildren().add(tileImage);
        }
    }

    private void setTileTexture(ImageView tileImage) {

    }

    public void kingdomTape() {
        bottomHBox.setMinHeight(200);
        initBuildingsArray();
        initTileIcons();
        popularityHBox = new HBox();
        buildingBorderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(buildingBorderPane, popularityHBox);
        buildingHBox = new HBox();
        TilesHBox = new HBox();///////
        buildingBorderPane.setCenter(buildingHBox);
        buildingGroupHBox = makeGroupHBox();
        buildingBorderPane.setBottom(buildingGroupHBox);
        makeActionVBox();
        buildingBorderPane.setRight(actionVBox);
        /////////////
        int x = 480;
        int y = 130;
        buildingBorderPane.setMaxWidth(x);
        buildingBorderPane.setMinWidth(x);
        buildingBorderPane.setMaxHeight(y);
        buildingBorderPane.setMinHeight(y);
        stackPane.getChildren().add(TilesHBox);
        TilesHBox.setVisible(false);
        ////////////////
        bottomHBox.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(GameMenu.class.getResource("/Images/Game/menu.png").toExternalForm())), null, null)));
        bottomHBox.setFocusTraversable(true);
        bottomHBox.getChildren().addAll(stackPane);
        stackPane.setMinHeight(140);
        stackPane.setMaxHeight(140);
        stackPane.setTranslateX(-100);
        stackPane.setMinWidth(520);
        stackPane.setMaxWidth(520);
        setBuildingIndex(0);
        setTileIndex(0);///////////
        popularityHBox.setVisible(false);
        bottomHBox.setAlignment(Pos.BOTTOM_CENTER);
        Text happiness = new Text(Integer.toString(GameController.currentPlayer.getHappiness()));
        happiness.setTranslateX(-70);
        happiness.setTranslateY(-60);
        happiness.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 18");
        happiness.setFill(Color.GREEN);
        happiness.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popularityBar(popularityHBox, buildingHBox);
            }
        });
        Text wealth = new Text(Integer.toString(GameController.currentPlayer.getWealth()));
        wealth.setTranslateX(-110);
        wealth.setTranslateY(-40);
        wealth.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 18");
        wealth.setFill(Color.BLUE);
        Text population = new Text(GameController.currentPlayer.getPopulation() + "/" +
                GameController.currentPlayer.getMaxPopulation());
        population.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 18");
        population.setFill(Color.RED);
        population.setTranslateX(-150);
        population.setTranslateY(-20);
        bottomHBox.getChildren().addAll(happiness, wealth, population);
        setPopularityHBox(popularityHBox);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), actionEvent -> {
            wealth.setText(Integer.toString(GameController.currentPlayer.getWealth()));
            happiness.setText(Integer.toString(GameController.currentPlayer.getHappiness()));
            population.setText(GameController.currentPlayer.getPopulation() + "/" +
                    GameController.currentPlayer.getMaxPopulation());
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        this.timeline = timeline;
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
            ImageView buildingIcon = new ImageView(buildingIcons.get(buildingGroup[i]));
            System.out.println("buildingGroup[" + i + "]" + " = " + buildingGroup[i]);
            buildingIcon.setFitHeight(20);
            buildingIcon.setPreserveRatio(true);
            int finalI1 = i;
            buildingIcon.setOnMouseClicked(mouseEvent -> {
                setBuildingIndex(buildingGroup[finalI1]);
            });
            hBox.getChildren().add(buildingIcon);
        }
        return hBox;
    }

    private void initBuildingsArray() {
        //todo correct this
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 16; j++) {
//                String address = GameMenu.class.getResource("/Images/Game/Buildings/building (" + (j+1) + ").png").toExternalForm();
//                buildingIcons.add(address);
//            }
//        }

        for (BuildingType value : BuildingType.values()) {
            buildingIcons.add(value.getSuperImage().getImage());
            allBuildingTypes.add(value);
        }

        int size = 0;
        for (BuildingType value : BuildingType.values()) {
            size++;
        }
        int cnt = 0;
        int i = 0;
        for (BuildingType value : BuildingType.values()) {
            if (i == 6) break;
            if (cnt == ((size * i) / 6)) {
                buildingGroup[i] = cnt;
                System.out.println("cnt: " + cnt);
                i++;
            }
            cnt++;
        }
        System.err.println("++++++++++++   " + size + " " + buildingGroup);
    }

    public void setPopularityHBox(HBox popularityHBox) {
        Text text = new Text("Popularity");
        text.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 23");
        text.setTranslateX(20);
        text.setTranslateY(20);
        HBox food = new HBox();
        Kingdom kingdom = GameController.currentPlayer;
        int food1 = kingdom.showPopularityFactorsFood();
        String sign = "";
        if(food1 > 0) sign = "+";
        Text text1 = new Text(sign + food1);
        /*ImageView imageView = new ImageView(MainMenu.class.getResource("Images/Game/mask1.jpg").toString());
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(20);
        food.getChildren().addAll(text1, imageView);*/
        food.setTranslateX(40 - text1.getBoundsInLocal().getWidth());
        food.setTranslateY(30);
        Text text2 = new Text("Food");
        text1.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        text2.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        String maskImage5 = food1 > 0 ? "8" : food1 < 0 ? "9" : "10";
        Circle mask5 = new Circle(10);
        mask5.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/mask" + maskImage5 + ".png").toString())));
        food.getChildren().addAll(text1, mask5, text2);
        food.setSpacing(5);
        HBox tax = new HBox();
        int tax1 = kingdom.showPopularityFactorsTax();
        String sign1 = "";
        if(tax1 > 0) sign1 = "+";
        Text text3 = new Text(sign1 + tax1);
        tax.setTranslateX(text1.getBoundsInLocal().getWidth() - text3.getBoundsInLocal().getWidth()
                - text2.getBoundsInLocal().getWidth() - 10);
        tax.setTranslateY(50);
        Text text4 = new Text("Tax");
        text3.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        text4.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        String maskImage = tax1 > 0 ? "8" : tax1 < 0 ? "9" : "10";
        Circle mask = new Circle(10);
        mask.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/mask" + maskImage + ".png").toString())));
        tax.getChildren().addAll(text3, mask, text4);
        tax.setSpacing(5);
        HBox fear = new HBox();
        int fear1 = kingdom.showPopularityFactorsFear();
        String sign2 = "";
        if(fear1 > 0) sign2 = "+";
        Text text5 = new Text(sign2 + fear1);
        fear.setTranslateX(-87);
        fear.setTranslateY(70);
        Text text6 = new Text("Fear");
        text5.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        text6.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        String maskImage1 = fear1 > 0 ? "8" : fear1 < 0 ? "9" : "10";
        Circle mask1 = new Circle(10);
        mask1.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/mask" + maskImage1 + ".png").toString())));
        fear.getChildren().addAll(text5, mask1, text6);
        fear.setSpacing(5);
        HBox religion = new HBox();
        int religion1 = kingdom.showPopularityFactorsReligion();
        String sign3 = "";
        if(religion1 > 0) sign3 = "+";
        Text text7 = new Text(sign3 + religion1);
        religion.setTranslateX(-text7.getBoundsInLocal().getWidth());
        religion.setTranslateY(30);
        Text text8 = new Text("Religion");
        text7.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        text8.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        String maskImage2 = religion1 > 0 ? "8" : religion1 < 0 ? "9" : "10";
        Circle mask2 = new Circle(10);
        mask2.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/mask" + maskImage2 + ".png").toString())));
        religion.getChildren().addAll(text7, mask2, text8);
        religion.setSpacing(5);
        HBox wine = new HBox();
        int wine1 = kingdom.showPopularityFactorsWine();
        String sign4 = "";
        if(wine1 > 0) sign4 = "+";
        Text text9 = new Text(sign4 + wine1);
        wine.setTranslateX(-83 - text9.getBoundsInLocal().getWidth());
        wine.setTranslateY(50);
        Text text10 = new Text("Wine Usage");
        text9.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        text10.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20");
        String maskImage3 = wine1 > 0 ? "8" : wine1 < 0 ? "9" : "10";
        Circle mask3 = new Circle(10);
        mask3.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/mask" + maskImage3 + ".png").toString())));
        wine.getChildren().addAll(text9, mask3, text10);
        wine.setSpacing(5);
        Button back = new Button("Back");
        Button rate = new Button("Rate");
        String style3 = "-fx-min-width: 80; -fx-max-width: 80; -fx-background-color: #e81625;" +
                " -fx-text-fill: #fdd94b; -fx-min-height: 23; -fx-max-height: 23; -fx-font-size: 12;" +
                "-fx-font-family: 'Book Antiqua'";
        back.setStyle(style3);
        rate.setStyle(style3);
        back.setTranslateX(-540);
        back.setTranslateY(80);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                reversePopularityBar(popularityHBox);
            }
        });
        popularityHBox.setMaxWidth(500);
        popularityHBox.setMinWidth(500);
        popularityHBox.getChildren().addAll(text, food, tax, fear, religion, wine, back/*, rate*/);
    }

    private void makeTilesVisible(){
        TilesHBox.setVisible(true);
        buildingBorderPane.setVisible(false);
        popularityHBox.setVisible(false);
    }

    private void reversePopularityBar(HBox popularityHBox) {
        TilesHBox.setVisible(false);
        popularityHBox.setVisible(false);
        buildingBorderPane.setVisible(true);
    }

    private void popularityBar(HBox popularityHBox, HBox buildingHBox) {
        TilesHBox.setVisible(false);
        popularityHBox.setVisible(true);
        buildingBorderPane.setVisible(false);
    }

    public void nextTurn() throws IOException {
        System.out.println("salam");
        GameController.nextTurn();
        currentPlayer.setText("current player: " + GameController.currentPlayer.getOwner().getUsername());
        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
        MapController.minimapGraphicProcessor(miniMapCanvas, map);
    }

//    public void initialize() {
//        Tile[][] map = Data.loadMap("test");
//        mainCanvas.setHeight(800);
//        mainCanvas.setWidth(1000);
//        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
//    }
}
