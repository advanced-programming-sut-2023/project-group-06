package org.example.View;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Model.*;
import org.example.View.GameMenus.TradeMenus.MakeTradeMenu;

import java.util.ArrayList;
import java.util.Objects;

public class ChatMenu extends Application {
    private static Stage stage;
    public Button addPrivateChat;
    public TextField usernameTextField;
    public Label error;
    public Button done;
    public VBox leftVBox;
    private Scene scene;
    private static BorderPane borderPane;
    public VBox mainVBox;
    public Button backButton;
    private static ChatRoom selectedChat;

    @Override
    public void start(Stage stage) throws Exception {
        ChatMenu.stage = stage;
        borderPane = FXMLLoader.load(ChatMenu.class.getResource("/FXML/ChatMenu.fxml"));
        ScrollPane scrollPane = new ScrollPane(borderPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        borderPane.requestFocus();
        scene = new Scene(scrollPane);
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    public void initialize(){
        String style = "";
        Button publicChat = new Button("Public");
        publicChat.setStyle(style + " -fx-background-color: #dff168");
        mainVBox.getChildren().add(publicChat);
        publicChat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                selectedChat = publicChat;
                enterTheChat();
            }
        });
        for(ChatRoom chatRoom : Data.getCurrentUser().getChats()){
            String name = chatRoom.getChatType() == ChatType.ROOM ? chatRoom.getName() :
                    chatRoom.getUsers().get(0) == Data.getCurrentUser() ? chatRoom.getUsers().get(1).getUsername() :
                    chatRoom.getUsers().get(0).getUsername();
            Button chat = new Button(name);
            String color = chatRoom.getChatType() == ChatType.PRIVATE ? "#f10ccc" : "#0cf1c7";
            chat.setStyle(style + " -fx-background-color: " + color);
            chat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedChat = chatRoom;
                    enterTheChat();
                }
            });
            mainVBox.getChildren().add(chat);
        }
    }

    private void enterTheChat() {
        borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/Game/chatBackground.jpg").toExternalForm()))
                , null, null)));
        mainVBox.setVisible(false);
        leftVBox.setVisible(false);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        HBox emptyHBox = new HBox();
        emptyHBox.setStyle("-fx-min-height: 20");
        Button back = new Button("Back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    newBack();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        String style = "";
//        back.setStyle(style);
        hBox.getChildren().addAll(back);
        vBox.getChildren().addAll(hBox, emptyHBox);
        vBox.setSpacing(15);
        Message message3 = new Message(Data.getUserByName("mobin2"), "Hi How Are You Today!", "12:80", selectedChat);
        Message message1 = new Message(Data.getUserByName("mobin30"), "Hi How Are You Today!", "12:80", selectedChat);
        Message message2 = new Message(Data.getUserByName("mobin12"), "Salam?", "12:80", selectedChat);
        Message message4 = new Message(Data.getUserByName("mobin13"), "Hi How Are You Today! Hi How Are You Today?", "12:80", selectedChat);
        Message message5 = new Message(Data.getUserByName("mobin14"), "Hi How Are You Today! Hi How Are You Today? I'm Really Good But I Hate Java", "12:80", selectedChat);
        Message message6 = new Message(Data.getUserByName("mobin14"), "Hi How Are You Today! Hi How Are You Today?", "12:80", selectedChat);
        Message message7 = new Message(Data.getUserByName("mobin13"), "Hi How Are You Today! Hi How Are You Today?", "12:80", selectedChat);
        selectedChat.getMessages().add(message3);
        selectedChat.getMessages().add(message1);
        selectedChat.getMessages().add(message2);
        selectedChat.getMessages().add(message4);
        selectedChat.getMessages().add(message5);
        int num = Math.min(selectedChat.getMessages().size(), 5);
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
                    new Image(ProfileMenu.class.getResource("/Images/Game/send4.png").toString())));
            delete.setFill(new ImagePattern(
                    new Image(ProfileMenu.class.getResource("/Images/Game/send.jpg").toString())));
            edit.setVisible(false);
            delete.setVisible(false);
            edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    edit(message);
                }
            });
            delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    delete(message);
                }
            });
            HBox messageHBox = new HBox(empty, messageLabel, edit, delete);
            messageHBox.setMinWidth(1000);
            if(message.getOwner() == Data.getCurrentUser()){
                messageHBox.setAlignment(Pos.CENTER_RIGHT);
            }
            else {
                empty.setText("    " + message.getOwner().getUsername() + ": ");
                empty.setVisible(true);
                Text text = new Text("w    " + message.getOwner().getUsername() + ": ");
                double width = text.getBoundsInLocal().getWidth() + 40;
                System.out.println(width);
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
        TextField textField = new TextField();
        textField.setPromptText("enter the message");
        textField.setStyle("-fx-min-width: 1000; -fx-max-width: 1000; -fx-background-color: #e3cafc;" +
                "-fx-min-height: 38; -fx-max-height: 38");
        Circle circle = new Circle(18);
        circle.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource("/Images/Game/send4.png").toString())));
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText() != null && !Objects.equals(textField.getText(), "")){
                    Message message = new Message(Data.getCurrentUser(), textField.getText(), "00:00", selectedChat);
                    sendMessage(message);
                    textField.setText("");
                }
            }
        });
        HBox sending = new HBox(textField, circle);
        sending.setSpacing(10);
        sending.setLayoutX(150);
        sending.setLayoutY(650);
        borderPane.getChildren().add(vBox);
        borderPane.getChildren().add(sending);
    }

    private void delete(Message message) {
        System.out.println("delete");
    }

    private void edit(Message message) {
        System.out.println("edit");
    }

    private void sendMessage(Message message) {
        System.out.println("hiiiiii");
    }

    private void newBack() throws Exception {
        new MainMenu().start(stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(stage);
    }

    public void addPrivateChat(MouseEvent mouseEvent) {
        usernameTextField.setVisible(true);
        done.setVisible(true);
    }

    public void createPrivateChat(MouseEvent mouseEvent) {
        String name = usernameTextField.getText();
        if(Data.getUserByName(name) == null
                || Objects.equals(name, Data.getCurrentUser().getUsername())){
            error.setVisible(true);
            error.setTextFill(Color.RED);
        }
        else if(Data.getCurrentUser().hasPrivateChatWith(name)){
            error.setVisible(true);
            error.setText("You already have a chat with this one");
            error.setTextFill(Color.RED);
        }
        else {
            ArrayList<User> them = new ArrayList<>();
            them.add(Data.getCurrentUser());
            them.add(Data.getUserByName(name));
            ChatRoom chatRoom = new ChatRoom(them, ChatType.PRIVATE);
            Data.getCurrentUser().getChats().add(chatRoom);
            error.setText("successful");
            error.setTextFill(Color.GREEN);
            error.setVisible(true);
            Data.getUserByName(name).getChats().add(chatRoom);
            Button chat = new Button(name);
            String color = "#f10ccc";
            chat.setStyle(" -fx-background-color: " + color);
            chat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedChat = chatRoom;
                    enterTheChat();
                }
            });
            mainVBox.getChildren().add(chat);
        }
    }
}
