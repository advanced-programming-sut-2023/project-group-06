package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.KingdomController;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.Data;
import org.example.Model.Kingdom;
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
    public static HBox bottomHBox;
    public Pane mainPane;
    public Pane canvasPane;
    // map things
    int mapPointerX = 300;
    int mapPointerY = 1000;
    double lastMouseX;
    double lastMouseY;
    Text currentPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        scene = sceneMaker();
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            System.out.println("ininin " + mainCanvas);
            if (e.getCode() == KeyCode.I) zoomIn();
            else if (e.getCode() == KeyCode.O) zoomOut();
            else if (e.getCode() == KeyCode.F) nextTurn();
        });
        starter();
        if (!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    Tile[][] map;

    private void starter() {
        makeMapDraggable();
        map = Data.loadMap("test");
        mainCanvas.setHeight(2000);
        mainCanvas.setWidth(2000);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) map[i][j] = new Tile(TileStructure.DENSE_MEADOW, i, j);

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
        mainCanvas.setLayoutX(canvasPane.getWidth() / 2 - mainCanvas.getWidth() / 2);
        mainCanvas.setLayoutY(canvasPane.getHeight() / 2 - mainCanvas.getHeight() / 2);
    }

    private Scene sceneMaker() throws Exception {
        mainStackPane = FXMLLoader.load(GameMenu.class.getResource("/FXML/GameMenu.fxml"));
        mainBorderPane = (BorderPane) mainStackPane.getChildren().get(0);
        mainPane = (Pane) mainStackPane.getChildren().get(1);
        canvasPane = (Pane) mainBorderPane.getCenter();
        bottomHBox = (HBox) mainBorderPane.getBottom();
        kingdomTape();
        currentPlayer = new Text("current player: " + GameController.currentPlayer.getOwner().getUsername());
        mainBorderPane.getChildren().add(currentPlayer);
        currentPlayer.setX(20);
        currentPlayer.setY(25);
        currentPlayer.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20;");
        currentPlayer.setFill(Color.WHITE);
        mainCanvas = (Canvas) canvasPane.getChildren().get(0);
        Scene scene = new Scene(mainStackPane);
        return scene;
    }

    public HBox kingdomTape() {
        HBox hBox = bottomHBox;
        hBox.setMinHeight(170);
        HBox buildingHBox = new HBox(), popularityHBox = new HBox();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(buildingHBox, popularityHBox);
        hBox.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(
                GameMenu.class.getResource("/Images/Game/menu.png").toExternalForm())), null, null)));
        hBox.getChildren().addAll(stackPane);
        stackPane.setMinHeight(111);
        stackPane.setMaxHeight(111);
        stackPane.setTranslateX(-93);
        stackPane.setMinWidth(500);
        stackPane.setMaxWidth(500);
        buildingHBox.setVisible(false);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        buildingHBox.setStyle("-fx-background-color: RED");
        buildingHBox.getChildren().addAll(new Text("s"), new Button("ssss"));
        Button button = new Button("aa");
        button.setOnMouseClicked(mouseEvent -> {
            System.out.println("slam");
        });
        buildingHBox.getChildren().add(button);
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
        hBox.getChildren().addAll(happiness, wealth, population);
        setPopularityHBox(popularityHBox);
        return hBox;
    }

    public void setPopularityHBox(HBox popularityHBox) {
        Text text = new Text("Popularity");
        text.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 23");
        text.setTranslateX(10);
        text.setTranslateY(10);
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
        food.getChildren().addAll(text1, text2);
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
        tax.getChildren().addAll(text3, text4);
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
        fear.getChildren().addAll(text5, text6);
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
        religion.getChildren().addAll(text7, text8);
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
        wine.getChildren().addAll(text9, text10);
        wine.setSpacing(5);
        Button back = new Button("Back");
        Button rate = new Button("Rate");
        String style3 = "-fx-min-width: 80; -fx-max-width: 80; -fx-background-color: #e81625;" +
                " -fx-text-fill: #fdd94b; -fx-min-height: 23; -fx-max-height: 23; -fx-font-size: 12;" +
                "-fx-font-family: 'Book Antiqua'";
        back.setStyle(style3);
        rate.setStyle(style3);
        popularityHBox.getChildren().addAll(text, food, tax, fear, religion, wine/*, back, rate*/);
    }

    private void popularityBar(HBox popularityHBox, HBox buildingHBox) {
        popularityHBox.setVisible(true);
        buildingHBox.setVisible(false);
    }

    public void nextTurn() {
        System.out.println("salam");
        GameController.nextTurn();
    }

//    public void initialize() {
//        Tile[][] map = Data.loadMap("test");
//        mainCanvas.setHeight(800);
//        mainCanvas.setWidth(1000);
//        MapController.mapGraphicProcessor(mainCanvas, map, mapPointerX, mapPointerY);
//    }
}
