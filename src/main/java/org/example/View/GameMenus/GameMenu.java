package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.Data;
import org.example.Model.Tile;
import org.example.Model.TileStructure;

import java.util.ArrayList;

public class GameMenu extends Application {
    static Stage stage;
    static Scene scene;
    public StackPane mainStackPane;
    public BorderPane mainBorderPane;
    public Canvas mainCanvas;
    public static HBox bottomHBox;
    public Pane mainPane;
    public Pane canvasPane;
    // map things
    int mapPointerX = 300;
    int mapPointerY = 1000;
    double lastMouseX;
    double lastMouseY;
    private ArrayList<String> buildingIcons = new ArrayList<>();
    private int[] buildingGroup = new int[6];
    private int buildingIndex = 0;
    private HBox buildingHBox, popularityHBox, buildingGroupHBox;
    private BorderPane buildingBorderPane;
    private VBox actionVBox;

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
    }

    Tile[][] map;
    private void starter() {
        makeMapDraggable();
        map = Data.loadMap("test");
        mainCanvas.setHeight(2000);
        mainCanvas.setWidth(2000);

        for(int i = 0; i < 10 ; i++) for(int j =0; j < 10; j++) map[i][j] = new Tile(TileStructure.DENSE_MEADOW,i,j);

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
        kingdomTape();
        mainCanvas = (Canvas) canvasPane.getChildren().get(0);
        Scene scene = new Scene(mainStackPane);
        return scene;
    }

    private void setBuildingIndex(int i) {
        System.out.println("ttt: " + i);
        if (i >= buildingIcons.size() - 4 || i < 0) return;
        buildingIndex = i;
        showBuildings();
    }

    private void showBuildings() {
        System.out.println("000");
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
            String address = GameMenu.class.getResource("/Images/Game/Buildings/BuildingGroupIcons/building (" + (i+1) + ").png").toExternalForm();
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
                String address = GameMenu.class.getResource("/Images/Game/Buildings/building (" + (j+1) + ").png").toExternalForm();
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
