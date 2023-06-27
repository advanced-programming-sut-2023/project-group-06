package org.example.View.GameMenus.TradeMenus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.GameController;
import org.example.Model.TradeRequest;

import java.net.URL;

public class ShowTradeMenu extends Application {
    public static Stage stage;
    public static Pane pane;
    public Scene scene;
    public static VBox mainVbox;

    @Override
    public void start(Stage stage) throws Exception {
        ShowTradeMenu.stage = stage;
        /*pane = FXMLLoader.load(new URL(
                TradeMenu.class.getResource("/FXML/ShowTradesMenu.fxml").toExternalForm()
        ));*/
        pane = new Pane();
        setThePain();
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scene = new Scene(scrollPane);
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    private void setThePain() {
        VBox rightVBox = new VBox();
        VBox leftVBox = new VBox();
        Label label = new Label("sent requests");
        Label label1 = new Label("received requests");
        label.setLayoutX(250);
        label.setLayoutY(30);
        label1.setLayoutX(900);
        label1.setLayoutY(30);
        String style = "-fx-font-family: 'Book Antiqua'; -fx-font-size: 30";
        label.setStyle(style);
        label1.setStyle(style);
        rightVBox.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.CENTER);
        rightVBox.setSpacing(20);
        leftVBox.setSpacing(20);
        rightVBox.setLayoutY(100);
        rightVBox.setLayoutX(50);
        leftVBox.setLayoutX(690);
        leftVBox.setLayoutY(100);
        rightVBox.setMinWidth(600);
        rightVBox.setMinHeight(300);
        leftVBox.setMinWidth(600);
        leftVBox.setMinHeight(300);
        rightVBox.setStyle("-fx-border-color: #bd116f; -fx-background-color: #d0a333;" +
                "-fx-border-radius: 20; -fx-background-radius: 20");
        leftVBox.setStyle("-fx-border-color: #bd116f; -fx-background-color: #d0a333;" +
                "-fx-border-radius: 20; -fx-background-radius: 20");
        setTheRightVBox(rightVBox);
        setTheLeftVBox(leftVBox);
        Button back = new Button("back");
        back.setLayoutX(75);
        back.setLayoutY(30);
        back.setStyle("-fx-min-width: 80; -fx-max-width: 80; -fx-background-color: #e81625;" +
                "-fx-text-fill: #fdd94b; -fx-min-height: 30; -fx-max-height: 30; -fx-font-size: 18;" +
                "-fx-font-family: 'Book Antiqua'; -fx-padding: 5,5,5,5;");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                back();
            }
        });
        pane.getChildren().add(label);
        pane.getChildren().add(label1);
        pane.getChildren().add(rightVBox);
        pane.getChildren().add(leftVBox);
    }

    private void back() {

    }

    private void setTheLeftVBox(VBox vBox) {
        Label label3 = new Label("request");
        Label label4 = new Label("sender");
        Label label5 = new Label("status");
        Label label6 = new Label("");
        HBox hBox1 = new HBox(label3, label4, label5, label6);
        hBox1.setSpacing(50);
        String style = "-fx-font-family: 'Book Antiqua'; -fx-font-size: 18; -fx-min-width: 70;";
        label3.setStyle(style + "-fx-text-fill: blue;");
        label4.setStyle(style + "-fx-text-fill: blue;");
        label5.setStyle(style + "-fx-text-fill: blue;");
        label6.setStyle("-fx-min-width: 100; -fx-max-width: 100;");
        hBox1.setPadding(new Insets(14, 5, 5, 14));
        vBox.getChildren().add(hBox1);
        for (/*TradeRequest tradeRequest : GameController.currentPlayer.getTradeRequestsSentByMe()*/int i = 0; i < 10; i++) {
            Label label = new Label("trade: " + /*tradeRequest.getId()*/12);
            Label label1 = new Label(/*tradeRequest.getOwner().getOwner().getUsername()*/"mobin");
            Label label2 = new Label();
            label2.setText(/*!tradeRequest.isNotified() ? "unseen" : tradeRequest.isAccepted() ? "accepted" : */"rejected");
            Button button = new Button("see");
            Button button1 = new Button("accept");
            Button button2 = new Button("reject");
            HBox hBox9 = new HBox(button, button1, button2);
            hBox9.setSpacing(3);
            hBox9.setPadding(new Insets(3, 5, 5, 14));
            HBox hBox = new HBox(label, label1, label2, hBox9);
            hBox.setSpacing(50);
            label.setStyle(style);
            label1.setStyle(style);
            label2.setStyle(style);
            String styleButton = "-fx-min-width: 80; -fx-max-width: 80; -fx-background-color: #e81625;" +
                    " -fx-text-fill: #fdd94b; -fx-min-height: 30; -fx-max-height: 30; -fx-font-size: 18;" +
                    "-fx-font-family: 'Book Antiqua'; -fx-padding: 5,5,5,5";
            button.setStyle(styleButton);
            button1.setStyle(styleButton);
            button2.setStyle(styleButton);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    see(/*tradeRequest*/null);
                }
            });
            hBox.setPadding(new Insets(14, 5, 5, 14));
            vBox.getChildren().add(hBox);
        }
    }

    private void see(TradeRequest tradeRequest) {

    }

    private void setTheRightVBox(VBox vBox){
        Label label3 = new Label("request");
        Label label4 = new Label("target");
        Label label5 = new Label("status");
        Label label6 = new Label("");
        HBox hBox1 = new HBox(label3, label4, label5, label6);
        hBox1.setSpacing(80);
        String style = "-fx-font-family: 'Book Antiqua'; -fx-font-size: 18; -fx-min-width: 70;";
        label3.setStyle(style + "-fx-text-fill: blue;");
        label4.setStyle(style + "-fx-text-fill: blue;");
        label5.setStyle(style + "-fx-text-fill: blue;");
        label6.setStyle("-fx-min-width: 100; -fx-max-width: 100;");
        hBox1.setPadding(new Insets(14, 5, 5, 14));
        vBox.getChildren().add(hBox1);
        for (/*TradeRequest tradeRequest : GameController.currentPlayer.getTradeRequestsSentByMe()*/int i = 0; i < 19; i++) {
            Label label = new Label("trade: " + /*tradeRequest.getId()*/12);
            Label label1 = new Label(/*tradeRequest.getSentToWho().getOwner().getUsername()*/"mobin");
            Label label2 = new Label();
            label2.setText(/*!tradeRequest.isNotified() ? "unseen" : tradeRequest.isAccepted() ? "accepted" : */"rejected");
            Button button = new Button("see");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    see(/*tradeRequest*/null);
                }
            });
            HBox hBox = new HBox(label, label1, label2, button);
            hBox.setSpacing(80);
            label.setStyle(style);
            label1.setStyle(style);
            label2.setStyle(style);
            button.setStyle("-fx-min-width: 100; -fx-max-width: 100; -fx-background-color: #e81625;" +
                    " -fx-text-fill: #fdd94b; -fx-min-height: 30; -fx-max-height: 30; -fx-font-size: 18;" +
                    "-fx-font-family: 'Book Antiqua'; -fx-padding: 5,5,5,5");
            hBox.setPadding(new Insets(14, 5, 5, 14));
            vBox.getChildren().add(hBox);
        }
    }
}
