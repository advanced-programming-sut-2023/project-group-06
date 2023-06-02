package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.Controller.Controller;
import org.example.Controller.ProfileController;
import org.example.Model.Data;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Application {
    public VBox profileMenu;
    public ImageView avatarImage;
    public PasswordField passwordField;
    public TextField nicknameField;
    public TextField gmailField;
    public Label sloganLabel;
    public Label userPassword;
    public Label userNickname;
    public Label UserGmail;
    public Label userUsername;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField nicknameTextField;
    public TextField gmailTextField;
    public Button changeUsername;
    public Button saveUsername;
    public Button changePassword;
    public Button savePassword;
    public Button changeNickName;
    public Button saveNickName;
    public Button changeGmail;
    public Button saveGmail;
    public Label usernameError;
    public Label nicknameError;
    public Label emailError;
    ProfileController profileController;
    private Scene scene;
    private static Stage stage;
    private BorderPane borderPane;

//    public MenuType run(Scanner scanner) {
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.CHANGE_USERNAME).find()) {
//                System.out.println(ProfileController.changeUsername(Commands.getMatcher(command,Commands.CHANGE_USERNAME)).message);
//            } else if (Commands.getMatcher(command,Commands.CHANGE_NICKNAME).find()) {
//                System.out.println(ProfileController.changeNickname(Commands.getMatcher(command,Commands.CHANGE_NICKNAME)).message);
//            } else if (Commands.getMatcher(command,Commands.CHANGE_PASSWORD).find()) {
//                Response response = ProfileController.changePassword(Commands.getMatcher(command,Commands.CHANGE_PASSWORD));
//                System.out.println(response.message);
//                Matcher matcher = Commands.getMatcher(command,Commands.CHANGE_PASSWORD);
//                matcher.find();
//                String newPassword = matcher.group("newPassword");
//                String oldPassword = matcher.group("oldPassword");
//                if (response.equals(Response.RE_ENTER_PASSWORD)) {
//                    String passwordConfirm = scanner.nextLine();
//                    System.out.println(ProfileController.confirmReEnteredPassword(oldPassword,newPassword,passwordConfirm).message);
//                }
//            } else if (Commands.getMatcher(command,Commands.CHANGE_EMAIL).find()) {
//                System.out.println(ProfileController.changeEmail(Commands.getMatcher(command,Commands.CHANGE_EMAIL)).message);
//            } else if (Commands.getMatcher(command,Commands.CHANGE_SLOGAN).find()) {
//                System.out.println(ProfileController.changeSlogan(Commands.getMatcher(command,Commands.CHANGE_SLOGAN)).message);
//            } else if (Commands.getMatcher(command,Commands.REMOVE_SLOGAN).find()) {
//                System.out.println(ProfileController.removeSlogan().message);
//            } else if (Commands.getMatcher(command,Commands.SHOW_HIGH_SCORE).find()) {
//                System.out.println(ProfileController.showHighScore());
//            } else if (Commands.getMatcher(command,Commands.SHOW_RANK).find()) {
//                System.out.println(ProfileController.showRank());
//            } else if (Commands.getMatcher(command,Commands.SHOW_SLOGAN).find()) {
//                System.out.println(ProfileController.showSlogan());
//            } else if (Commands.getMatcher(command,Commands.SHOW_ALL_PROFILE).find()) {
//                System.out.println(ProfileController.showInfo());
//            } else if (Commands.getMatcher(command,Commands.BACK).find()) {
//                System.out.println(Response.ENTER_MAIN_MENU.message);
//                return MenuType.MAIN_MENU;
//            } else {
//                System.out.println(Response.INVALID_COMMAND.message);
//            }
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/ProfileMenu.fxml"));
        scene = new Scene(borderPane);
        //stage.setFullScreen(true);
        setThePain();
        stage.setScene(scene);
        stage.show();
    }

    private void setThePain(){
        VBox vBox = new VBox();
        vBox.setLayoutX(100);
        vBox.setLayoutY(100);
        Circle circle = new Circle(120);
        circle.setFill(new ImagePattern(new Image(ProfileMenu.class.getResource(
                "/Images/background1.jpg").toString()))); // TODO: 2023-05-30
        vBox.getChildren().add(circle);
        borderPane.getChildren().add(vBox);

        VBox vBox1 = new VBox();
        vBox1.setLayoutY(250);
        vBox1.setLayoutX(1050);
        vBox1.setSpacing(5);
        Button button = new Button("ScoreBoard");
        /*"#0caf01" : "#fc2f01";*/
        button.setStyle("-fx-min-width: 100; -fx-max-width: 200; -fx-background-color: #0caf01," +
                " linear-gradient(#fffffe, #efffff)," +
                " linear-gradient(#bea6fd 0%, #a7a9f5 49%, #bee6fd 50%, #a7d9f5 100%);" +
                "-fx-min-height: 50; -fx-max-height: 50;" +
                "-fx-font-size: 15;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    scoreBoard();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button button1 = new Button("back");
        vBox1.getChildren().addAll(button, button1);
        button1.setStyle("-fx-min-width: 100; -fx-max-width: 200; -fx-background-color: #0caf01," +
                " linear-gradient(#fffffe, #efffff)," +
                " linear-gradient(#bea6fd 0%, #a7a9f5 49%, #bee6fd 50%, #a7d9f5 100%);" +
                "-fx-min-height: 50; -fx-max-height: 50;" +
                "-fx-font-size: 15;");
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    back();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        borderPane.getChildren().add(vBox1);
    }

    private void back() throws Exception {
        new MainMenu().start(stage);
    }

    private void scoreBoard() throws Exception {
        new ScoreBoard().start(stage);
    }

    public void initialize(){
        usernameTextField.textProperty().addListener((observable, oldText, newText) -> {
            if (!Controller.isUsernameValid(newText)) {
                usernameError.setText(Response.INVALID_USERNAME_FORMAT.message);
                usernameError.setStyle("-fx-text-fill: RED");
            } else usernameError.setText("");
        });

        userUsername.setText(Data.getCurrentUser().getUsername());
        userNickname.setText(Data.getCurrentUser().getNickname());
        UserGmail.setText(Data.getCurrentUser().getEmail());
    }

    public void changeUsername(MouseEvent mouseEvent) {
        usernameTextField.setVisible(true);
        userUsername.setVisible(false);
        changeUsername.setVisible(false);
        saveUsername.setVisible(true);
    }

    public void changePassword(MouseEvent mouseEvent) {
        passwordTextField.setVisible(true);
        userPassword.setVisible(false);
        changePassword.setVisible(false);
        savePassword.setVisible(true);
    }

    public void changeNickName(MouseEvent mouseEvent) {
        nicknameTextField.setVisible(true);
        userNickname.setVisible(false);
        changeNickName.setVisible(false);
        saveNickName.setVisible(true);
    }

    public void changeGmail(MouseEvent mouseEvent) {
        gmailTextField.setVisible(true);
        UserGmail.setVisible(false);
        changeGmail.setVisible(false);
        saveGmail.setVisible(true);
    }

    public void saveUsername(MouseEvent mouseEvent) {
        Response response = ProfileController.changeUsername(Translator.getMatcherByGroups(
                Translator.CHANGE_USERNAME, usernameTextField.getText()));
        Alert alert;
        if(response != Response.USERNAME_CHANGE){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("Change Info Failed!");
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(response.message);
            alert.setHeaderText("Change username was successful");
            changeUsername.setVisible(true);
            saveUsername.setVisible(false);
            usernameTextField.setVisible(false);
            userUsername.setVisible(true);
            userUsername.setText(usernameTextField.getText());
        }
        alert.showAndWait();
    }

    public void saveNickName(MouseEvent mouseEvent) {
        Response response = ProfileController.changeNickname(Translator.getMatcherByGroups(
                Translator.CHANGE_NICKNAME, nicknameTextField.getText()));
        Alert alert;
        if(response != Response.NICKNAME_CHANGE){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("Change Info Failed!");
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(response.message);
            alert.setHeaderText("Change nickname was successful");
            changeNickName.setVisible(true);
            saveNickName.setVisible(false);
            nicknameTextField.setVisible(false);
            userNickname.setVisible(true);
            userNickname.setText(nicknameTextField.getText());
        }
        alert.showAndWait();
    }

    public void savePassword(MouseEvent mouseEvent) {
    }

    public void saveGmail(MouseEvent mouseEvent) {
        Response response = ProfileController.changeEmail(Translator.getMatcherByGroups(
                Translator.CHANGE_EMAIL, gmailTextField.getText()));
        Alert alert;
        if(response != Response.EMAIL_CHANGE){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("Change Info Failed!");
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(response.message);
            alert.setHeaderText("Change email was successful");
            changeGmail.setVisible(true);
            saveGmail.setVisible(false);
            gmailTextField.setVisible(false);
            UserGmail.setVisible(true);
            UserGmail.setText(nicknameTextField.getText());
        }
        alert.showAndWait();
    }
}
