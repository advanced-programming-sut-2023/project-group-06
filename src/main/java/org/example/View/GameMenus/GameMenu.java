package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.Data;
import org.example.Model.Tile;
import org.example.Model.TileStructure;
import org.example.View.Graphics.SuperImage;
import org.example.View.MainMenu;

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
        System.out.println(MainMenu.class.getResource("/FXML/MainMenu.fxml"));
        System.out.println(GameMenu.class.getResource("/FXML/MainMenu.fxml"));
        System.out.println(GameMenu.class.getResource("/FXML/GameMenu.fxml"));
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
