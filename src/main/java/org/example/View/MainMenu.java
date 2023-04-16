package org.example.View;

import org.example.Controller.MainController;

import java.util.Scanner;

public class MainMenu extends Menu{
    MainController mainController;

    @Override
    public MenuType run(Scanner scanner) {
        while(true){
            String command  = scanner.nextLine();
            if(Commands.getMatcher(command, Commands.START_GAME).find()) {
                System.out.println(MainController.startGame(Commands.getMatcher(command, Commands.START_GAME)).message);
                return MenuType.GAME_MENU;
            }
            else if(Commands.getMatcher(command, Commands.ENTER_PROFILE_MENU).find()) {
                System.out.println(Response.ENTER_PROFILE_MENU.message);
                return MenuType.PROFILE_MENU;
            }
            else if(Commands.getMatcher(command, Commands.LOGOUT).find()){
                System.out.println(Response.LOGOUT_SUCCESSFUL);
                return MenuType.SIGN_UP_MENU;
            }
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
