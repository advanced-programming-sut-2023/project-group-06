package org.example.View;

import org.example.Controller.MainController;
import org.example.Main;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu{
    MainController mainController;

    @Override
    public MenuType run(Scanner scanner) {
        while(true){
            String command  = scanner.nextLine();
            Matcher matcher;
            if((matcher = Commands.getMatcher(command, Commands.START_GAME)).find()) {
                System.out.println(MainController.startGame(matcher).message);
                return MenuType.GAME_MENU;
            }
            else if(Commands.getMatcher(command, Commands.ENTER_PROFILE_MENU).find()) {
                System.out.println(Response.ENTER_PROFILE_MENU.message);
                return MenuType.PROFILE_MENU;
            }
            else if(Commands.getMatcher(command, Commands.LOGOUT).find()){
                System.out.println(Response.LOGOUT_SUCCESSFUL.message);
                return MenuType.LOGIN_MENU;
            }
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
