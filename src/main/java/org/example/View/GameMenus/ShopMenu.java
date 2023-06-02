package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.ShopController;
import org.example.View.Commands;
import org.example.View.Response;

import java.util.Scanner;

public class ShopMenu extends Application {
    ShopController shopController;

    @Override
    public void start(Stage stage) throws Exception {

    }

//    public MenuType run(Scanner scanner){
//        while(true){
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.EXIT).find()) {
//                System.out.println(Response.CLOSE_SHOP_MENU.message);
//                break;
//            } else if (Commands.getMatcher(command,Commands.SHOW_PRICES_LIST).find()) {
//                System.out.println(ShopController.showPriceList());
//            } else if (Commands.getMatcher(command,Commands.BUY).find()) {
//                System.out.println(ShopController.buy(Commands.getMatcher(command,Commands.BUY)).message);
//            } else if (Commands.getMatcher(command,Commands.SELL).find()) {
//                System.out.println(ShopController.sell(Commands.getMatcher(command,Commands.SELL)).message);
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//        return null;
//    }
}
