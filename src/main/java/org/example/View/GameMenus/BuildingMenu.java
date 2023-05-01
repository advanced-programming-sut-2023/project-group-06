package org.example.View.GameMenus;

import org.example.Controller.GameControllers.BuildingController;
import org.example.View.Commands;
import org.example.View.Menu;
import org.example.View.MenuType;
import org.example.View.Response;

import java.util.Scanner;

public class BuildingMenu extends Menu {
    public MenuType run(Scanner scanner) {
        if (BuildingController.isCastleType()) System.out.println("building hit point: " + BuildingController.showBuildingHp());
        if (BuildingController.isCagedWarDogs()) {
            System.out.println(BuildingController.releaseDogs().message);
            System.out.println(Response.CLOSE_BUILDING_MENU.message);
            return null;
        }
        while (true) {
            String command = scanner.nextLine();
            if (Commands.getMatcher(command,Commands.CREATE_UNIT).find()) {
                System.out.println(BuildingController.createUnit(Commands.getMatcher(command,Commands.CREATE_UNIT)).message);
            } else if (Commands.getMatcher(command,Commands.REPAIR).find()) {
                System.out.println(BuildingController.repair().message);
            } else if (Commands.getMatcher(command,Commands.EXIT).find()) {
                System.out.println(Response.CLOSE_BUILDING_MENU.message);
                break;
            } else if (Commands.getMatcher(command,Commands.CREATE_WEAPON).find()) {
                System.out.println(BuildingController.createWeapon(Commands.getMatcher(command,Commands.CREATE_WEAPON)).message);
            } else {
                System.out.println(Response.INVALID_COMMAND.message);
            }
        }
        return null;
    }
}
