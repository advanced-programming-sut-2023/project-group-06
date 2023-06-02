package org.example.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Controller.LoginController;
import org.example.Model.Data;

import java.util.regex.Matcher;

public class LoginMenu extends Application {
    public TextField username;
    public StackPane passwordStackPane;
    public TextField passwordTextField;
    public PasswordField passwordPasswordField;
    public ToggleButton passwordToggle;
    public Hyperlink forgotPassword;
    public Label answerTheQuestion;
    public Label question;
    public TextField answer;
    public Button login;
    public Button back;
    public Button changePassword;
    public Label incorrectAnswer;
    public Label loginResult;
    public RadioButton stayLoggedIn;
    LoginController loginController;
    private BorderPane borderPane;
    private Scene scene;
    private static Stage stage;
    private boolean didForget = false;

    private static final String[] questions = {"What is my father's name?", "What is my first pet's name?", "What is my mother's last name?"};
//    public MenuType run(Scanner scanner) {
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.LOGIN_USER).find()) {
//                Matcher matcher = Commands.getMatcher(command,Commands.LOGIN_USER); matcher.find();
//                String username = matcher.group("username");
//                if (LoginController.setLastLoginAttempt(username, (System.currentTimeMillis() / 1000L))) {
//                    Response response = LoginController.loginUser(Commands.getMatcher(command, Commands.LOGIN_USER));
//                    System.out.println(response.message);
//                    if (response == Response.LOGIN_SUCCESSFUL) {
//                        LoginController.resetLoginAttempts(username);
//                        return MenuType.MAIN_MENU;
//                    }
//                } else {
//                    long lastLoginAttempt = LoginController.getLastLoginAttempt(username);
//                    long currentTime = System.currentTimeMillis() / 1000L;
//                    System.out.printf(Response.TRY_AGAIN_LATER.message,
//                            lastLoginAttempt + LoginController.getNumberOfLoginAttempts(username) * 5 - currentTime);
//                    System.out.println();
//                }
//            } else if (Commands.getMatcher(command, Commands.FORGOT_PASSWORD).find()) {
//                Matcher matcher = Commands.getMatcher(command, Commands.FORGOT_PASSWORD);
//                matcher.find();
//                String username = matcher.group("username");
//                System.out.println(questions[Data.getUserByName(username).getQuestionIndex()]);
//                String questionAnswer = scanner.nextLine();
//                Response response = LoginController.forgotPassword(Commands.getMatcher(command, Commands.FORGOT_PASSWORD), questionAnswer);
//                if (response.equals(Response.PASSWORD_CHANGE)) {
//                    System.out.println("Please enter your password:");
//                    String newPassword = scanner.nextLine();
//                    Response response1 = LoginController.isPasswordStrong(newPassword);
//                    if (response1 != null) {
//                        System.out.println(response1.message);
//                        continue;
//                    }
//                    System.out.println("Please re-enter your password:");
//                    String newPasswordConfirmation = scanner.nextLine();
//                    System.out.println(LoginController.changePasswordSuccessful(username,questionAnswer,newPassword,newPasswordConfirmation).message);
//                } else System.out.println(response.message);
//            } else if (Commands.getMatcher(command,Commands.BACK).find()) {
//                return MenuType.START_MENU;
//            } else {
//                System.out.println(Response.INVALID_COMMAND.message);
//            }
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/LoginMenu.fxml"));
        scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void initialize() {
        passwordToggle.setBackground(new Background(new BackgroundFill(new ImagePattern(
                new Image(SignUpMenu.class.getResource("/Images/eyeHide.png").toExternalForm())), null, null)));
    }

    public void passwordToggleAction(ActionEvent actionEvent) {
        passwordStackPane.getChildren().get(0).toFront();
        passwordStackPane.getChildren().get(0).setVisible(false);
        passwordStackPane.getChildren().get(1).setVisible(true);
        ((TextField) passwordStackPane.getChildren().get(1)).setText(((TextField) passwordStackPane.getChildren().get(0)).getText());
        passwordToggle.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/eye" + ((passwordToggle.isSelected()) ? "Show" : "Hide") + ".png").toExternalForm()))
                , null, null)));
    }

    public void forgotPassword(ActionEvent mouseEvent) {
        String usernameText = username.getText();
        didForget = true;
        answerTheQuestion.setVisible(true);
        if(Data.getUserByName(usernameText) == null){
            answerTheQuestion.setText(Response.INVALID_USERNAME.message);
            answerTheQuestion.setTextFill(Color.RED);
        }
        else{
            answerTheQuestion.setText("answer the question:");
            answerTheQuestion.setTextFill(Color.BLACK);
            question.setVisible(true);
            answer.setVisible(true);
            changePassword.setVisible(true);
            int questionIndex = Data.getUserByName(usernameText).getQuestionIndex();
            question.setText(questions[questionIndex]);
        }
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        String usernameText = username.getText();
        String passwordText =
                passwordTextField.isVisible() ? passwordTextField.getText() : passwordPasswordField.getText();
        String stayLogged = stayLoggedIn.isSelected() ? "stay-logged-in" : null;
        Matcher matcher = Translator.getMatcherByGroups(
                Translator.LOGIN_USER, usernameText, passwordText, stayLogged);

        if (/*LoginController.setLastLoginAttempt(usernameText, (System.currentTimeMillis() / 1000L))*/ true) {
            Response response1 = LoginController.loginUser(matcher);
            loginResult.setVisible(true);
            loginResult.setText(response1.message);
            if (response1 == Response.LOGIN_SUCCESSFUL) {
                loginResult.setTextFill(Color.GREEN);
                LoginController.resetLoginAttempts(usernameText);
                new MainMenu().start(stage);
            }
        } else {
            long lastLoginAttempt = LoginController.getLastLoginAttempt(usernameText);
            long currentTime = System.currentTimeMillis() / 1000L;
            /*System.out.printf(Response.TRY_AGAIN_LATER.message,
                    lastLoginAttempt + LoginController.getNumberOfLoginAttempts(username) * 5 - currentTime);
            System.out.println();*/ // TODO: 2023-05-27
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new SignUpMenu().start(stage);
    }

    public void change(MouseEvent mouseEvent) {
/*        Response response = LoginController.forgotPassword(..., answer.getText());
        if (response.equals(Response.PASSWORD_CHANGE)) {
            String newPassword = scanner.nextLine();
            Response response1 = LoginController.isPasswordStrong(newPassword);
            if (response1 != null) {
                System.out.println(response1.message);
                continue;
            }
            System.out.println("Please re-enter your password:");
            String newPasswordConfirmation = scanner.nextLine();
            System.out.println(LoginController.changePasswordSuccessful(username,questionAnswer,newPassword,newPasswordConfirmation).message);
        } else System.out.println(response.message);*/
    }
}
