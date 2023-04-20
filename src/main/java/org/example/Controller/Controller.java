package org.example.Controller;

import org.example.Model.Data;
import org.example.Model.User;
import org.example.View.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public static User currentUser;
    public static MenuType currentMenu;
    public static void run(Scanner scanner){
        Data.loadData("src/main/java/org/example/Model/data.json");
        if (Data.isStayLoggedIn()) currentMenu = MenuType.MAIN_MENU;
        else currentMenu = MenuType.START_MENU;
        while (true) {
            currentMenu = currentMenu.menu.run(scanner);
            if(currentMenu == null) break;
        }
    }

    private static String theLastCaptcha;
    public static String getCaptcha(){
        theLastCaptcha = generator();
        return captchaGenerator(theLastCaptcha);
    }
    public static boolean isCaptchaCorrect(String captcha){
        return captcha.equals(theLastCaptcha);
    }
    private static String generator(){
        Random random = new Random(10);
        Random rand = new Random();
        int min = 4;
        int max = 8;
        int length = rand.nextInt(max-min) + min;
        String chars = "0123465789";
        StringBuilder captcha = new StringBuilder();
        while(length-- > 0){
            int index = (int)(Math.random()*10);
            captcha.append(chars.charAt(index));
        }
        return captcha.toString();
    }
    private static String captchaGenerator(String input){
        String[][] digits = { {"⡀⡀⣄⣤⣶⣿⣶⣤⡀⡀⡀⡀",
                               "⡀⡀⣾⣿⠳⡀⠉⣿⣷⡀⡀⡀",
                               "⡀⢀⣿⡇⡀⡀⠈⢺⣿⡀⡀⡀",
                               "⡀⢸⣿⡀⣴⣿⣆⠘⣿⡏⡀⡀",
                               "⡀⢸⣿⡄⠈⠛⠁⢸⣿⠇⡀⡀",
                               "⡀⡀⣿⣧⡀⡀⡀⣼⣿⡀⡀⡀",
                               "⡀⡀⠽⣿⣷⣶⣾⣿⠧⡀⡀⡀",
                               "⡀⡀⡀⡀⠈⠉⠉⡀⡀⡀⡀⡀"},
                               {"⡀⡀⡀⡀⣀⣤⣤⡀⡀⡀⡀⡀",
                                "⡀⡀⡀⣿⠿⢻⣿⡀⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⢸⣿⡀⡀⡀⡀⡀",
                                "⡀⢀⠁⡀⡀⢸⣿⡀⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⠤⢸⣿⡀⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⢸⣿⡀⡀⡀⡀⡀",
                                "⡀⡀⢰⣤⣤⣼⣿⣤⣤⣴⡀⡀",
                                "⡀⡀⠈⠉⠉⠉⠉⠉⠉⠉⡀⡀"},
                               {"⡀⡀⡀⣠⣴⣶⣶⣤⡀⡀⡀⡀",
                                "⡀⡀⢿⡿⠉⠁⠉⢿⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⢸⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⣾⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⢀⣾⡿⠁⡀⡀⡀",
                                "⡀⡀⡀⢀⣴⣿⠋⡀⡀⡀⡀⡀",
                                "⡀⡀⣿⣿⣿⣶⣶⣶⣶⣶⡀⡀",
                                "⡀⡀⠉⠉⠉⠉⠉⠉⠉⠉⡀⡀"},
                               {"⡀⡀⡀⣀⣤⣤⣤⣤⡀⡀⡀⡀",
                                "⡀⡀⢿⡿⠛⠉⠙⢿⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⣸⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⣤⣴⣾⠛⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⠉⠉⠛⣿⣷⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⠈⣿⡇⡀⡀",
                                "⡀⡀⣦⣤⣀⣀⣤⣿⣿⡀⡀⡀",
                                "⡀⡀⠉⠛⠛⠛⠛⠉⡀⡀⡀⡀"},
                               {"⡀⡀⡀⡀⡀⡀⡀⣀⣀⡀⡀⡀",
                                "⡀⡀⡀⣿⣿⡀⡀⣿⣿⡀⡀⡀",
                                "⡀⡀⢀⣿⡇⡀⡀⣿⣿⡀⡀⡀",
                                "⡀⡀⣸⣿⡀⡀⡀⣿⣿⡀⡀⡀",
                                "⡀⡀⣿⣿⡀⡀⡀⣿⣿⡀⡀⡀",
                                "⡀⢀⣿⣿⣿⣿⣿⣿⣿⣾⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⣾⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⠿⠿⡀⡀⡀"},
                               {"⡀⡀⢠⣤⣤⣤⣤⣤⣴⡀⡀⡀",
                                "⡀⡀⢸⣿⠍⠉⠉⠉⠉⡀⡀⡀",
                                "⡀⡀⢸⣿⡀⡀⡀⡀⡀⡀⡀⡀",
                                "⡀⡀⢸⣿⡿⠿⢿⣿⣶⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⠈⣿⡇⡀⡀",
                                "⡀⡀⣤⡆⡀⡀⡀⡀⣿⡏⡀⡀",
                                "⡀⡀⢿⣿⣦⣤⣤⣿⣿⡀⡀⡀",
                                "⡀⡀⡀⠈⠙⠛⠛⠉⡀⡀⡀⡀"},
                               {"⡀⡀⡀⡀⡀⡀⣀⣤⣤⡀⡀⡀",
                                "⡀⡀⡀⣠⣾⣿⠿⠛⠛⡀⡀⡀",
                                "⡀⡀⢠⣿⠏⡀⡀⡀⡀⡀⡀⡀",
                                "⡀⡀⣿⣿⣠⣾⣿⣿⣦⡀⡀⡀",
                                "⡀⡀⣿⣷⠁⡀⡀⠈⣿⣧⡀⡀",
                                "⡀⡀⣿⣧⡀⡀⡀⡀⣿⣿⡀⡀",
                                "⡀⡀⢻⣿⣦⣀⣠⣾⣿⠃⡀⡀",
                                "⡀⡀⡀⠈⠛⠛⠛⠋⡀⡀⡀⡀"},
                               {"⡀⢠⣤⣤⣤⣤⣤⣤⣤⣤⡀⡀",
                                "⡀⢸⣿⠛⠛⠛⠛⢛⣿⡏⡀⡀",
                                "⡀⢸⣿⡀⡀⡀⡀⣿⣿⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⢰⣿⠇⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⣿⡿⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⡀⣼⣿⠁⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⢀⣿⡟⡀⡀⡀⡀⡀",
                                "⡀⡀⡀⡀⠚⠛⡀⡀⡀⡀⡀⡀"},
                               {"⡀⡀⡀⣠⣴⣶⣶⣤⡀⡀⡀⡀",
                                "⡀⡀⣾⣿⠋⠉⠉⢿⣿⡀⡀⡀",
                                "⡀⡀⢿⣿⡀⡀⡀⢸⣿⠁⡀⡀",
                                "⡀⡀⡀⠙⣻⣷⣿⡛⠁⡀⡀⡀",
                                "⡀⡀⣼⣿⠉⡀⠉⠻⣿⡄⡀⡀",
                                "⡀⡀⣿⡇⡀⡀⡀⡀⣿⣷⡀⡀",
                                "⡀⡀⢿⣿⣦⣤⣤⣾⣿⠃⡀⡀",
                                "⡀⡀⡀⠈⠙⠛⠛⠉⡀⡀⡀⡀"},
                               {"⡀⡀⡀⢀⣤⣤⣤⣤⡀⡀⡀⡀",
                                "⡀⡀⣰⣿⠟⠉⠙⠻⣿⡆⡀⡀",
                                "⡀⡀⣿⣿⡀⡀⡀⡀⣿⣿⡀⡀",
                                "⡀⡀⣿⣿⡀⡀⡀⣠⣿⣿⡀⡀",
                                "⡀⡀⠈⠿⣿⣿⡿⠃⣿⡿⡀⡀",
                                "⡀⡀⡀⡀⡀⡀⡀⣼⣿⠁⡀⡀",
                                "⡀⡀⡀⣤⣤⣶⣿⠿⠁⡀⡀⡀",
                                "⡀⡀⡀⠛⠋⠉⡀⡀⡀⡀⡀⡀"}};
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < 8; i++){
            for(Character c : input.toCharArray()) output.append(digits[c.charValue() - '0'][i]);
            if(i < 7) output.append("\n");
        }
        makeNoise(output);
        return output.toString();
    }
    private static void makeNoise(StringBuilder str){
        for(int i = 0; i < str.length(); i++){
            if (str.charAt(i) == '\n') continue;
            int r = 0;
            for(int j = 0; j < 8; j++){
                if(Math.random() < 0.1) r |= 1 << j;
            }
            str.setCharAt(i, (char) (((str.charAt(i) - 10240) ^ r) + 10240));
        }
    }

    public static String makeEntryValid(String entry) {//it was protected before
        if (entry == null) return null;
        if (entry.isEmpty()) return entry;
        if (entry.charAt(0)=='"') return entry.substring(1, entry.length() - 1);
        else return entry;
    }

    public static String nullGroup(Matcher matcher, String[] groupNames) {
        for (int i = 0; i < groupNames.length; i++) {
            if (matcher.group(groupNames[i]) == null) continue;
            if (makeEntryValid(matcher.group(groupNames[i])).isEmpty()) return groupNames[i];
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
