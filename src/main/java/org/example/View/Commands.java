package org.example.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // word : (((\"[^\"]*\")|\S*))
    CREATE_USER("(?=.* -p )(?=.* -u )(?=.* -email )(?=.* -n )^create user(( -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*))))|" +
            "( -p (?<password>(((\\\"[^\\\"]*\\\")|\\S*)))( (?<passwordConfirmation>(((\\\"[^\\\"]*\\\")|\\S*))))?)|" +
            "( -email (?<email>(((\\\"[^\\\"]*\\\")|\\S*))))|( (?<sloganSign>-s) (?<slogan>(((\\\"[^\\\"]*\\\")|\\S*))))|( -n (?<nickname>(((\\\"[^\\\"]*\\\")|\\S*))))){4,5}$"),
    QUESTION_PICK("(?=.* -q )(?=.* -a )(?=.* -c )^question pick(( -q (?<questionNumber>-?\\d+))|" +
            "( -a (?<answer>(((\\\"[^\\\"]*\\\")|\\S*))))|( -c (?<answerConfirmation>(((\\\"[^\\\"]*\\\")|\\S*))))){3}$"),
    BACK("^back$"),
    LOGIN_USER("(?=.* -p )(?=.* -u )^user login(( -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*))))|" +
            "( -p (?<password>(((\\\"[^\\\"]*\\\")|\\S*))))){2}(?<stayLoggedIn> --stay logged in)?$"),
    FORGOT_PASSWORD("^forgot my password -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    LOGOUT("^user logout$"),
    CHANGE_USERNAME("^profile change -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    CHANGE_PASSWORD("(?=.* -n )(?=.* -o )^profile change(( -o (?<oldPassword>(((\\\"[^\\\"]*\\\")|\\S*))))" +
            "|( -n (?<newPassword>(((\\\"[^\\\"]*\\\")|\\S*))))){2}$"),
    CHANGE_EMAIL("^profile change -e (?<email>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    CHANGE_SLOGAN("^profile change -s (?<slogan>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    REMOVE_SLOGAN("^profile remove slogan$"),
    SHOW_HIGH_SCORE("^profile display highscore$"),
    SHOW_RANK("^profile display rank$"),
    SHOW_SLOGAN("^profile display slogan$"),
    SHOW_ALL_PROFILE("^profile display$");

    private String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, Commands commandRegex){
        return Pattern.compile(commandRegex.regex).matcher(command);
    }

    private static boolean existFlag(String command, String flag) {
        if (Pattern.compile(" -" + flag + " ").matcher(command).find()) return true;
        return false;
    }
}
