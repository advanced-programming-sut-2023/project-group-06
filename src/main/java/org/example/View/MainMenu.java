package org.example.View;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.MainController;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Application {
    MainController mainController;

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

    }
}
