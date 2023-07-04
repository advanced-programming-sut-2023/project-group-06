package org.example.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameRoomMenu extends Application {
    public static WaitingGame waitingGame;
    private static Stage stage;
    private static Pane pane;
    private Scene scene;
    private static ChatRoom selectedChat;
    private TextField mainTextField = new TextField();
    private Circle mainSend;
    private Timeline timeline;
    private ArrayList<Message> lastSixMessages = new ArrayList<>();
    private Timeline chatsTimeline;

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

        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    /*public GameRoomMenu(WaitingGame waitingGame) {
        this.waitingGame = waitingGame;
        selectedChat = waitingGame.getChatRoom();
    }*/

    public void initialize(){
        System.out.println("kkk " + selectedChat);
        selectedChat = waitingGame.getChatRoom();
        System.out.println("lll " + selectedChat);
    }

    private void startTheTimeLine() throws IOException {
        Data.getCurrentUser().sendToServer();
        Data.getCurrentUser().sendToServer();
        setTopHBox();
        setTheBottomHBox();
        enterTheChat();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            try {
                Data.getCurrentUser().sendToServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            boolean areEqual = true;
            if(selectedChat.lastSix().size() != lastSixMessages.size()) areEqual = false;
            if(areEqual) {
                for (int i = 0; i < lastSixMessages.size(); i++) {
                    if (!lastSixMessages.get(i).equals(selectedChat.lastSix().get(i))) {
                        areEqual = false;
                        break;
                    }
                }
            }
            if(!areEqual){
                lastSixMessages.clear();
                lastSixMessages.addAll(selectedChat.lastSix());
                try {
                    enterTheChat();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
        timeline.setCycleCount(-1);
        this.timeline = timeline;
        timeline.play();
    }

    private void enterTheChat() throws IOException {
        for (int i = pane.getChildren().size() - 1; i >= 0; i--) {
            if(isValid(i)) pane.getChildren().remove(i);
        }
        pane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/Game/chatBackground.jpg").toExternalForm()))
                , null, null)));
//        if(!stage.isFullScreen()) stage.setFullScreen(true);
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        int num = Math.min(selectedChat.getMessages().size(), 6);
        String style2 = "-fx-pref-width: 300;\n" +
                "    -fx-pref-height: 35;\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-background-radius: 10;\n" +
                "    -fx-border-radius: 10;\n" +
                "    -fx-border-color: #0f2d94;\n" +
                "    -fx-background-color: #eefffa;\n" +
                "    -fx-padding: 6, 5, 5, 5;";
        for(int i = num; i >= 1; i--){
            Message message = selectedChat.getMessages().get(selectedChat.getMessages().size() - i);
            String content = message.getContent();
            Label messageLabel = new Label(content);
            messageLabel.setStyle(style2);
            messageLabel.setWrapText(true);
            Text text1 = new Text(content);
            int lines = (int)(text1.getBoundsInLocal().getWidth() / 200) + 1;
            messageLabel.setMinHeight(lines * 42);
            messageLabel.setMaxHeight(lines * 42);
            Label empty = new Label(/*message.getOwner().getUsername() + ": "*/);
            empty.setStyle("-fx-min-width: 50; -fx-max-width: 50");
            empty.setVisible(false);
            Circle edit = new Circle(20);
            Circle delete = new Circle(20);
            edit.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource("/Images/Game/edit2.png").toString())));
            delete.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource("/Images/Game/delete1.png").toString())));
            edit.setVisible(false);
            delete.setVisible(false);
            edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mainTextField.setText(message.getContent());
                    mainSend.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                edit(message);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            });
            delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        delete(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            HBox messageHBox = new HBox(empty, messageLabel, edit, delete);
            messageHBox.setMinWidth(1000);
            messageHBox.setSpacing(6);
            if(message.getOwner() == Data.getCurrentUser()){
                messageHBox.setAlignment(Pos.CENTER_RIGHT);
            }
            else {
                empty.setText("    " + message.getOwner().getUsername() + ": ");
                empty.setVisible(true);
                Text text = new Text("w    " + message.getOwner().getUsername() + ": ");
                double width = text.getBoundsInLocal().getWidth() + 40;
                empty.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;" +
                        " -fx-max-width: " + width + "; -fx-min-width: " + width);
                messageHBox.setAlignment(Pos.CENTER_LEFT);
            }
            messageHBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    edit.setVisible(true);
                    delete.setVisible(true);
                }
            });
            messageHBox.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    edit.setVisible(false);
                    delete.setVisible(false);
                }
            });
            vBox.getChildren().add(messageHBox);
        }
        vBox.setLayoutX(0);
        vBox.setLayoutY(100);
        pane.getChildren().add(vBox);
    }

    private void setTheBottomHBox(){
        TextField textField = new TextField();
        mainTextField = textField;
        textField.setPromptText("enter the message");
        textField.setStyle("-fx-min-width: 1000; -fx-max-width: 1000; -fx-background-color: #e3cafc;" +
                "-fx-min-height: 38; -fx-max-height: 38");
        Circle circle = new Circle(18);
        mainSend = circle;
        circle.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/send4.png").toString())));
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText() != null && !Objects.equals(textField.getText(), "")){
                    Message message = new Message(Data.getCurrentUser(), textField.getText(), "00:00", selectedChat);
                    try {
                        sendMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    textField.setText("");
                }
            }
        });
        HBox sending = new HBox(textField, circle);
        sending.setSpacing(10);
        sending.setLayoutX(150);
        sending.setLayoutY(650);
        pane.getChildren().add(sending);
    }

    private void setTopHBox(){
        HBox hBox = new HBox();
        hBox.setSpacing(60);
        HBox emptyHBox = new HBox();
        emptyHBox.setStyle("-fx-min-height: 20");
        Button back = new Button("Leave");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Leave();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        String style = "-fx-min-width: 100;\n" +
                "    -fx-max-width: 100;\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-background-radius: 20;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-padding: 4 30 4 30;\n" +
                "    -fx-text-fill: #e7fce2;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-background-color: #0f2d94;";
        String style3 = "-fx-min-width: 200;\n" +
                "    -fx-max-width: 200;\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-background-radius: 20;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-padding: 4 30 4 30;\n" +
                "    -fx-text-fill: #e7fce2;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-background-color: #0f2d94;";
        String style4 = "-fx-min-width: 300; -fx-min-height: 40;" +
                "    -fx-max-width: 300; -fx-max-height: 40;" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-background-radius: 20;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-padding: 4 30 4 30;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-background-color: #ffe8fb;";
        back.setStyle(style);
        hBox.getChildren().addAll(back);
        if(Data.getCurrentUser() == waitingGame.getAdmin()){
            Button start = new Button("start");
            start.setStyle(style);
            start.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    startTheGame();
                }
            });
        }
        pane.getChildren().add(hBox);
    }

    private void startTheGame() {

    }

    private void Leave() {

    }

    private void delete(Message message) throws IOException {
        System.out.println("delete");
        Data.getCurrentUser().deleteMessageCommand(message);
    }

    private void edit(Message message) throws IOException {
        System.out.println("edit");
        String text = mainTextField.getText();
        mainTextField.setText("");
        message.setContent(text);
        editMessage(message);
        mainSend.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mainTextField.getText() != null && !Objects.equals(mainTextField.getText(), "")){
                    Message message = new Message(Data.getCurrentUser(), mainTextField.getText(), "00:00", selectedChat);
                    try {
                        sendMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mainTextField.setText("");
                }
            }
        });
    }

    private void editMessage(Message message) throws IOException {
        Data.getCurrentUser().editMessage(message);
    }

    private void sendMessage(Message message) throws IOException {
        System.out.println(lastSixMessages);
        System.out.println(selectedChat.lastSix());
        System.out.println("send");
        Data.getCurrentUser().sendMessageCommand(message);
        /*enterTheChat();*/
    }

    public boolean isValid(int i){
        return pane.getChildren().get(i).getClass() == VBox.class;
    }
}
