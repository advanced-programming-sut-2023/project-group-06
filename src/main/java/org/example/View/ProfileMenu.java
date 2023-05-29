package org.example.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.Controller.Controller;
import org.example.Controller.ProfileController;

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
    ProfileController profileController;
    private Scene scene;
    private Stage stage;
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
        this.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/ProfileMenu.fxml"));
        scene = new Scene(borderPane);
        /*Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        *//*stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);*/
        /*borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(
                new Image(ProfileMenu.class.getResource(
                        "/images/background1.jpg").toString())), null, null)));*/
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        /*usernameTextField.textProperty().addListener((observable, oldText, newText) -> {
            if (!Controller.isUsernameValid(newText)) {
                usernameError.setText(Response.INVALID_USERNAME_FORMAT.message);
                usernameError.setStyle("-fx-text-fill: RED");
            } else usernameError.setText("");
        });*/
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
    }

    public void saveNickName(MouseEvent mouseEvent) {
    }

    public void savePassword(MouseEvent mouseEvent) {
    }

    public void saveGmail(MouseEvent mouseEvent) {
    }
}
