package org.example.View;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Translator {
    CREATE_USER("username", "password", "passwordConfirmation", "nickname", "email", "slogan"),
    LOGIN_USER("username", "password", "stayLoggedIn"),
    CHANGE_USERNAME("username"),
    CHANGE_NICKNAME("nickname"),
    CHANGE_PASSWORD("oldPassword", "newPassword"),
    CHANGE_EMAIL("email"),
    CHANGE_SLOGAN("slogan"),
    START_GAME("username2", "username3", "username4", "username5", "username6", "username7", "username8"),
    SECURITY_QUESTION("questionNumber", "answer", "answerConfirmation"),
    ;

    private String[] groupNames;

    Translator(String... groupNames) {
        this.groupNames = groupNames;
    }

    public static Matcher getMatcherByGroups(Translator translator, String... inputs) {
        String command = "";
        String regex = "";
        if (inputs.length != translator.groupNames.length) System.out.println("ERROR IN TRANSLATING IS INCOMING : ");
        for (int i = 0; i < translator.groupNames.length; i++) {
            String groupName = translator.groupNames[i];
            regex += "(?<" + groupName + ">.*)/";
            command += inputs[i] + "/";
        }
        Matcher matcher = Pattern.compile(regex).matcher(command);
        System.out.println(matcher);
        return Pattern.compile(regex).matcher(command);
    }
}
