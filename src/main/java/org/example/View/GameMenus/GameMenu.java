package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.Data;
import org.example.Model.Tile;

public class GameMenu extends Application {
    static Stage stage;
    public StackPane mainStackPane;
    public BorderPane mainBorderPane;
    public Canvas mainCanvas;
    public HBox bottomHBox;
    public Pane mainPane;
    public Pane canvasPane;
    // map things
    int mapPointerX = 300;
    int mapPointerY = 1000;
    //

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        mainStackPane = FXMLLoader.load(GameMenu.class.getResource("/FXML/GameMenu.fxml"));
        Scene scene = new Scene(mainStackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        Tile[][] map = Data.loadMap("test");
        mainCanvas.setHeight(800);
        mainCanvas.setWidth(1000);
        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
    }
}
