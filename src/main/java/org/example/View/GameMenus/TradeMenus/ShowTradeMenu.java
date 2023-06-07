package org.example.View.GameMenus.TradeMenus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class ShowTradeMenu extends Application {
    private static Stage stage;
    private static BorderPane borderPane;
    private Scene scene;
    private static VBox mainVbox;

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = FXMLLoader.load(new URL(
                TradeMenu.class.getResource("/FXML/TradeMenu.fxml").toExternalForm()
        ));
        ShowTradeMenu.stage = stage;
        Scene scene = new Scene(borderPane);
        setThePain();
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    private void setThePain() {
        VBox rightVBox = new VBox();
        VBox leftVBox = new VBox();
        rightVBox.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.CENTER);
        rightVBox.setSpacing(20);
        leftVBox.setSpacing(20);
        rightVBox.setStyle("-fx-border-color: #420627; -fx-background-color: blue");
        leftVBox.setStyle("-fx-border-color: #420627; -fx-background-color: green");
        setTheRightVBox(rightVBox);
        setTheLeftVBox(leftVBox);
        HBox hBox = new HBox(leftVBox, rightVBox);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        Button button = new Button("lkjgk");
        borderPane.getChildren().add(rightVBox);
        borderPane.getChildren().add(button);
    }

    private void setTheLeftVBox(VBox vBox) {

    }

    private void setTheRightVBox(VBox vBox){

    }
}
