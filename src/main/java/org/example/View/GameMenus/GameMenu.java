package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
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
    public static HBox bottomHBox;
    public Pane UIPane;
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
            else if (e.getCode() == KeyCode.RIGHT) setBuildingIndex(buildingIndex + 1);
            else if (e.getCode() == KeyCode.LEFT) setBuildingIndex(buildingIndex - 1);
            else if (e.getCode() == KeyCode.F) nextTurn();
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
        stackPane.setMinHeight(111);
        stackPane.setMaxHeight(111);
        stackPane.setTranslateX(-93);
        stackPane.setMinWidth(500);
        stackPane.setMaxWidth(500);
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
