package org.example.View;

import org.example.Controller.Controller;
import org.example.Controller.SignUpController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpMenu extends Menu{
    SignUpController signUpController;
    private static final String[] allRandomSlogans = {"Every man dies. Not every man lives.", "Nothing is impossible. The word itself says I’m possible!",
            "We need much less than we think we need.", "You do not find the happy life. You make it", "I shall have my revenge, in this life or the next",
            "A hero need not speak, When he is gone, the world will speak for him", "They told me I couldn't, that’s why I did.",
            "I am a man of fortune, and I must seek my fortune.", "Keep friends close and enemies guessing.",
            "I don’t need to get a life, I am a gamer I have lots of lives.", "Gamer zone, Be careful", "Eat-Sleep-Play-Repeat", "Life is a game, Play to win",
            "Escape Reality & Play Games.", "Do not disturb, I am Gaming.", "Games the only legal place to kill stupids."};
    @Override
    public MenuType run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (Commands.getMatcher(command,Commands.CREATE_USER).find()) {
                Response response = SignUpController.createUser(Commands.getMatcher(command,Commands.CREATE_USER), scanner);
                System.out.println(response.message);
                if (response.equals(Response.PICK_SECURITY_QUESTION)) {
                    Matcher matcher = Commands.getMatcher(command,Commands.CREATE_USER);
                    matcher.find();
                    String username = matcher.group("username");
                    while (true) {
                        command = scanner.nextLine();
                        if (Commands.getMatcher(command,Commands.QUESTION_PICK).find()){
                            response = SignUpController.securityQuestion(Commands.getMatcher(command,Commands.QUESTION_PICK),username);
                            System.out.println(response.message);
                            if (response.equals(Response.USER_CREATED)) {
                                return MenuType.LOGIN_MENU;
                            }
                        } else if (Commands.getMatcher(command,Commands.BACK).find()) {
                            SignUpController.back(Commands.getMatcher(command,Commands.BACK),username);
                            break;
                        } else {
                            System.out.println(Response.INVALID_COMMAND.message);
                        }

                    }
                }
            }
        }
    }
    public static void randomSlogan(String slogan) {
        slogan = allRandomSlogans[(int)(Math.random() * 16)];
        System.out.println("Your slogan is " + slogan);
    }
    public static boolean randomPassword(String password, Scanner scanner) {
        password = SignUpController.passwordGenerator();
        System.out.println("Your random password is: " + password + '\n' +
                "Please re-enter your password here: ");
        String enteredPassword = scanner.nextLine();
        if (!password.equals(enteredPassword)) return false;
        return true;
    }
}
