package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.Controller.Controller;
import org.example.Controller.ProfileController;
import org.example.Model.Data;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Application {
    public VBox profileMenu;
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
    public Button changeNickName;
    public Button saveNickName;
    public Button changeGmail;
    public Button saveGmail;
    public Label usernameError;
    public Label nicknameError;
    public Label emailError;
    public Label userSlogan;
    public TextField sloganTextField;
    public Button changeSlogan;
    public Button saveSlogan;
    private static VBox hiddenVbox;
    private static VBox rightVbox;
    private static Circle avatar;
    ProfileController profileController;
    private Scene scene;
    private static Stage stage;
    private static BorderPane borderPane;

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
        if (!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    private void setThePain(){
        VBox vBox = new VBox();
        vBox.setLayoutX(100);
        vBox.setLayoutY(100);
        Circle circle = new Circle(120);
        circle.setFill(new ImagePattern(Data.getCurrentUser().getAvatar()));
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
        System.out.println(userSlogan);

        vBox1.getChildren().addAll(button, button1);
        borderPane.getChildren().add(vBox1);
        rightVbox = vBox1;
        avatar = circle;
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
        userSlogan.setText(Data.getCurrentUser().getSlogan());
        userSlogan.setWrapText(true);
        int lines = (Data.getCurrentUser().getSlogan().length() / 30) + 1;
        userSlogan.setMinHeight(lines * 42);
        userSlogan.setMaxHeight(lines * 42);
        if(Objects.equals(Data.getCurrentUser().getSlogan(), "")
                || Objects.equals(Data.getCurrentUser().getSlogan(), "null")
                || Data.getCurrentUser().getSlogan() == null)
            userSlogan.setText("slogan is empty");
        TextField oldPassword = new TextField();
        TextField newPassword = new TextField();
        TextField newPasswordConfirmation = new TextField();
        Label error = new Label();
        error.setTextFill(Color.RED);
        oldPassword.setPromptText("old password");
        newPassword.setPromptText("new password");
        newPasswordConfirmation.setPromptText("confirm new password");
        oldPassword.setStyle("-fx-max-width: 300; -fx-min-width: 300; -fx-pref-height: 30; -fx-font-size: 15px;\n" +
                "-fx-background-radius: 10; -fx-border-color: #a62bb9; -fx-border-radius: 10;\n" +
                "-fx-background-color: transparent; -fx-padding: 6, 4, 4, 4;");
        newPassword.setStyle("-fx-min-width: 300 ;-fx-max-width: 300; -fx-pref-height: 30; -fx-font-size: 15px;\n" +
                "-fx-background-radius: 10; -fx-border-color: #a62bb9; -fx-border-radius: 10;\n" +
                "-fx-background-color: transparent; -fx-padding: 6, 4, 4, 4;");
        newPasswordConfirmation.setStyle("-fx-max-width: 300; -fx-min-width: 300; -fx-pref-height: 30; -fx-font-size: 15px;\n" +
                "-fx-background-radius: 10; -fx-border-color: #a62bb9; -fx-border-radius: 10;\n" +
                "-fx-background-color: transparent; -fx-padding: 6, 4, 4, 4;");
        Button confirm = new Button("confirm");
        Button back = new Button("back");
        back.setStyle("-fx-min-width: 100; -fx-max-width: 200; -fx-background-color: #0caf01," +
                " linear-gradient(#fffffe, #efffff)," +
                " linear-gradient(#bea6fd 0%, #a7a9f5 49%, #bee6fd 50%, #a7d9f5 100%);" +
                "-fx-min-height: 30; -fx-max-height: 30;" +
                "-fx-font-size: 15;");
        confirm.setStyle("-fx-min-width: 100; -fx-max-width: 200; -fx-background-color: #0caf01," +
                " linear-gradient(#fffffe, #efffff)," +
                " linear-gradient(#bea6fd 0%, #a7a9f5 49%, #bee6fd 50%, #a7d9f5 100%);" +
                "-fx-min-height: 30; -fx-max-height: 30;" +
                "-fx-font-size: 15;");
        HBox hBox = new HBox(confirm, back);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(30);
        VBox vBox = new VBox(oldPassword, newPassword, newPasswordConfirmation, error, hBox);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(670);
        vBox.setLayoutY(340);

        newPassword.textProperty().addListener((observable, oldText, newText) -> {
            Response response;
            if ((response = Controller.isPasswordValid((newPassword).getText())) != Response.PASSWORD_GOOD) {
                error.setText(response.message);
                Text text = new Text(response.message);
                error.setMinWidth(text.getBoundsInLocal().getWidth());
            }
            else error.setText("");
        });

        hiddenVbox = vBox;
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changePasswordConfirm(vBox);
            }
        });

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                avatar.setVisible(true);
                rightVbox.setVisible(true);
                profileMenu.setVisible(true);
                borderPane.getChildren().remove(hiddenVbox);
            }
        });
    }

    private void changePasswordConfirm(VBox vBox) {
        Response response = ProfileController.changePassword(Translator.getMatcherByGroups(
                Translator.CHANGE_PASSWORD, ((TextField) vBox.getChildren().get(0)).getText(),
                ((TextField) vBox.getChildren().get(1)).getText()));
        if(response != Response.RE_ENTER_PASSWORD){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("Change password failed");
            alert.showAndWait();
        }
        else{
            response = ProfileController.confirmReEnteredPassword(((TextField) vBox.getChildren().get(0)).getText(),
                    ((TextField) vBox.getChildren().get(1)).getText(), ((TextField) vBox.getChildren().get(2)).getText());
            Alert alert;
            if(response != Response.PASSWORD_CHANGE){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(response.message);
                alert.setHeaderText("Change password failed");
            }
            else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText(response.message);
                alert.setHeaderText("Change password successful");
            }
            alert.showAndWait();
        }
    }

    public void changeUsername(MouseEvent mouseEvent) {
        usernameTextField.setVisible(true);
        userUsername.setVisible(false);
        changeUsername.setVisible(false);
        saveUsername.setVisible(true);
    }

    public void changePassword(MouseEvent mouseEvent) {
        avatar.setVisible(false);
        rightVbox.setVisible(false);
        profileMenu.setVisible(false);
        borderPane.getChildren().add(hiddenVbox);
        ((TextField) hiddenVbox.getChildren().get(0)).setText("");
        ((TextField) hiddenVbox.getChildren().get(1)).setText("");
        ((TextField) hiddenVbox.getChildren().get(2)).setText("");
        ((Label) hiddenVbox.getChildren().get(3)).setText("");
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
            UserGmail.setText(gmailTextField.getText());
        }
        alert.showAndWait();
    }

    public void changeSlogan(MouseEvent mouseEvent) {
        sloganTextField.setVisible(true);
        userSlogan.setVisible(false);
        changeSlogan.setVisible(false);
        saveSlogan.setVisible(true);
    }

    public void saveSlogan(MouseEvent mouseEvent) {
        Response response = ProfileController.changeSlogan(Translator.getMatcherByGroups(
                Translator.CHANGE_SLOGAN, sloganTextField.getText()));
        Alert alert;
        if(response != Response.SLOGAN_CHANGE){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("Change Info Failed!");
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(response.message);
            alert.setHeaderText("Change slogan was successful");
            changeSlogan.setVisible(true);
            saveSlogan.setVisible(false);
            sloganTextField.setVisible(false);
            userSlogan.setVisible(true);
            userSlogan.setText(sloganTextField.getText());
            userSlogan.setWrapText(true);
            Text text = new Text(sloganTextField.getText());
            System.out.println(text.getBoundsInLocal().getWidth());
            int lines = (int)(text.getBoundsInLocal().getWidth() / 170) + 1;
            userSlogan.setMinHeight(lines * 42);
            userSlogan.setMaxHeight(lines * 42);
        }
        alert.showAndWait();
    }

    public void removeSlogan(MouseEvent mouseEvent) {
        userSlogan.setText("slogan is empty");
        userSlogan.setMaxHeight(42);
        userSlogan.setMinHeight(42);
    }
}
