package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Controller.MainController;
import org.example.Model.Data;
import org.example.View.GameMenus.GameMenu;

import java.io.IOException;

public class MapMenu extends Application {
    private static Stage stage;
    private static Pane pane;
    private Scene scene;
    private VBox leftVBox = new VBox();
    private VBox middleVBox = new VBox();
    private VBox rightVBox = new VBox();
    private boolean isPublic = true;

    @Override
    public void start(Stage stage) throws Exception {
        MapMenu.stage = stage;
        pane = FXMLLoader.load(ChatMenu.class.getResource("/FXML/MapMenu.fxml"));
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        pane.requestFocus();
        scene = new Scene(scrollPane);
        stage.setScene(scene);
        setThePane();
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    private void setThePane() {
        setTheLeftVBox();
        setTheMiddleVBox();
        setTheRightVBox();
        pane.getChildren().add(leftVBox);
        pane.getChildren().add(middleVBox);
        pane.getChildren().add(rightVBox);
        middleVBox.setLayoutX(500);
        middleVBox.setLayoutY(100);
        leftVBox.setLayoutX(100);
        leftVBox.setLayoutY(100);
        rightVBox.setLayoutY(100);
        rightVBox.setLayoutX(900);
    }

    private void setTheRightVBox() {
    }

    private void setTheMiddleVBox() {
        HBox searchHBox = new HBox();
        TextField textField = new TextField();
        textField.setPromptText("search");
        textField.setStyle("-fx-min-width: 200; -fx-max-width: 200");
        Circle circle = new Circle(20);
        circle.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Icons/search.png").toString())));
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    search(textField.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        searchHBox.getChildren().addAll(textField, circle);
        middleVBox.getChildren().add(searchHBox);
    }

    private void search(String text) throws IOException {
        System.out.println("map search working");
        if (!Data.getCurrentUser().downloadMap(text)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ALREADY HAVE THIS MAP");
            alert.showAndWait();
        }
        setTheLeftVBox();
    }

    private void setTheLeftVBox() {
        for (int i = leftVBox.getChildren().size() - 1; i >= 0; i--) {
            leftVBox.getChildren().remove(i);
        }
        for (String s : Data.getCurrentUser().getMapNamesIHave()) {
            Button button = new Button(s);
            button.setOnMouseClicked(mouseEvent -> {
                try {
                    Response response = MainController.startGame(Translator.getMatcherByGroups(
                            Translator.START_GAME,  "sina", null, null, null, null, null, null));
                    System.out.println(response.message);
                    new GameMenu(s).start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            leftVBox.getChildren().add(button);
        }
    }

}
