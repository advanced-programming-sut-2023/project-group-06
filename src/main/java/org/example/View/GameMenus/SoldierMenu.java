package org.example.View.GameMenus;

import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Scanner;

public class SoldierMenu extends Menu {
    public MenuType run(Scanner scanner){
        while(true){
            String command = scanner.nextLine();
            if (Commands.getMatcher(command,Commands.EXIT).find()) {
                System.out.println(Response.CLOSE_BUILDING_MENU.message);
                break;
            }
            else System.out.println(Response.INVALID_COMMAND.message);
        }
        return null;
    }
}
