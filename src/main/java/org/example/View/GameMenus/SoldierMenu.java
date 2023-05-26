package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.SoldierController;
import org.example.View.Commands;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SoldierMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {

    }
//    public MenuType run(Scanner scanner){
//        while(true){
//            String command = scanner.nextLine();
//            Matcher matcher;
//            if (Commands.getMatcher(command,Commands.EXIT).find()) {
//                System.out.println(Response.CLOSE_SOLDIER_MENU.message);
//                break;
//            }
//            else if((matcher = Commands.getMatcher(command, Commands.MOVE_UNITES_WITH_TYPE)).find())
//                System.out.println(SoldierController.moveUnitWithType(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.MOVE_UNITES_WITHOUT_TYPE)).find())
//                System.out.println(SoldierController.moveUnitWithoutType(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.PATROL)).find())
//                System.out.println(SoldierController.patrolUnit(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.SET_STATE)).find())
//                System.out.println(SoldierController.setUnitState(matcher).message);
//            else if ((matcher = Commands.getMatcher(command, Commands.POUR_OIL)).find())
//                System.out.println(SoldierController.pourOil(matcher).message);
//            else if(Commands.getMatcher(command, Commands.STOP_PATROL).find())
//                System.out.println(SoldierController.stopPatrolling().message);
//            else if((matcher = Commands.getMatcher(command, Commands.DIG_DITCH)).find())
//                System.out.println(SoldierController.digDitch(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.FILL_DITCH)).find())
//                System.out.println(SoldierController.fillDitch(matcher).message);
//            else if(Commands.getMatcher(command, Commands.STOP_DIGGING).find())
//                System.out.println(SoldierController.stopDigging().message);
//            else if ((matcher = Commands.getMatcher(command, Commands.BUILD_EQUIPMENT)).find())
//                System.out.println(SoldierController.buildEquipment(matcher).message);
//            else if((matcher = Commands.getMatcher(command, Commands.DIG_TUNNEL)).find())
//                System.out.println(SoldierController.digTunnel(matcher).message);
//            else if ((matcher = Commands.getMatcher(command, Commands.ATTACK_ENEMY)).find())
//                System.out.println(SoldierController.attack(matcher).message);
//            else if ((matcher = Commands.getMatcher(command, Commands.ATTACK)).find())
//                System.out.println(SoldierController.fireAtEnemy(matcher).message);
//            else if ((matcher = Commands.getMatcher(command, Commands.PUT_LADDER)).find())
//                System.out.println(SoldierController.putLadder(matcher).message);
//            else if (Commands.getMatcher(command, Commands.THROW_LADDER).find())
//                System.out.println(SoldierController.throwLadder().message);
//            else if (Commands.getMatcher(command, Commands.DISBAND).find())
//                System.out.println(SoldierController.disband().message);
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//        return null;
//    }
}
