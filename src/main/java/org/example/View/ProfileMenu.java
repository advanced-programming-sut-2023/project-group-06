package org.example.View;

import org.example.Controller.Controller;
import org.example.Controller.ProfileController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu{
    ProfileController profileController;

    @Override
    public MenuType run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (Commands.getMatcher(command,Commands.CHANGE_USERNAME).find()) {
                System.out.println(ProfileController.changeUsername(Commands.getMatcher(command,Commands.CHANGE_USERNAME)).message);
            } else if (Commands.getMatcher(command,Commands.CHANGE_NICKNAME).find()) {
                System.out.println(ProfileController.changeNickname(Commands.getMatcher(command,Commands.CHANGE_NICKNAME)).message);
            } else if (Commands.getMatcher(command,Commands.CHANGE_PASSWORD).find()) {
                Response response = ProfileController.changePassword(Commands.getMatcher(command,Commands.CHANGE_PASSWORD));
                System.out.println(response.message);
                Matcher matcher = Commands.getMatcher(command,Commands.CHANGE_PASSWORD);
                matcher.find();
                String newPassword = matcher.group("newPassword");
                String oldPassword = matcher.group("oldPassword");
                if (response.equals(Response.RE_ENTER_PASSWORD)) {
                    String passwordConfirm = scanner.nextLine();
                    System.out.println(ProfileController.confirmReEnteredPassword(oldPassword,newPassword,passwordConfirm).message);
                }
            } else if (Commands.getMatcher(command,Commands.CHANGE_EMAIL).find()) {
                System.out.println(ProfileController.changeEmail(Commands.getMatcher(command,Commands.CHANGE_EMAIL)).message);
            } else if (Commands.getMatcher(command,Commands.CHANGE_SLOGAN).find()) {
                System.out.println(ProfileController.changeSlogan(Commands.getMatcher(command,Commands.CHANGE_SLOGAN)).message);
            } else if (Commands.getMatcher(command,Commands.REMOVE_SLOGAN).find()) {
                System.out.println(ProfileController.removeSlogan().message);
            } else if (Commands.getMatcher(command,Commands.SHOW_HIGH_SCORE).find()) {
                System.out.println(ProfileController.showHighScore());
            } else if (Commands.getMatcher(command,Commands.SHOW_RANK).find()) {
                System.out.println(ProfileController.showRank());
            } else if (Commands.getMatcher(command,Commands.SHOW_SLOGAN).find()) {
                System.out.println(ProfileController.showSlogan());
            } else if (Commands.getMatcher(command,Commands.SHOW_ALL_PROFILE).find()) {
                System.out.println(ProfileController.showInfo());
            } else if (Commands.getMatcher(command,Commands.BACK).find()) {
                System.out.println(Response.ENTER_MAIN_MENU.message);
                return MenuType.MAIN_MENU;
            } else {
                System.out.println(Response.INVALID_COMMAND.message);
            }
        }
    }
}
