package org.example.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Model.Data;
import org.example.Model.FriendRequest;
import org.example.Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FriendMenu extends Application {
    private static Stage stage;
    private static Pane pane;
    private Scene scene;
    private static String searched = "";
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<FriendRequest> requests = new ArrayList<>();
    private ArrayList<Boolean> online = new ArrayList<>();
    private Timeline timeline;
    
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
        startTheTimeLine();
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    private void setOnlinePeople(){
        online.clear();
        for (User friend : friends)
            online.add(Data.getCurrentUser().isOnline(friend));
    }

    public void startTheTimeLine() throws IOException {
        setTheRightVBox();
        Data.getCurrentUser().sendToServer();
        Data.getCurrentUser().sendToServer();
        friends.addAll(Data.getCurrentUser().getMyFriends());
        requests.addAll(Data.getCurrentUser().getFriendRequestsReceivedByMe());
        setOnlinePeople();
        setThePane();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            try {
                Data.getCurrentUser().sendToServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            boolean areRequestsUpdated = true;
            boolean areFriendsUpdated = true;
            boolean areOnlineUpdated = true;
            if(Data.getCurrentUser().getFriendRequestsReceivedByMe().size() != requests.size()) areRequestsUpdated = false;
            if(Data.getCurrentUser().getMyFriends().size() != friends.size()) areFriendsUpdated = false;
            if(online.size() != friends.size()) areOnlineUpdated = false;
            if(areFriendsUpdated && areRequestsUpdated && areOnlineUpdated) {
                for (int i = 0; i < requests.size(); i++) {
                    if (!requests.get(i).equals(Data.getCurrentUser().getFriendRequestsReceivedByMe().get(i))) {
                        areRequestsUpdated = false;
                        break;
                    }
                }
                for(int i = 0; i < friends.size(); i++){
                    if(friends.get(i) != Data.getCurrentUser().getMyFriends().get(i)){
                        areFriendsUpdated = false;
                        break;
                    }
                }
                for(int i = 0; i < online.size(); i++){
                    if(online.get(i) != Data.getCurrentUser().isOnline(friends.get(i))){
                        areOnlineUpdated = false;
                        break;
                    }
                }
            }
            if(!areFriendsUpdated || !areRequestsUpdated || !areOnlineUpdated){
                if(!areFriendsUpdated) {
                    friends.clear();
                    friends.addAll(Data.getCurrentUser().getMyFriends());
                }
                if(!areRequestsUpdated){
                    requests.clear();
                    requests.addAll(Data.getCurrentUser().getFriendRequestsReceivedByMe());
                }
                if(!areOnlineUpdated) setOnlinePeople();
                setThePane();
            }
        }));
        timeline.setCycleCount(-1);
        this.timeline = timeline;
        timeline.play();
    }

    public boolean isValid(int i){
        return ((VBox) pane.getChildren().get(i)).getChildren().get(0).getClass() != Button.class;
    }

    /*public void setLastSeenPane(){
        VBox vBox = new VBox();
        vBox.setLayoutY(100);
        vBox.setLayoutX(500);
        vBox.setSpacing(20);
        for (User myFriend : Data.getCurrentUser().getMyFriends()) {
            long lastSeen = Data.getCurrentUser().getLastSeen(myFriend);
            Text label = new Text(timeConverter(lastSeen));
            label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 10");
            vBox.getChildren().add(label);
        }
        pane.getChildren().add(vBox);
    }*/

    public void setThePane(){
        for (int i = pane.getChildren().size() - 1; i >= 0; i--)
            if(isValid(i)) pane.getChildren().remove(i);
        VBox leftVBox = new VBox();
        VBox mainVBox = new VBox();
        leftVBox.setLayoutY(100);
        leftVBox.setLayoutX(100);
        leftVBox.setSpacing(20);
        mainVBox.setLayoutX(600);
        mainVBox.setLayoutY(100);
        mainVBox.setSpacing(20);

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
        pane.getChildren().addAll(leftVBox, mainVBox);
    }

    private void setTheRightVBox(){
        VBox rightVBox = new VBox();
        rightVBox.setSpacing(20);
        rightVBox.setLayoutY(100);
        rightVBox.setLayoutX(900);
        Button back = new Button("Back");
        String style1 = "";
        back.setStyle(style1);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    timeline.stop();
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
                new Image(Data.getCurrentUser().getAvatar())));
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
        pane.getChildren().add(rightVBox);
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
        else if(!Data.getCurrentUser().getAllClients().contains(text)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("this user has never logged in!");
            alert.setHeaderText("not found");
            alert.showAndWait();
        }
        else{
            searched = text;
            circle.setFill(new ImagePattern(
                    new Image(Data.getCurrentUser().getAvatar())));
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
        /*Data.getCurrentUser().getFriendRequestsSentByMe().add(friendRequest);
        user.getFriendRequestsReceivedByMe().add(friendRequest);*/
        Data.getCurrentUser().sendRequestCommand(friendRequest);
    }

    private void reject(FriendRequest friendRequest) throws IOException {
        System.out.println("reject");
        /*Data.getCurrentUser().getFriendRequestsReceivedByMe().remove(friendRequest);
        friendRequest.getSender().getFriendRequestsSentByMe().remove(friendRequest);*/
        Data.getCurrentUser().rejectRequestCommand(friendRequest);
        setThePane();
    }

    private void accept(FriendRequest friendRequest) throws IOException {
        System.out.println("accept");
        /*friendRequest.setAccepted(true);
        Data.getCurrentUser().getMyFriends().add(friendRequest.getSender());
        friendRequest.getSender().getMyFriends().add(Data.getCurrentUser());*/
        Data.getCurrentUser().acceptRequestCommand(friendRequest);
        setThePane();
    }

    public String timeConverter(long time){
        int seconds = (int)(time % 90000);
        int hour = seconds / 3600;
        int seconds2 = seconds - hour * 3600;
        int minutes = seconds2 / 60;
        String hh = hour < 10 ? "0" + hour : String.valueOf(hour);
        String mm = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        return hh + ":" + mm;
    }
}
