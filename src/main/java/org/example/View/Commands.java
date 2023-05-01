package org.example.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // word : (((\"[^\"]*\")|\S*))
    // number : ((-?\d*)|(\"-?\d*\"))
    CREATE_USER("(?!.* -p .* -p .*)(?!.* -u .* -u .*)(?!.* -email .* -email .*)(?!.* -n .* -n .*)" +
            "(?=.* -p )(?=.* -u )(?=.* -email )(?=.* -n )^user create(( -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*))))|" +
            "( -p (?<password>(((\\\"[^\\\"]*\\\")|\\S*)))( (?<passwordConfirmation>(((\\\"[^\\\"]*\\\")|\\S*))))?)|" +
            "( -email (?<email>(((\\\"[^\\\"]*\\\")|\\S*))))|( (?<sloganSign>-s) (?<slogan>(((\\\"[^\\\"]*\\\")|\\S*))))|( -n (?<nickname>(((\\\"[^\\\"]*\\\")|\\S*))))){4,5}$"),
    QUESTION_PICK("(?=.* -q )(?=.* -a )(?=.* -c )^question pick(( -q (?<questionNumber>((-?\\d*)|(\\\"-?\\d*\\\"))))|" +
            "( -a (?<answer>(((\\\"[^\\\"]*\\\")|\\S*))))|( -c (?<answerConfirmation>(((\\\"[^\\\"]*\\\")|\\S*))))){3}$"),
    BACK("^back$"),
    LOGIN_USER("(?=.* -p )(?=.* -u )^user login(( -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*))))|" +
            "( -p (?<password>(((\\\"[^\\\"]*\\\")|\\S*))))){2}(?<stayLoggedIn> --stay-logged-in)?$"),
    FORGOT_PASSWORD("^forgot my password -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    LOGOUT("^user logout$"),
    CHANGE_USERNAME("^profile change -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    CHANGE_NICKNAME("^profile change -n (?<nickname>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
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
    START_GAME("^start game with( (?<username2>(((\"[^\"]*\")|\\S*))))?( (?<username3>(((\"[^\"]*\")|\\S*))))?" +
            "( (?<username4>(((\"[^\"]*\")|\\S*))))?( (?<username5>(((\"[^\"]*\")|\\S*))))?" +
            "( (?<username6>(((\"[^\"]*\")|\\S*))))?( (?<username7>(((\"[^\"]*\")|\\S*))))?" +
            "( (?<username8>(((\"[^\"]*\")|\\S*))))?$"),
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
    SHOW_POPULATION("^show population$"),
    SHOW_WEALTH("^show wealth$"),
    SHOW_AVAILABLE_ENGINEERS("^show available engineers$"),
    EXIT("^exit$"),
    //Game Menu Commands
    START_THE_GAME("^let's play$"),
    ENTER_KINGDOM_MENU("^enter kingdom menu$"),
    ENTER_TRADE_MENU("^enter trade menu$"),
    ENTER_MAP_MENU("^enter map menu$"),
    ENTER_SHOP_MENU("^enter shop menu$"),
    DROP_BUILDING("dropbuilding -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+) -type (?<type>(((\"[^\"]*\")|\\S*)))( -d (?<direction>[nswer]))?"),
    SET_MAP_WIDTH_HEIGHT("width: (?<width>\\d+) height: (?<height>\\d+)"),
    CHOOSE_DEFAULT_MAP("map name: (?<mapName>(((\\\"[^\\\"]*\\\")|\\S*)))"),
    DROP_TREE("droptree -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+) -type (?<type>(((\"[^\"]*\")|\\S*)))"),
    SET_TEXTURE_ONE_TILE("settexture -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+) -t (?<type>(((\"[^\"]*\")|\\S*)))"),
    SET_TEXTURE_MULTIPLE_TILES("settexture -x1 (?<x1>(\\-)?\\d+) -y1 (?<y1>(\\-)?\\d+) -x2 (?<x2>(\\-)?\\d+) -y2 (?<y2>(\\-)?\\d+) -t (?<type>(((\"[^\"]*\")|\\S*)))"),
    PUT_MAIN_CASTLE("main castle -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+) -color (?<color>(((\"[^\"]*\")|\\S*))) -d (?<direction>[nswer])"),
    NEXT_TURN("next turn"),
    CLEAR_BLOCK("clear -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+)"),
    DROP_ROCK("droprock -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+) -d (?<direction>[nswer])"),
    SELECT_BUILDING("select building -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+)"),
    SELECT_UNIT("^select unit -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+)$"),
    //Map Menu Commands
    SHOW_MAP("show map -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+)"),
    MOVE_MAP("map(( up( (?<up>\\d+))?)|( right( (?<right>\\d+))?)|( down( (?<down>\\d+))?)|( left( (?<left>\\d+))?))*"),
    SHOW_DETAILS("show details -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+)"),
    SAVE_MAP("save map -name (?<name>(((\"[^\"]*\")|\\S*)))"),
    CREATE_UNIT("(?!.* -t .* -t .*)(?=.* -t )^create unit( -t (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))| -c (?<count>((-?\\d*)|(\\\"-?\\d*\\\")))){1,2}$"),
    REPAIR("^repair$"),
    CREATE_WEAPON("(?!.* -t .* -t .*)(?=.* -t )^create weapon( -t (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))| -c (?<count>((-?\\d*)|(\\\"-?\\d*\\\")))){1,2}$"),
    SHOW_RESOURCES("^show resources$"),
    SHOW_WEAPONS("^show weapons$"),
    SHOW_PRICES_LIST("^show prices list$"),
    BUY("(?!.* -t .* -t .*)(?=.* -t )^buy( -t (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))| -c (?<count>((-?\\d*)|(\\\"-?\\d*\\\")))){1,2}$"),
    SELL("(?!.* -t .* -t .*)(?=.* -t )^sell( -t (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))| -c (?<count>((-?\\d*)|(\\\"-?\\d*\\\")))){1,2}$"),
    SEND_TRADE_REQUEST("(?=.* -u )(?=.* -t )(?=.* -a )(?=.* -p )(?=.* -m )^trade(( -t (?<resourceType>(((\\\"[^\\\"]*\\\")|\\S*))))" +
            "|( -a (?<resourceAmount>((-?\\d*)|(\\\"-?\\d*\\\"))))" +
            "|( -m (?<message>(((\\\"[^\\\"]*\\\")|\\S*))))|( -p (?<price>((-?\\d*)|(\\\"-?\\d*\\\"))))" +
            "|( -u (?<username>(((\\\"[^\\\"]*\\\")|\\S*))))){5}$"),
    ACCEPT_TRADE_REQUEST("(?=.* -m )(?=.* -i )^trade accept(( -i (?<id>((-?\\d*)|(\\\"-?\\d*\\\"))))" +
            "|( -m (?<message>(((\\\"[^\\\"]*\\\")|\\S*))))){2}$"),
    TRADE_LIST("^trade list$"),
    TRADE_HISTORY("^trade history$"),
    SHOW_GOLD("^show gold$"),
    SET_THE_GATE("^(?<command>(open|close)) the gate -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+)$"),
    //Soldier Menu Commands
    MOVE_UNITES_WITH_TYPE_SINA("(?=.* -x )(?=.* -y )(?=.* -t )^move unit to( -x (?<x>((-?\\d*)|(\\\"-?\\d*\\\")))| -y (?<y>((-?\\d*)|(\\\"-?\\d*\\\")))| -type (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))){3}$"),
    MOVE_UNITES_WITH_TYPE("^move unit to -x (?<x>(\\-)?\\d+) -y (?<y>(\\-)?\\d+) -t (?<type>(((\"[^\"]*\")|\\S*)))$"),
    SHOW_SOLDIERS("^show soldiers$"),
    SHOW_HORSE("^show horses$"),
    POUR_OIL("^pour oil -d (?<direction>[nswer])$")
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
