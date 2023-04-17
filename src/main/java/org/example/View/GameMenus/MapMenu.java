package org.example.View.GameMenus;

import org.example.Controller.GameControllers.MapController;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu extends Menu {
    MapController mapController;

    public MenuType run(Scanner scanner){
        while(true){
            String command = scanner.nextLine();
            Matcher matcher;
            if((matcher = Commands.getMatcher(command, Commands.SHOW_MAP)).find())
                System.out.println(MapController.showMap(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.MOVE_MAP)).find())
                MapController.moveMap(matcher);
            else if((matcher = Commands.getMatcher(command, Commands.SHOW_DETAILS)).find())
                System.out.println(MapController.showDetails(matcher));
            else if(Commands.getMatcher(command, Commands.EXIT).find())
                return MenuType.GAME_MENU;
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
