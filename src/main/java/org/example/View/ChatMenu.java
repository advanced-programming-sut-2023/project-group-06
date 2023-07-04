package org.example.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Model.*;
import org.example.View.GameMenus.TradeMenus.MakeTradeMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ChatMenu extends Application {
    private static Stage stage;
    public Button addPrivateChat;
    public TextField usernameTextField;
    public Label error;
    public Button done;
    public VBox leftVBox;
    public VBox rightVBox;
    public Button createRoom;
    public TextField roomNameTextField;
    public Label roomError;
    public Button done1;
    private Scene scene;
    private static BorderPane borderPane;
    public VBox mainVBox;
    public Button backButton;
    private static ChatRoom selectedChat;
    private TextField mainTextField = new TextField();
    private Circle mainSend;
    private Timeline timeline;
    private ArrayList<Message> lastSixMessages = new ArrayList<>();
    private Timeline chatsTimeline;
    private ArrayList<ChatRoom> chatRooms = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        chatRooms.addAll(Data.getCurrentUser().getChats());
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
        setTheMainVBox();
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            try {
                Data.getCurrentUser().sendToServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            boolean areEqual = true;
            if(Data.getCurrentUser().getChats().size() != chatRooms.size()) areEqual = false;
            if(areEqual) {
                for (int i = 0; i < chatRooms.size(); i++) {
                    if (!chatRooms.get(i).equals(Data.getCurrentUser().getChats().get(i))) {
                        areEqual = false;
                        break;
                    }
                }
            }
            if(!areEqual){
                chatRooms.clear();
                chatRooms.addAll(Data.getCurrentUser().getChats());
                setTheMainVBox();
            }
        }));
        timeline1.setCycleCount(-1);
        this.chatsTimeline = timeline1;
        timeline1.play();
    }

    private void setTheMainVBox(){
        for (int i = mainVBox.getChildren().size() - 1; i >= 0; i--) {
            mainVBox.getChildren().remove(i);
        }
        for(ChatRoom chatRoom : Data.getCurrentUser().getChats()){
            if(chatRoom == Data.getPublicRoom()) {
                Button publicChat = new Button("Public");
                publicChat.setStyle(" -fx-background-color: #dff168; -fx-text-fill: black");
                publicChat.setTextFill(Color.BLACK);
                mainVBox.getChildren().add(publicChat);
                publicChat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedChat = Data.getPublicRoom();
                        /*enterTheChat();*/
                        try {
                            startTheTimeLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                continue;
            }
            String name = chatRoom.getChatType() == ChatType.ROOM ? chatRoom.getName() :
                    chatRoom.getUsers().get(0) == Data.getCurrentUser() ? chatRoom.getUsers().get(1).getUsername() :
                            chatRoom.getUsers().get(0).getUsername();
            Button chat = new Button(name);
            String color = chatRoom.getChatType() == ChatType.PRIVATE ? "#f10ccc" : "#0cf1c7";
            chat.setStyle("-fx-background-color: " + color);
            chat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedChat = chatRoom;
                    /*enterTheChat();*/
                    try {
                        startTheTimeLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainVBox.getChildren().add(chat);
        }
    }

    private void startTheTimeLine() throws IOException {
        Data.getCurrentUser().sendToServer();
        Data.getCurrentUser().sendToServer();
        setTopHBox();
        setTheBottomHBox();
        /*lastSixMessages = selectedChat.lastSix();*/
        enterTheChat();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            try {
                Data.getCurrentUser().sendToServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("sss  " + selectedChat.lastSix() + "   " + lastSixMessages);
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
        for (int i = borderPane.getChildren().size() - 1; i >= 0; i--) {
            if(isValid(i)) borderPane.getChildren().remove(i);
        }
        borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/Game/chatBackground.jpg").toExternalForm()))
                , null, null)));
        mainVBox.setVisible(false);
        leftVBox.setVisible(false);
        rightVBox.setVisible(false);
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
        borderPane.getChildren().add(vBox);
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
        borderPane.getChildren().add(sending);
    }

    private void setTopHBox(){
        HBox hBox = new HBox();
        hBox.setSpacing(60);
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
        if(selectedChat.getChatType() == ChatType.ROOM){
            Button addUsers = new Button("+ add member");
            TextField adding = new TextField();
            adding.setPromptText("username");
            Button add = new Button("add");
            adding.setVisible(false);
            add.setVisible(false);
            addUsers.setStyle(style3);
            adding.setStyle(style4);
            add.setStyle(style);
            addUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    adding.setVisible(true);
                    add.setVisible(true);
                }
            });
            add.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        addingMember(adding.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            hBox.getChildren().addAll(addUsers, adding, add);
        }
        borderPane.getChildren().add(hBox);
    }

    private void addingMember(String username) throws IOException {
        if(Data.getUserByName(username) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid username");
            alert.setHeaderText("Add Member failed");
            alert.showAndWait();
        }
        else if(selectedChat.hasSomeOne(username)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("this user is already in the room");
            alert.setHeaderText("Add Member Failed");
            alert.showAndWait();
        }
        else{
            addMember(username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("new member added");
            alert.setHeaderText("Add Member Successful");
            alert.showAndWait();
        }
    }

    private void addMember(String username) throws IOException {
        selectedChat.getUsers().add(Data.getUserByName(username));
        Data.getUserByName(username).getChats().add(selectedChat);
        System.out.println("add member");
        Data.getCurrentUser().addToGroup(selectedChat);
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

    private void newBack() throws Exception {
        timeline.stop();
        new MainMenu().start(stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(stage);
    }

    public void addPrivateChat(MouseEvent mouseEvent) {
        usernameTextField.setVisible(true);
        done.setVisible(true);
    }

    public void createPrivateChat(MouseEvent mouseEvent) throws IOException {
        String name = usernameTextField.getText();
        if(Data.getUserByName(name) == null
                || Objects.equals(name, Data.getCurrentUser().getUsername())){
            error.setVisible(true);
            error.setText("Invalid username");
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
            error.setText("successful");
            error.setTextFill(Color.GREEN);
            error.setVisible(true);
            Button chat = new Button(name);
            String color = "#f10ccc";
            chat.setStyle(" -fx-background-color: " + color);
            chat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedChat = chatRoom;
                    /*enterTheChat();*/
                    try {
                        startTheTimeLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainVBox.getChildren().add(chat);
        }
    }

    public void createRoom(MouseEvent mouseEvent) throws IOException {
        String name = roomNameTextField.getText();
        if(!name.matches("\\w+")){
            roomError.setVisible(true);
            roomError.setTextFill(Color.RED);
            roomError.setText("invalid name");
        }
        else if(Data.getCurrentUser().haveRoom(name)){
            roomError.setVisible(true);
            roomError.setText("You already have a room with this name");
            roomError.setTextFill(Color.RED);
        }
        else {
            ArrayList<User> users = new ArrayList<>();
            users.add(Data.getCurrentUser());
            ChatRoom chatRoom = new ChatRoom(users, name);/////////////?????????????
            roomError.setText("successful");
            roomError.setTextFill(Color.GREEN);
            roomError.setVisible(true);
            Button chat = new Button(name);
            String color = "#0cf1c7";
            chat.setStyle("-fx-text-fill: Black; -fx-background-color: " + color);
            chat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedChat = chatRoom;
                    /*enterTheChat();*/
                    try {
                        startTheTimeLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainVBox.getChildren().add(chat);
        }
    }

    public void addRoom(MouseEvent mouseEvent) {
        roomNameTextField.setVisible(true);
        done1.setVisible(true);
    }

    public void enterFriendsMenu() throws Exception {
        new FriendMenu().start(stage);
    }

    public boolean isValid(int i){
        return borderPane.getChildren().get(i).getClass() == VBox.class;
    }

    public void enterLobby(MouseEvent mouseEvent) throws Exception {
        new Lobby().start(stage);
    }
}