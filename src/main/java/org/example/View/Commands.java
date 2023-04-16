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
    SHOW_ALL_PROFILE("^profile display$"),
    //Main Menu Commands
    START_GAME("^start game with( (?<username2>(((\"[^\"]*\")|\\S*))))?( (?<username3>(((\\\"[^\\\"]*\\\")|\\S*))))?" +
            "( (?<username4>(((\\\"[^\\\"]*\\\")|\\S*))))?( (?<username5>(((\\\"[^\\\"]*\\\")|\\S*))))?" +
            "( (?<username6>(((\\\"[^\\\"]*\\\")|\\S*))))?( (?<username7>(((\\\"[^\\\"]*\\\")|\\S*))))?" +
            "( (?<username8>(((\\\"[^\\\"]*\\\")|\\S*))))?$"),
    ENTER_PROFILE_MENU("^enter profile menu$"),
    //Kingdom Menu Commands
    SHOW_POPULARITY_FACTORS("^show popularity factors$"),
    SET_FOOD_RATE("^food rate -r (?<foodRate>(\\-)?\\d+)$"),
    SET_TAX_RATE("^tax rate -r (?<taxRate>(\\-)?\\d+)$"),
    SET_FEAR_RATE("^fear rate -r (?<fearRate>(\\-)?\\d+)$"),
    SHOW_POPULARITY("^show popularity$"),
    SHOW_FOOD_LIST("^show food list$"),
    SHOW_FOOD_RATE("^food rate show$"),
    SHOW_TAX_RATE("^tax rate show$"),
    EXIT("^exit$"),
    //Game Menu Commands
    START_THE_GAME("^let's play$"),
    ENTER_KINGDOM_MENU("^enter kingdom menu$"),
    ENTER_TRADE_MENU("^enter trade menu$"),
    ENTER_MAP_MENU("^enter map menu$"),
    ENTER_SHOP_MENU("^enter shop menu$"),
    ;

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
