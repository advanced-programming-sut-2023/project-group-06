package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.TradeController;
import org.example.View.Commands;
import org.example.View.Response;
import java.util.Scanner;

public class TradeMenu extends Application {
    TradeController tradeController;

    @Override
    public void start(Stage stage) throws Exception {

    }

//    public MenuType run(Scanner scanner){
//        System.out.println(TradeController.tradeNotification());
//        System.out.println(TradeController.showAllPlayers());
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.EXIT).find()) {
//                System.out.println(Response.CLOSE_TRADE_MENU.message);
//                return null;
//            } else if (Commands.getMatcher(command,Commands.SEND_TRADE_REQUEST).find()) {
//                System.out.println(TradeController.sendTrade(Commands.getMatcher(command,Commands.SEND_TRADE_REQUEST)).message);
//            } else if (Commands.getMatcher(command,Commands.ACCEPT_TRADE_REQUEST).find()) {
//                System.out.println(TradeController.acceptTrade(Commands.getMatcher(command,Commands.ACCEPT_TRADE_REQUEST)).message);
//            } else if (Commands.getMatcher(command,Commands.TRADE_LIST).find()) {
//                System.out.println(TradeController.tradeList());
//            } else if (Commands.getMatcher(command,Commands.TRADE_HISTORY).find()) {
//                System.out.println(TradeController.tradeHistory());
//            } else if (Commands.getMatcher(command, Commands.REJECT_TRADE_REQUEST).find()) {
//                System.out.println(TradeController.rejectTrade(Commands.getMatcher(command, Commands.REJECT_TRADE_REQUEST)).message);
//            }else System.out.println(Response.INVALID_COMMAND.message);
//        }
//    }
}
