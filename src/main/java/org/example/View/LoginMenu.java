package org.example.View;

import org.example.Controller.LoginController;
import org.example.Model.Data;

import java.util.Scanner;
import java.util.WeakHashMap;
import java.util.regex.Matcher;

public class LoginMenu extends Menu{
    LoginController loginController;
    private static final String[] questions = {"What is my father's name?", "What is my first pet's name?", "What is my mother's last name?"};
    @Override
    public MenuType run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (Commands.getMatcher(command,Commands.LOGIN_USER).find()) {
                System.out.println(LoginController.loginUser(Commands.getMatcher(command,Commands.LOGIN_USER)));
                return MenuType.MAIN_MENU;
            } else if (Commands.getMatcher(command, Commands.FORGOT_PASSWORD).find()) {
                Matcher matcher = Commands.getMatcher(command, Commands.FORGOT_PASSWORD);
                matcher.find();
                String username = matcher.group("username");
                System.out.println(questions[Data.getUserByName(username).getQuestionIndex()]);
                String questionAnswer = scanner.nextLine();
                Response response = LoginController.forgotPassword(Commands.getMatcher(command, Commands.FORGOT_PASSWORD), questionAnswer);
                if (response.equals(Response.PASSWORD_CHANGE)) {
                    System.out.println("Please enter your password:");
                    String newPassword = scanner.nextLine();
                    System.out.println("Please re-enter your password:");
                    String newPasswordConfirmation = scanner.nextLine();
                    System.out.println(LoginController.changePasswordSuccessful(newPassword,newPasswordConfirmation).message);
                } else System.out.println(response.message);
            } else {
                System.out.println(Response.INVALID_COMMAND.message);
            }
        }
    }
}
