package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.example.Controller.LoginController;
import org.example.Model.Data;
import org.example.View.MainMenu;
import org.example.View.Response;
import org.example.View.SignUpMenu;
import org.example.View.Translator;

import java.util.regex.Matcher;

public class LoginMenu2 extends Application {
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
    public VBox mainVBox;
    LoginController loginController;
    private static BorderPane borderPane;
    private Scene scene;
    private static Stage stage;
    private boolean didForget = false;

    private static final String[] questions = {"What is my father's name?", "What is my first pet's name?", "What is my mother's last name?"};

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu2.stage = stage;
        borderPane = new BorderPane();
        scene = new Scene(borderPane);
        stage.setScene(scene);
        /*borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(SignUpMenu.class.getResource
                ("/Images/418028.jpg").toExternalForm()))
                , null, null)));*/
        stage.setFullScreen(true);
        stage.show();
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
        Response response = LoginController.forgotPassword(Translator.getMatcherByGroups(
                Translator.FORGOT_PASSWORD, username.getText()), answer.getText());
        if (response.equals(Response.PASSWORD_CHANGE))
            runChangePasswordPane();
        else {
            incorrectAnswer.setVisible(true);
            incorrectAnswer.setTextFill(Color.RED);
            incorrectAnswer.setText(response.message);
        }
    }

    private void runChangePasswordPane() {
        VBox changePassMenu = new VBox();
        changePassMenu.setSpacing(40);
        changePassMenu.setAlignment(Pos.CENTER);
        Button back = new Button("back");
        back.setOnMouseClicked(mouseEvent -> {
            answer.setVisible(false);
            changePassword.setVisible(false);
            answer.setText(null);
            borderPane.setCenter(mainVBox);
        });
        TextField newPass = new TextField();
        TextField newPassConfirm = new TextField();
        newPass.setPromptText("new password");
        newPassConfirm.setPromptText("confirmation");
        Label error = new Label();
        error.setTextFill(Color.RED);
        Button confirm = new Button();
        HBox buttons = new HBox(confirm, back);
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.CENTER);
        changePassMenu.getChildren().addAll(newPass, newPassConfirm, error, buttons);
        borderPane.setCenter(changePassMenu);
    }
}
