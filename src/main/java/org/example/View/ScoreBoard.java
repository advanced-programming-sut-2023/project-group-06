package org.example.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.Model.Data;
import org.example.Model.User;

import java.util.Objects;

public class ScoreBoard extends Application {
    public VBox mainVBox;
    private static Stage stage;
    private Scene scene;
    private static BorderPane borderPane;

    @Override
    public void start(Stage stage) throws Exception {
        ScoreBoard.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/ScoreBoard.fxml"));
        ScrollPane scrollPane = new ScrollPane(borderPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scene = new Scene(scrollPane);
//        stage.setFullScreen(true);
        setThePain();
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        int i = 0;
        Data.sortUsersByHighScore();
        for(User user : Data.getUsers()){
            i++;
            if(i > 10) break;
            addUserToScoreBoard(mainVBox, i, user);
        }
    }

    public void setThePain(){

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(stage);
    }

    private void addUserToScoreBoard(VBox vBox, int rank, User user){
        HBox hBox = new HBox();
        hBox.setSpacing(100);
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-border-radius: 20; -fx-border-color: black; -fx-max-width: 800;");
        if(rank == 1) hBox.setStyle("-fx-background-color: gold; -fx-border-radius: 20; -fx-max-width: 800;");
        if(rank == 2) hBox.setStyle("-fx-background-color: silver; -fx-border-radius: 20; -fx-max-width: 800;");
        if(rank == 3) hBox.setStyle("-fx-background-color: #f06000; -fx-border-radius: 20; -fx-max-width: 800;");
        Label label = new Label(user.getUsername());
        Label label1 = new Label(Objects.toString(user.getHighScore()));
        label.setFont(Font.font("Times New Roman", 20));
        label.setStyle("-fx-padding: 10px; -fx-border-radius: 20; -fx-pref-width: 150px");
        label1.setFont(Font.font("Times New Roman", 20));
        label1.setStyle("-fx-padding: 10px; -fx-border-radius: 20px; -fx-pref-width: 150px");
        hBox.getChildren().addAll(label, label1);
        vBox.getChildren().add(hBox);
    }
}
