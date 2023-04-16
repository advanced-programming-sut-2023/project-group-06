package org.example.View;

import org.example.Controller.Controller;
import org.example.Controller.StartController;

import java.util.Scanner;

public class StartMenu extends Menu{
    StartController startController;

    @Override
    public MenuType run(Scanner scanner) {
        while (true) {
            System.out.println("Already have an account?");
            String command = scanner.nextLine();
            Response response = StartController.chooseMenuToEnter(command);
            if (response == null) continue;
            System.out.println(response.message);
            if (response.equals(Response.ENTER_SIGN_UP_MENU)) return MenuType.SIGN_UP_MENU;
            if (response.equals(Response.ENTER_LOGIN_MENU)) return MenuType.LOGIN_MENU;
            if (response.equals(Response.ENTER_MAIN_MENU)) return MenuType.MAIN_MENU;
        }
    }
}
