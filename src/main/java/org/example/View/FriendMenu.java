package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Model.Data;
import org.example.Model.FriendRequest;
import org.example.Model.User;

import java.io.IOException;
import java.util.Objects;

public class FriendMenu extends Application {
    private static Stage stage;
    private static Pane pane;
    private Scene scene;
    private static String searched = "";
    
    @Override
    public void start(Stage stage) throws Exception {
        FriendMenu.stage = stage;
        pane = FXMLLoader.load(ChatMenu.class.getResource("/FXML/FriendMenu.fxml"));
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

    public void setThePane(){
        for (int i = pane.getChildren().size() - 1; i >= 0; i--)
            pane.getChildren().remove(i);
        VBox leftVBox = new VBox();
        VBox mainVBox = new VBox();
        VBox rightVBox = new VBox();
        leftVBox.setLayoutY(100);
        leftVBox.setLayoutX(100);
        leftVBox.setSpacing(20);
        mainVBox.setLayoutX(600);
        mainVBox.setLayoutY(100);
        mainVBox.setSpacing(20);
        rightVBox.setSpacing(20);
        rightVBox.setLayoutY(100);
        rightVBox.setLayoutX(900);

        FriendRequest friendRequest0 = new FriendRequest(Data.getUserByName("mobin12"), Data.getCurrentUser());
        FriendRequest friendRequest1 = new FriendRequest(Data.getUserByName("mobin13"), Data.getCurrentUser());
        FriendRequest friendRequest2 = new FriendRequest(Data.getUserByName("mobin14"), Data.getCurrentUser());
        FriendRequest friendRequest3 = new FriendRequest(Data.getUserByName("mobin13"), Data.getCurrentUser());
        FriendRequest friendRequest4 = new FriendRequest(Data.getUserByName("mobin15"), Data.getCurrentUser());
        Data.getCurrentUser().getFriendRequestsReceivedByMe().add(friendRequest0);
        Data.getCurrentUser().getFriendRequestsReceivedByMe().add(friendRequest1);
        Data.getCurrentUser().getFriendRequestsReceivedByMe().add(friendRequest2);
        Data.getCurrentUser().getFriendRequestsReceivedByMe().add(friendRequest3);
        Data.getCurrentUser().getFriendRequestsReceivedByMe().add(friendRequest4);
        Data.getCurrentUser().getMyFriends().add(Data.getUserByName("mobin15"));
        Data.getCurrentUser().getMyFriends().add(Data.getUserByName("mobin16"));
        Data.getCurrentUser().getMyFriends().add(Data.getUserByName("mobin17"));
        Data.getCurrentUser().getMyFriends().add(Data.getUserByName("mobin18"));
        Data.getCurrentUser().getMyFriends().add(Data.getUserByName("mobin19"));
        Data.getCurrentUser().getMyFriends().add(Data.getUserByName("mobin20"));

        Label label = new Label("received requests");
        String style = "-fx-font-family: 'Times New Roman'; -fx-font-size: 20";
        label.setStyle(style);
        leftVBox.getChildren().add(label);
        for (FriendRequest friendRequest : Data.getCurrentUser().getFriendRequestsReceivedByMe()) {
            Label label1 = new Label(friendRequest.getSender().getUsername());
            label1.setStyle("-fx-font-size: 18; -fx-font-family: 'Times New Roman';" +
                    "-fx-background-color: #5ee7bd; -fx-text-fill: black;" +
                    "-fx-border-radius: 10; -fx-border-color: #050af1;" +
                    "-fx-min-width: 120; -fx-max-width: 120;" +
                    "-fx-min-height: 40; -fx-max-height: 40;" +
                    "-fx-background-radius: 10");
            label1.setAlignment(Pos.CENTER);
            Circle green = new Circle(10);
            green.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource("/Images/Icons/green.png").toString())));
            Circle red = new Circle(10);
            red.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource("/Images/Icons/red.png").toString())));
            green.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        accept(friendRequest);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            red.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        reject(friendRequest);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            HBox hBox = new HBox(label1, green, red);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            leftVBox.getChildren().add(hBox);
        }
        Label label1 = new Label("My Friends");
        label1.setStyle(style);
        mainVBox.getChildren().add(label1);
        for (User myFriend : Data.getCurrentUser().getMyFriends()) {
            Label label2 = new Label(myFriend.getUsername());
            label2.setStyle("-fx-font-size: 18; -fx-font-family: 'Times New Roman';" +
                    "-fx-background-color: #5ec2e7; -fx-text-fill: black;" +
                    "-fx-border-radius: 10; -fx-border-color: #050af1;" +
                    "-fx-min-width: 120; -fx-max-width: 120;" +
                    "-fx-min-height: 40; -fx-max-height: 40;" +
                    "-fx-background-radius: 10");
            label2.setAlignment(Pos.CENTER);
            Circle blue = new Circle(4);
            blue.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource("/Images/Icons/yellow.png").toString())));
            StackPane stackPane = new StackPane(label2, blue);
            blue.setTranslateX(55);
            blue.setTranslateY(15);
            if(!isOnline(myFriend)) blue.setVisible(false);
            mainVBox.getChildren().add(stackPane);
        }
        Button back = new Button("Back");
        String style1 = "";
        back.setStyle(style1);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new MainMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        TextField textField = new TextField();
        textField.setPromptText("search");
        textField.setStyle("-fx-pref-width: 300;\n" +
                "    -fx-pref-height: 30;\n" +
                "    -fx-font-size: 15px;\n" +
                "    -fx-background-radius: 10;\n" +
                "    -fx-border-color: #a62bb9;\n" +
                "    -fx-border-radius: 10;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-padding: 6, 4, 4, 4;");
        Circle circle = new Circle(80);
        circle.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource(Data.getCurrentUser().getAvatar()).toString())));
        circle.setVisible(false);
        Circle search = new Circle(20);
        search.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Icons/search.png").toString())));
        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                search(textField.getText(), circle);
            }
        });
        HBox hBox = new HBox(textField, search);
        hBox.setSpacing(10);
        Button send = new Button("send request");
        send.setStyle(style1);
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    sending();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        rightVBox.getChildren().addAll(back, hBox, send, circle);
        pane.getChildren().addAll(leftVBox, mainVBox, rightVBox);
    }

    private boolean isOnline(User myFriend) {
        return Data.getCurrentUser().isOnline(myFriend);
    }

    private void search(String text, Circle circle) {
        circle.setVisible(false);
        searched = "";
        if(text == null || text.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid username");
            alert.setHeaderText("not found");
            alert.showAndWait();
        }
        else if(Data.getUserByName(text) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("username not found");
            alert.setHeaderText("not found");
            alert.showAndWait();
        }
        else if(text.equals(Data.getCurrentUser().getUsername())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("why are you searching yourself?");
            alert.setHeaderText("not found");
            alert.showAndWait();
        }
        else{
            searched = text;
            circle.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource(Data.getUserByName(text).getAvatar()).toString())));
            circle.setVisible(true);
        }
    }

    private void sending() throws IOException {
        if(Objects.equals(searched, "")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("you must search a user first");
            alert.setHeaderText("not found");
            alert.showAndWait();
        }
        else if(Data.getCurrentUser().getMyFriends().contains(Data.getUserByName(searched))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("you are already friend with this one");
            alert.setHeaderText("send request failed");
            alert.showAndWait();
        }
        else send(Data.getUserByName(searched));
    }

    private void send(User user) throws IOException {
        System.out.println("send");
        FriendRequest friendRequest = new FriendRequest(Data.getCurrentUser(), user);
        Data.getCurrentUser().getFriendRequestsSentByMe().add(friendRequest);
        user.getFriendRequestsReceivedByMe().add(friendRequest);
        Data.getCurrentUser().sendRequestCommand(friendRequest);
    }

    private void reject(FriendRequest friendRequest) throws IOException {
        System.out.println("reject");
        Data.getCurrentUser().getFriendRequestsReceivedByMe().remove(friendRequest);
        friendRequest.getSender().getFriendRequestsSentByMe().remove(friendRequest);
        Data.getCurrentUser().rejectRequestCommand(friendRequest);
        setThePane();
    }

    private void accept(FriendRequest friendRequest) throws IOException {
        System.out.println("accept");
        friendRequest.setAccepted(true);
        Data.getCurrentUser().getMyFriends().add(friendRequest.getSender());
        friendRequest.getSender().getMyFriends().add(Data.getCurrentUser());
        Data.getCurrentUser().acceptRequestCommand(friendRequest);
        setThePane();
    }
}
