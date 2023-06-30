package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Model.ChatRoom;
import org.example.Model.ChatType;
import org.example.Model.Data;
import org.example.Model.User;
import org.example.View.GameMenus.TradeMenus.MakeTradeMenu;

import java.util.ArrayList;
import java.util.Objects;

public class ChatMenu extends Application {
    private static Stage stage;
    public Button addPrivateChat;
    public TextField usernameTextField;
    public Label error;
    public Button done;
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
        }
        else if(Data.getCurrentUser().hasPrivateChatWith(name)){
            error.setVisible(true);
            error.setText("You already have a chat with this one");
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
