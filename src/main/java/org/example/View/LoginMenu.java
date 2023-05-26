package org.example.View;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Controller.LoginController;
import org.example.Model.Data;

import java.util.Scanner;
import java.util.WeakHashMap;
import java.util.regex.Matcher;

public class LoginMenu extends Application {
    LoginController loginController;
    private static final String[] questions = {"What is my father's name?", "What is my first pet's name?", "What is my mother's last name?"};
//    public MenuType run(Scanner scanner) {
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.LOGIN_USER).find()) {
//                Matcher matcher = Commands.getMatcher(command,Commands.LOGIN_USER); matcher.find();
//                String username = matcher.group("username");
//                if (LoginController.setLastLoginAttempt(username, (System.currentTimeMillis() / 1000L))) {
//                    Response response = LoginController.loginUser(Commands.getMatcher(command, Commands.LOGIN_USER));
//                    System.out.println(response.message);
//                    if (response == Response.LOGIN_SUCCESSFUL) {
//                        LoginController.resetLoginAttempts(username);
//                        return MenuType.MAIN_MENU;
//                    }
//                } else {
//                    long lastLoginAttempt = LoginController.getLastLoginAttempt(username);
//                    long currentTime = System.currentTimeMillis() / 1000L;
//                    System.out.printf(Response.TRY_AGAIN_LATER.message,
//                            lastLoginAttempt + LoginController.getNumberOfLoginAttempts(username) * 5 - currentTime);
//                    System.out.println();
//                }
//            } else if (Commands.getMatcher(command, Commands.FORGOT_PASSWORD).find()) {
//                Matcher matcher = Commands.getMatcher(command, Commands.FORGOT_PASSWORD);
//                matcher.find();
//                String username = matcher.group("username");
//                System.out.println(questions[Data.getUserByName(username).getQuestionIndex()]);
//                String questionAnswer = scanner.nextLine();
//                Response response = LoginController.forgotPassword(Commands.getMatcher(command, Commands.FORGOT_PASSWORD), questionAnswer);
//                if (response.equals(Response.PASSWORD_CHANGE)) {
//                    System.out.println("Please enter your password:");
//                    String newPassword = scanner.nextLine();
//                    Response response1 = LoginController.isPasswordStrong(newPassword);
//                    if (response1 != null) {
//                        System.out.println(response1.message);
//                        continue;
//                    }
//                    System.out.println("Please re-enter your password:");
//                    String newPasswordConfirmation = scanner.nextLine();
//                    System.out.println(LoginController.changePasswordSuccessful(username,questionAnswer,newPassword,newPasswordConfirmation).message);
//                } else System.out.println(response.message);
//            } else if (Commands.getMatcher(command,Commands.BACK).find()) {
//                return MenuType.START_MENU;
//            } else {
//                System.out.println(Response.INVALID_COMMAND.message);
//            }
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
