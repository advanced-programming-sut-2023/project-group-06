package org.example.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.example.Controller.Controller;
import org.example.Controller.SignUpController;
import org.example.Model.Data;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpMenu extends Application {
    public StackPane passwordStackPane;
    public TextField passwordTextField;
    public PasswordField passwordPasswordField;
    public ToggleButton passwordToggle;
    public StackPane passwordConfirmationStackPane;
    public TextField passwordConfirmationTextField;
    public PasswordField passwordConfirmationPasswordField;
    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    public TextField username;
    public Label usernameError;
    public Button randomPassword;
    public Label passwordError;
    public TextField nickname;
    public TextField email;
    public CheckBox wantSlogan;
    public TextField slogan;
    public ChoiceBox sloganChoiceBox;
    public Button register;
    public Hyperlink haveAccount;
    SignUpController signUpController;

    private static final String[] allRandomSlogans = {"Every man dies. Not every man lives.", "Nothing is impossible. The word itself says I’m possible!",
            "We need much less than we think we need.", "You do not find the happy life. You make it", "I shall have my revenge, in this life or the next",
            "A hero need not speak, When he is gone, the world will speak for him", "They told me I couldn't, that’s why I did.",
            "I am a man of fortune, and I must seek my fortune.", "Keep friends close and enemies guessing.",
            "I don’t need to get a life, I am a gamer I have lots of lives.", "Gamer zone, Be careful", "Eat-Sleep-Play-Repeat", "Life is a game, Play to win",
            "Escape Reality & Play Games.", "Do not disturb, I am Gaming.", "Games the only legal place to kill stupids."};

    public static void main(String[] args) {
        launch(args);
    }

//    public MenuType run(Scanner scanner) {
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.CREATE_USER).find()) {
//                Response response = SignUpController.createUser(Commands.getMatcher(command,Commands.CREATE_USER), scanner);
//                System.out.println(response.message);
//                if (response.equals(Response.PICK_SECURITY_QUESTION)) {
//                    Matcher matcher = Commands.getMatcher(command,Commands.CREATE_USER);
//                    matcher.find();
//                    String username = matcher.group("username");
//                    while (true) {
//                        command = scanner.nextLine();
//                        if (Commands.getMatcher(command,Commands.QUESTION_PICK).find()){
//                            response = SignUpController.securityQuestion(scanner, Commands.getMatcher(command,Commands.QUESTION_PICK),username);
//                            System.out.println(response.message);
//                            if (response.equals(Response.USER_CREATED)) {
//                                return MenuType.LOGIN_MENU;
//                            }
//                            else break;
//                        } else if (Commands.getMatcher(command,Commands.BACK).find()) {
//                            SignUpController.back(Commands.getMatcher(command,Commands.BACK),username);
//                            System.out.println(Response.CREATION_INTERRUPTION.message);
//                            break;
//                        } else {
//                            System.out.println(Response.INVALID_COMMAND.message);
//                        }
//                    }
//                }
//            } else if (Commands.getMatcher(command,Commands.BACK).find()) {
//                return MenuType.START_MENU;
//            } else {
//                System.out.println(Response.INVALID_COMMAND.message);
//            }
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Data.loadData("src/main/java/org/example/Model/Data.json");
        this.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/SignUpMenu.fxml"));
        scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        passwordPasswordField.textProperty().addListener((observable, oldPassword, newPassword) -> {
            Response response;
            if ((response = Controller.isPasswordValid(((TextField) passwordStackPane.getChildren().get(1)).getText())) != Response.PASSWORD_GOOD) {
                passwordError.setText(response.message);
                passwordError.setStyle("-fx-text-fill: RED");
            } else {
                passwordError.setText("");
            }
        });
        passwordTextField.textProperty().addListener((observable, oldPassword, newPassword) -> {
            Response response;
            if ((response = Controller.isPasswordValid(((TextField) passwordStackPane.getChildren().get(1)).getText())) != Response.PASSWORD_GOOD) {
                passwordError.setText(response.message);
                passwordError.setStyle("-fx-text-fill: RED");
            } else {
                passwordError.setText("");
            }
        });
        passwordError.textProperty().addListener((observable -> {
            Response response;
            if ((response = Controller.isPasswordValid(((TextField) passwordStackPane.getChildren().get(1)).getText())) != Response.PASSWORD_GOOD) {
                passwordError.setText(response.message);
                passwordError.setStyle("-fx-text-fill: RED");
            }
        }));
        passwordToggle.setBackground(new Background(new BackgroundFill(new ImagePattern(
                new Image(SignUpMenu.class.getResource("/Images/eyeHide.png").toExternalForm())), null, null)));
        username.textProperty().addListener(((observableValue, oldUsername, newUsername) -> {
            if (!Controller.isUsernameValid(newUsername)) {
                usernameError.setText(Response.INVALID_USERNAME_FORMAT.message);
                usernameError.setStyle("-fx-text-fill: RED");
            } else usernameError.setText("");
        }));
    }

    public static String randomSlogan() {
        String slogan = allRandomSlogans[(int) (Math.random() * 16)];
        System.out.println("Your slogan is: " + slogan);
        return slogan;
    }

    public static String randomPassword(Scanner scanner) {
        String password = SignUpController.passwordGenerator();
        System.out.println("Your random password is: " + password + '\n' +
                "Please re-enter your password here: ");
        String enteredPassword = scanner.nextLine();
        if (!password.equals(enteredPassword)) return null;
        return password;
    }

    public static String getCaptcha(Scanner scanner, String captcha) {
        System.out.println(captcha);
        String input = scanner.nextLine();
        return input;
    }

    public void randomPassword(ActionEvent actionEvent) {
    }

    public void wantSlogan(ActionEvent actionEvent) {
    }

    public void sloganChoiceBoxAction(ActionEvent actionEvent) {
    }

    public void register(ActionEvent actionEvent) {
    }

    public void haveAccount(ActionEvent actionEvent) {
    }

    public void passwordToggleAction(ActionEvent actionEvent) {
        passwordStackPane.getChildren().get(0).toFront();
        passwordStackPane.getChildren().get(0).setVisible(false);
        passwordStackPane.getChildren().get(1).setVisible(true);
        ((TextField) passwordStackPane.getChildren().get(1)).setText(((TextField) passwordStackPane.getChildren().get(0)).getText());
        passwordConfirmationStackPane.getChildren().get(0).toFront();
        passwordConfirmationStackPane.getChildren().get(0).setVisible(false);
        passwordConfirmationStackPane.getChildren().get(1).setVisible(true);
        ((TextField) passwordConfirmationStackPane.getChildren().get(1)).setText(((TextField) passwordConfirmationStackPane.getChildren().get(0)).getText());
        passwordToggle.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/eye" + ((passwordToggle.isSelected()) ? "Show" : "Hide") + ".png").toExternalForm()))
                , null, null)));
    }
}
