package org.example.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.example.Model.WaitingGame;

public class GameRoomMenu extends Application {
    private WaitingGame waitingGame;
    private static Stage stage;
    private static Pane pane;
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        GameRoomMenu.stage = stage;
        pane = FXMLLoader.load(ChatMenu.class.getResource("/FXML/GameRoomMenu.fxml"));
        /*pane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/279547.jpg").toExternalForm()))
                , null, null)));*/
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

    public GameRoomMenu(WaitingGame waitingGame) {
        this.waitingGame = waitingGame;
    }

    public void setThePane(){

    }
}
