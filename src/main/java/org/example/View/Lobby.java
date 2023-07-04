package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Model.Data;
import org.example.Model.User;
import org.example.Model.WaitingGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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
        pane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/279547.jpg").toExternalForm()))
                , null, null)));
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

    private void setTheMiddleVBox() {
        middleVBox.setSpacing(15);
        Label label = new Label("create a game");
        label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 23; -fx-text-fill: #10ea1d");
        /*TextField textFieldId = new TextField();
        textFieldId.setPromptText("enter an id");
        textFieldId.setStyle("-fx-min-width: 200; -fx-max-width: 200");*/
        Label label1 = new Label("choose the capacity");
        label1.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 16; -fx-text-fill: #10ea1d");
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8);
        choiceBox.setValue(2);
        HBox hBox = new HBox(label1, choiceBox);
        hBox.setSpacing(5);
        RadioButton radioButton = new RadioButton("public");
        RadioButton radioButton1 = new RadioButton("private");
        radioButton.setStyle("-fx-text-fill: #10ea1d");
        radioButton1.setStyle("-fx-text-fill: #10ea1d");
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
        create.setStyle("-fx-min-width: 170; -fx-max-width: 170; -fx-background-color: #bb0eb9;" +
                " -fx-text-fill: #fdfdfc; -fx-min-height: 36; -fx-max-height: 36; -fx-font-size: 18;" +
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
        WaitingGame waitingGame1 = null;
        for(WaitingGame waitingGame : Data.getCurrentUser().getAllWaitingGames()){
            if(waitingGame.getAdmin() == Data.getCurrentUser()) {
                waitingGame1 = waitingGame;
                break;
            }
        }
        middleVBox.getChildren().addAll(label, hBox, hBox1, create);
        if(waitingGame1 != null) putTheEnterButton(waitingGame1);
    }

    private void putTheEnterButton(WaitingGame waitingGame){
        VBox vBox = addWaitingRoom(waitingGame);
        Button enter = new Button("enter");
        enter.setStyle("-fx-min-width: 120; -fx-max-width: 120; -fx-background-color: #bb0eb9;" +
                " -fx-text-fill: #fdfdfc; -fx-min-height: 33; -fx-max-height: 33; -fx-font-size: 18;" +
                " -fx-font-family: 'Book Antiqua'");
        enter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                entering(waitingGame);
            }
        });
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        vBox.getChildren().add(enter);
        middleVBox.getChildren().add(vBox);
    }

    private void entering(WaitingGame waitingGame1) {
        //todo
    }

    private void setTheRightVBox(){
        rightVBox.setSpacing(15);
        Button back = new Button("back");
        back.setStyle("-fx-min-width: 120; -fx-max-width: 120; -fx-background-color: #bb0eb9;" +
                " -fx-text-fill: #fdfdfc; -fx-min-height: 33; -fx-max-height: 33; -fx-font-size: 18;" +
                " -fx-font-family: 'Book Antiqua'");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    back();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
        HBox hBox = new HBox(textField, circle);
        VBox vBox = new VBox();
        vBox.setVisible(false);
        rightVBox.getChildren().addAll(back, hBox, vBox);
    }

    private void back() throws Exception {
        new ChatMenu().start(stage);
    }

    private void create(Integer value, boolean isPublic) throws IOException {
        System.out.println("create");
        WaitingGame waitingGame = new WaitingGame(value, Data.getCurrentUser(), isPublic);
        Data.getCurrentUser().createWaitingGameCommand(waitingGame);
        putTheEnterButton(waitingGame);
    }

    private void setTheLeftVBox() {
        for (int i = leftVBox.getChildren().size() - 1; i >= 0; i--)
            leftVBox.getChildren().remove(i);
        Label label = new Label("waiting games");
        label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 23; -fx-text-fill: #10ea1d");
        Circle refresh = new Circle(20);
        refresh.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Icons/refresh.png").toString())));
        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    refresh();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        HBox hBox = new HBox(label, refresh);
        hBox.setSpacing(7);
        leftVBox.setSpacing(15);
        leftVBox.getChildren().add(hBox);
        ArrayList<WaitingGame> waitingGames1 = new ArrayList<>(Data.getCurrentUser().getAllWaitingGames());
        Collections.shuffle(waitingGames1);
        int publicNum = 0;
        for (WaitingGame allWaitingGame : Data.getCurrentUser().getAllWaitingGames()) {
            if(allWaitingGame.isPublic() && !allWaitingGame.isGameStarted()) publicNum++;
        }
        for(int i = 0; i < Math.min(5, publicNum); i++){
            WaitingGame waitingGame = waitingGames1.get(i);
            if(!waitingGame.isPublic() || waitingGame.isGameStarted()){
                waitingGames1.remove(i);
                i--;
                continue;
            }
            VBox vBox = addWaitingRoom(waitingGame);
            leftVBox.getChildren().add(vBox);
        }
    }

    private VBox addWaitingRoom(WaitingGame waitingGame){
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        Label label1 = new Label("Game id : " + waitingGame.getId());
        label1.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20;" +
                "/*-fx-border-color: blue; -fx-border-radius: 20*/");
        vBox.getChildren().add(label1);
        Label label3 = new Label("Capacity : " + waitingGame.getCapacity());
        label3.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 17");
        vBox.getChildren().add(label3);
        for(User user : waitingGame.getPlayers()){
            Label label2 = new Label(user.getUsername() + " : " + user.getNickname());
            label2.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 15");
            vBox.getChildren().add(label2);
        }
        vBox.setStyle("-fx-background-color: #e0fd95; -fx-background-radius: 15; -fx-padding: 10, 10, 10, 10;" +
                " -fx-border-color: blue; -fx-border-radius: 15");
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
        return vBox;
    }

    private void join(WaitingGame waitingGame) throws IOException {
        Data.getCurrentUser().sendToServer();
        //todo
        Data.getCurrentUser().joinCommand(waitingGame);
        refresh();
    }

    private void refresh() throws IOException {
        System.out.println("refresh");
        Data.getCurrentUser().sendToServer();
        setTheLeftVBox();
    }

    private void search(String text) throws IOException {
        if(text == null || text.equals("") || !text.matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid id");
            alert.setHeaderText("id not found");
            alert.showAndWait();
        }
        else {
            int id = Integer.parseInt(text);
            if(Data.getCurrentUser().getWaitingRoomById(id) == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("no room with this id exist");
                alert.setHeaderText("id not found");
                alert.showAndWait();
            }
            else if(Data.getCurrentUser().getWaitingRoomById(id).isGameStarted()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("sorry! this game has started");
                alert.setHeaderText("id not found");
                alert.showAndWait();
            }
            else{
                Data.getCurrentUser().sendToServer();
                rightVBox.getChildren().remove(rightVBox.getChildren().size() - 1);
                rightVBox.getChildren().add(addWaitingRoom(Data.getCurrentUser().getWaitingRoomById(id)));
            }
        }
    }
}
