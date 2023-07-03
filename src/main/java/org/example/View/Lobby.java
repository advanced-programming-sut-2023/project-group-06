package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Model.Data;
import org.example.Model.User;
import org.example.Model.WaitingGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Lobby extends Application {
    private static Stage stage;
    private static Pane pane;
    private Scene scene;
    private VBox leftVBox = new VBox();
    private VBox middleVBox = new VBox();
    private VBox rightVBox = new VBox();
    private boolean isPublic = true;

    @Override
    public void start(Stage stage) throws Exception {
        Lobby.stage = stage;
        pane = FXMLLoader.load(ChatMenu.class.getResource("/FXML/Lobby.fxml"));
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
        setTheRightVBox();
        pane.getChildren().add(leftVBox);
        pane.getChildren().add(middleVBox);
        middleVBox.setLayoutX(600);
        middleVBox.setLayoutY(100);
        leftVBox.setLayoutX(100);
        leftVBox.setLayoutY(100);
    }

    private void setTheRightVBox() {
        middleVBox.setSpacing(15);
        Label label = new Label("create a game");
        /*TextField textFieldId = new TextField();
        textFieldId.setPromptText("enter an id");
        textFieldId.setStyle("-fx-min-width: 200; -fx-max-width: 200");*/
        Label label1 = new Label("choose the capacity");
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8);
        choiceBox.setValue(2);
        HBox hBox = new HBox(label1, choiceBox);
        hBox.setSpacing(5);
        RadioButton radioButton = new RadioButton("public");
        RadioButton radioButton1 = new RadioButton("private");
        radioButton.setSelected(true);
        radioButton1.setSelected(false);
        radioButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isPublic = true;
                radioButton1.setSelected(false);
            }
        });
        radioButton1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isPublic = false;
                radioButton.setSelected(false);
            }
        });
        HBox hBox1 = new HBox(radioButton, radioButton1);
        hBox1.setSpacing(6);
        Button create = new Button("create the game");
        create.setStyle("-fx-min-width: 80; -fx-max-width: 80; -fx-background-color: #bb0eb9;" +
                " -fx-text-fill: #fdfdfc; -fx-min-height: 23; -fx-max-height: 23; -fx-font-size: 12;" +
                " -fx-font-family: 'Book Antiqua'");
        create.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    create(choiceBox.getValue(), isPublic);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        middleVBox.getChildren().addAll(label, hBox, hBox1, create);
    }

    private void create(Integer value, boolean isPublic) throws IOException {
        System.out.println("create");
        WaitingGame waitingGame = new WaitingGame(value, Data.getCurrentUser(), isPublic);
        Data.getCurrentUser().createWaitingGameCommand(waitingGame);
    }

    private void setTheLeftVBox() {
        Label label = new Label("waiting games");
        leftVBox.setSpacing(10);
        leftVBox.getChildren().add(label);
        ArrayList<Integer> shuffle = shuffle(Data.getCurrentUser().getAllWaitingGames().size());
        for(int i = 0; i < Math.min(5, shuffle.size()); i++){
            WaitingGame waitingGame = Data.getCurrentUser().getAllWaitingGames().get(shuffle.get(i));
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            Label label1 = new Label("Game id : " + waitingGame.getId());
            label1.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20;" +
                    "-fx-border-color: blue");
            vBox.getChildren().add(label1);
            Label label3 = new Label("Capacity : " + waitingGame.getCapacity());
            label3.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 17");
            vBox.getChildren().add(label3);
            for(User user : waitingGame.getPlayers()){
                Label label2 = new Label(user.getUsername());
                label2.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 15");
                vBox.getChildren().add(label2);
            }
            Button join = new Button("join");
            join.setStyle("-fx-min-width: 80; -fx-max-width: 80; -fx-background-color: #bb0eb9;" +
                    " -fx-text-fill: #fdfdfc; -fx-min-height: 23; -fx-max-height: 23; -fx-font-size: 12;" +
                    " -fx-font-family: 'Book Antiqua'");
            join.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        join(waitingGame);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            vBox.getChildren().add(join);
            leftVBox.getChildren().add(vBox);
        }
    }

    private void join(WaitingGame waitingGame) throws IOException {
        Data.getCurrentUser().joinCommand(waitingGame);
        refresh();
    }

    public ArrayList<Integer> shuffle(int n){
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            arrayList.add(i);
        }
        Collections.shuffle(arrayList);
        return arrayList;
    }

    private void refresh() throws IOException {
        Data.getCurrentUser().sendToServer();
        //todo render
    }

    private void search(int id) throws IOException {
        Data.getCurrentUser().sendToServer();
        //todo
    }
}
