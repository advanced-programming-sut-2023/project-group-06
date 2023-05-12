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
            if(Commands.getMatcher(command, Commands.MOVE_MAP).find())
                System.out.print(MapController.moveMap(command));
            else if((matcher = Commands.getMatcher(command, Commands.SHOW_DETAILS)).find())
                System.out.print(MapController.showDetails(matcher));
            else if(Commands.getMatcher(command, Commands.EXIT).find()) {
                System.out.println("Map menu closed successfully!");
                return null;
            }
            else System.out.println(Response.INVALID_COMMAND.message);
        }
    }
}
