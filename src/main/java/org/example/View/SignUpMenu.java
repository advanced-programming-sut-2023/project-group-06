package org.example.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.Controller.Controller;
import org.example.Controller.LoginController;
import org.example.Controller.SignUpController;

import java.io.IOException;
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
    public VBox mainVbox;
    private Stage stage;
    private Scene scene;
    private static BorderPane borderPane;
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
        this.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/SignUpMenu.fxml"));
        System.out.println(borderPane);
        scene = new Scene(borderPane);
        stage.setScene(scene);
//        stage.setFullScreen(true);
        Screen screen = Screen.getPrimary();
        stage.setWidth(screen.getBounds().getWidth());
        stage.setHeight(screen.getBounds().getHeight());
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
        String defaultString = "---";
        sloganChoiceBox.getItems().add(defaultString);
        sloganChoiceBox.setValue(defaultString);
        sloganChoiceBox.getItems().add("random slogan");
        sloganChoiceBox.setMaxWidth(50);
        sloganChoiceBox.setMinWidth(50);
        sloganChoiceBox.setMaxHeight(20);
        sloganChoiceBox.setConverter(new StringConverter() {
            @Override
            public String toString(Object o) {
                if (!(o instanceof String)) return null;
                String s = (String) o;
                String finalString = "";
                int j = 0;
                for (int i = 0; i < s.length(); i++) {
                    j++;
                    if (s.charAt(i) == ' ' && j >= 20) {
                        finalString += '\n';
                        j = 0;
                    } else {
                        finalString += s.charAt(i);
                    }
                }
                return finalString;
            }

            @Override
            public Object fromString(String s) {
                return null;
            }
        });
        for (String string : allRandomSlogans) {
            sloganChoiceBox.getItems().add(string);
        }
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
        passwordTextField.setText(SignUpController.passwordGenerator());
        passwordPasswordField.setText(SignUpController.passwordGenerator());
    }

    public void wantSlogan(ActionEvent actionEvent) {
        if (wantSlogan.isSelected()) {
            slogan.setVisible(true);
            sloganChoiceBox.setVisible(true);
        } else {
            slogan.setVisible(false);
            sloganChoiceBox.setVisible(false);
        }
    }

    public void sloganChoiceBoxAction(ActionEvent actionEvent) {
        String slogan = (sloganChoiceBox.getValue().toString().equals("random"))
                ? allRandomSlogans[(int) (Math.random() * 16)] : sloganChoiceBox.getValue().toString();
        if (sloganChoiceBox.getValue().equals("---")) return;
        this.slogan.setText(slogan);
    }

    public void register(ActionEvent actionEvent) throws IOException {
        username.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        passwordPasswordField.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        passwordTextField.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        passwordConfirmationTextField.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        passwordConfirmationPasswordField.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        nickname.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        email.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        slogan.setStyle("-fx-border-color: transparent transparent #616161 transparent");
        String username = this.username.getText();
        String password = (passwordToggle.isSelected()) ? passwordTextField.getText() : passwordPasswordField.getText();
        String passwordConfirmation = (passwordToggle.isSelected()) ? passwordConfirmationTextField.getText() :
                passwordConfirmationPasswordField.getText();
        String slogan = (wantSlogan.isSelected()) ? this.slogan.getText() : null;
        String email = this.email.getText();
        String nickname = this.nickname.getText();
        Response response = SignUpController.createUser(Translator.getMatcherByGroups(Translator.CREATE_USER, username, password,
                passwordConfirmation, nickname, email, slogan));
        if (response != Response.PICK_SECURITY_QUESTION) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("Register Failed");
            alert.showAndWait();
            handleError(response);
        } else {
            runQuestionPickPane();
        }
    }

    private void runQuestionPickPane() throws IOException {
//        mainVbox.setVisible(false);
        VBox questionPickMenu = new VBox();
        System.out.println(mainVbox);
        questionPickMenu.setAlignment(Pos.CENTER);
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        String randomCaptcha = Controller.getCaptcha();
        gc.setFill(Color.ORANGE);
        gc.fillRect(0, 0, 50, 50);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 3));
        gc.fillText(randomCaptcha, 5, 6);
        questionPickMenu.getChildren().add(canvas);
        borderPane.setCenter(questionPickMenu);
    }

    public void haveAccount (ActionEvent actionEvent) throws Exception {
        new LoginMenu().start(stage);
    }

    public void passwordToggleAction (ActionEvent actionEvent){
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

    private void handleError (Response response){
        if (response == Response.EMPTY_USERNAME || response == Response.USERNAME_EXISTS || response == Response.INVALID_USERNAME_FORMAT) {
            username.setStyle("-fx-border-color: RED");
        } else if (response == Response.EMPTY_PASSWORD || response == Response.SHORT_PASSWORD || response == Response.PASSWORD_CAPITAL
                || response == Response.PASSWORD_LOWER || response == Response.PASSWORD_NUMBER
                || response == Response.PASSWORD_SYMBOL) {
            passwordTextField.setStyle("-fx-border-color: RED");
            passwordPasswordField.setStyle("-fx-border-color: RED");
        }
        else if(response == Response.EMPTY_CONFIRMATION
                || response == Response.PASSWORD_CONFIRMATION) {
            passwordConfirmationTextField.setStyle("-fx-border-color: RED");
            passwordConfirmationPasswordField.setStyle("-fx-border-color: RED");
        }
        else if(response == Response.EMPTY_EMAIL || response == Response.EMAIL_EXISTS
                || response == Response.INVALID_EMAIL_FORMAT)
            email.setStyle("-fx-border-color: RED");
        else if(response == Response.EMPTY_NICKNAME)
            nickname.setStyle("-fx-border-color: RED");
    }
}
