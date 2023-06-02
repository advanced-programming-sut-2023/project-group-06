package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.MapController;
import org.example.View.Commands;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu extends Application {
    MapController mapController;

//    public MenuType run(Scanner scanner){
//        while(true){
//            String command = scanner.nextLine();
//            Matcher matcher;
//            if(Commands.getMatcher(command, Commands.MOVE_MAP).find())
//                System.out.print(MapController.moveMap(command));
//            else if((matcher = Commands.getMatcher(command, Commands.SHOW_DETAILS)).find())
//                System.out.print(MapController.showDetails(matcher));
//            else if(Commands.getMatcher(command, Commands.EXIT).find()) {
//                System.out.println("Map menu closed successfully!");
//                return null;
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
