package org.example.View;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Translator {
    CREATE_USER("username", "password", "passwordConfirmation", "nickname", "email", "slogan"),
    LOGIN_USER("username", "password", "stayLoggedIn"),
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
