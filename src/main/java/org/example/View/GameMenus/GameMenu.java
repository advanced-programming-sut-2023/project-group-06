package org.example.View.GameMenus;

import org.example.Controller.GameControllers.GameController;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Scanner;

public class GameMenu extends Menu {
    GameController gameController;

    public MenuType run(Scanner scanner){
        while(true){
            String command = scanner.nextLine();
            if(Commands.getMatcher(command, Commands.START_THE_GAME).find())
                break;
                //todo
            else System.out.println(Response.INVALID_COMMAND.message);
        }
        while(true){
            String command = scanner.nextLine();
            if(Commands.getMatcher(command, Commands.ENTER_MAP_MENU).find())
                return MenuType.MAP_MENU;
            else if(Commands.getMatcher(command, Commands.ENTER_KINGDOM_MENU).find())
                return MenuType.KINGDOM_MENU;
            else if(Commands.getMatcher(command, Commands.ENTER_SHOP_MENU).find())
                return MenuType.SHOP_MENU;
            else if(Commands.getMatcher(command, Commands.ENTER_TRADE_MENU).find())
                return MenuType.TRADE_MENU;
                //todo
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
