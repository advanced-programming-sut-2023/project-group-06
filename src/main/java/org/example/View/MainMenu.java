package org.example.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.Controller.MainController;
import org.example.Model.Data;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Application {
    MainController mainController;
    private BorderPane borderPane;
    private Scene scene;
    private Stage stage;

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
        /*Data.loadData("src/main/java/org/example/Model/Data.json");*/
        this.stage = stage;
        borderPane = FXMLLoader.load(SignUpMenu.class.getResource("/FXML/MainMenu.fxml"));
        scene = new Scene(borderPane);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
}
