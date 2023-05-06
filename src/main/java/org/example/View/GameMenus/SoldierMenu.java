package org.example.View.GameMenus;

import org.example.Controller.GameControllers.SoldierController;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SoldierMenu extends Menu {
    public MenuType run(Scanner scanner){
        while(true){
            String command = scanner.nextLine();
            Matcher matcher;
            if (Commands.getMatcher(command,Commands.EXIT).find()) {
                System.out.println(Response.CLOSE_SOLDIER_MENU.message);
                break;
            }
            else if((matcher = Commands.getMatcher(command, Commands.MOVE_UNITES_WITH_TYPE)).find())
                System.out.println(SoldierController.moveUnitWithType(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.MOVE_UNITES_WITHOUT_TYPE)).find())
                System.out.println(SoldierController.moveUnitWithoutType(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.PATROL)).find())
                System.out.println(SoldierController.patrolUnit(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.SET_STATE)).find())
                System.out.println(SoldierController.setUnitState(matcher).message);
            else if ((matcher = Commands.getMatcher(command, Commands.POUR_OIL)).find())
                System.out.println(SoldierController.pourOil(matcher).message);
            else if((matcher = Commands.getMatcher(command, Commands.STOP_PATROL)).find())
                System.out.println(SoldierController.stopPatrolling().message);
            else if((matcher = Commands.getMatcher(command, Commands.DIG_DITCH)).find())
                System.out.println(SoldierController.digDitch(matcher).message);
            else System.out.println(Response.INVALID_COMMAND.message);
        }
        return null;
    }
}
