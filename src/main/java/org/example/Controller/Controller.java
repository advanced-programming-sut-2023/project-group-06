package org.example.Controller;

import org.example.Model.User;
import org.example.View.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public static User currentUser;
    public static MenuType currentMenu;

    public static void run(Scanner scanner){
        currentMenu = MenuType.START_MENU;
        while (true) {
            currentMenu = currentMenu.menu.run(scanner);
            if(currentMenu == null) break;
        }
    }
    public static String captchaGenerator(){
        return null;
        //todo
    }
    protected static String makeEntryValid(String entry) {
        if (entry.charAt(0)=='"') return entry.substring(1, entry.length() - 1);
        else return entry;
    }

    protected static String nullGroup(Matcher matcher, String[] groupNames) {
        for (int i = 0; i < groupNames.length; i++) {
            if (matcher.group(groupNames[i]) == null) return groupNames[i];
        }
        return null;
    }

    protected static boolean isUsernameValid(String username) {
        return !Pattern.compile("\\W").matcher(username).find();
    }

    protected static boolean isLongPassword(String password) {
        return (password.length() >= 6);
    }

    protected static boolean containCapitalLetter(String word) {
        return Pattern.compile("[A-Z]").matcher(word).find();
    }

    protected static boolean containLowercaseLetter(String word) {
        return Pattern.compile("[a-z]").matcher(word).find();
    }

    protected static boolean containSymbol(String word) {
        return Pattern.compile("\\W").matcher(word).find();
    }

    protected static boolean containNumber(String word) {
        return Pattern.compile("\\d").matcher(word).find();
    }

    protected static boolean isValidEmail(String email) {
        return Pattern.compile("^\\w+([\\.]?\\w+)*@\\w+([\\.]?\\w+)*(\\.\\w+)+$").matcher(email).find();
    }
}
