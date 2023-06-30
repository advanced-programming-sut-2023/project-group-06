package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Controller.MainController;
import org.example.Controller.ProfileController;
import org.example.Model.Data;
import org.example.View.GameMenus.GameMenu;

public class MainMenu extends Application {
    public VBox MainVbox;
    public Button logout;
    public Button profile;
    public Button startGame;
    /*public ChoiceBox numberOfPlayers;*/
    public Label numberOfPlayersLabel;
    public HBox hbox;
    public HBox hBox1;
    public Label choosePlayers;
    public Button start;
    public Label startError;
    public Text text;
    public Button chat;
    private ChoiceBox<Integer> choiceBox;
    MainController mainController;
    private BorderPane borderPane;
    private Scene scene;
    private static Stage stage;

    public MainMenu() {
    }

//    public MenuType run(Scanner scanner) {
//        while(true){
//            String command  = scanner.nextLine();
//            Matcher matcher;
//            if((matcher = Commands.getMatcher(command, Commands.START_GAME)).find()) {
//                String result = MainController.startGame(matcher).message;
//                System.out.println(result);
//                if(Objects.equals(result, "Game started successfully!"))    return MenuType.GAME_MENU;
//            }
//            else if(Commands.getMatcher(command, Commands.ENTER_PROFILE_MENU).find()) {
//                System.out.println(Response.ENTER_PROFILE_MENU.message);
//                return MenuType.PROFILE_MENU;
//            }
//            else if(Commands.getMatcher(command, Commands.LOGOUT).find()){
//                System.out.println(Response.LOGOUT_SUCCESSFUL.message);
//                return MenuType.LOGIN_MENU;
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        MainMenu.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/MainMenu.fxml"));
        scene = new Scene(borderPane);
        setThePain();
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    public void setThePain(){
        borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/knight.jpg").toExternalForm()))
                , null, null)));
        Circle circle = new Circle(120);
        circle.setFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource(Data.getCurrentUser().getAvatar()).toString())));
        circle.setCenterX(300);
        circle.setCenterY(200);
        borderPane.getChildren().add(circle);
    }

    @FXML
    private void enterChatMenu() throws Exception {
        new ChatMenu().start(stage);
    }

    public void initialize(){
        text.setText("Hello " + Data.getCurrentUser().getUsername());
        text.setFill(Color.rgb(17, 250, 17));
        text.setFont(Font.font("Times New Roman", 40));

        ChoiceBox<Integer> numberOfPlayers = new ChoiceBox<>();
        numberOfPlayers.getItems().addAll(2, 3, 4, 5, 6, 7, 8);
        numberOfPlayers.setValue(2);
        numberOfPlayers.setLayoutX(70);
        numberOfPlayers.setLayoutY(50);
        numberOfPlayers.setOnAction(actionEvent -> {
            Integer selected = numberOfPlayers.getSelectionModel().getSelectedItem();
            setHBox1(hBox1);
        });
        numberOfPlayers.setVisible(false);
        choiceBox = numberOfPlayers;
        hbox.getChildren().add(numberOfPlayers);
        setHBox1(hBox1);
    }

    public void setHBox1(HBox hbox){
        hbox.getChildren().clear();
        int numberOfPlayersValue = choiceBox.getValue();
        for(int i = 0; i < numberOfPlayersValue - 1; i++){
            TextField textField = new TextField();
            textField.setStyle("-fx-background-color: #fdf090; -fx-min-width: 100; -fx-max-width: 100;" +
                    " -fx-border-radius: 10; -fx-border-color: #0f2d94;" +
                    " -fx-min-height: 40; -fx-max-height: 40; -fx-background-radius: 10");
            textField.setPromptText("user " + (i + 1));
            hbox.getChildren().add(textField);
        }
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        if (Data.getCurrentUser() != null) {
            Data.getCurrentUser().inactivateClient();
            Data.getCurrentUser().setClient(null);
        }
        new SignUpMenu().start(stage);
    }

    public void enterProfile(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(stage);
    }

    public void startGame(MouseEvent mouseEvent) {
        numberOfPlayersLabel.setVisible(true);
        choiceBox.setVisible(true);
        choosePlayers.setVisible(true);
        hBox1.setVisible(true);
        start.setVisible(true);
    }

    public void start(MouseEvent mouseEvent) throws Exception {
        int size = hBox1.getChildren().size();
        String user2 =  size >= 1 ? ((TextField) hBox1.getChildren().get(0)).getText() : null;
        String user3 =  size >= 2 ? ((TextField) hBox1.getChildren().get(1)).getText() : null;
        String user4 =  size >= 3 ? ((TextField) hBox1.getChildren().get(2)).getText() : null;
        String user5 =  size >= 4 ? ((TextField) hBox1.getChildren().get(3)).getText() : null;
        String user6 =  size >= 5 ? ((TextField) hBox1.getChildren().get(4)).getText() : null;
        String user7 =  size >= 6 ? ((TextField) hBox1.getChildren().get(5)).getText() : null;
        String user8 =  size >= 7 ? ((TextField) hBox1.getChildren().get(6)).getText() : null;
        Response response = MainController.startGame(Translator.getMatcherByGroups(
                Translator.START_GAME, user2, user3, user4, user5, user6, user7, user8));
        System.out.println(response.message);
        if(response != Response.GAME_STARTED_SUCCESSFULLY){
            startError.setVisible(true);
            startError.setText(response.message);
            startError.setTextFill(Color.RED);
        }
        else{
            new GameMenu().start(stage);
        }
    }
}
