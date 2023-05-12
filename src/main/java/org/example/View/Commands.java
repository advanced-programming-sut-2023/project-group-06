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
    DROP_BUILDING("(?!.* -x .* -x )(?!.* -y .* -y )(?!.* -type .* -type )(?!.* -d .* -d )(?=.* -x )(?=.* -y )(?=.* -type )" +
            "^dropbuilding( -x (?<x>(-?\\d*)|(\\\"-?\\\\d*\\\"))| " +
            "-y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -type (?<type>(\\\"[^\\\\\\\"]*\\\")|\\S*)| -d (?<direction>[nswer])){3,4}$"),
    SET_MAP_WIDTH_HEIGHT("(?=.*width: )(?=.*height: )^(width: (?<width>\\d+)\\s*|height: (?<height>\\d+)\\s*){2}$"),
    CHOOSE_DEFAULT_MAP("map name: (?<mapName>(((\\\"[^\\\"]*\\\")|\\S*)))"),
    DROP_TREE("(?=.* -x )(?=.* -y )(?=.* -type )^droptree( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -type (?<type>(\\\"[^\\\"]*\\\")|\\S*)){3}$"),
    SET_TEXTURE_ONE_TILE("(?=.* -x )(?=.* -y )(?=.* -t )^settexture( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -t (?<type>(\\\"[^\\\"]*\\\")|\\S*)){3}$"),
    SET_TEXTURE_MULTIPLE_TILES("(?=.* -x1 )(?=.* -y1 )(?=.* -x2 )(?=.* -y2 )(?=.* -t )^settexture( -x1 (?<x1>(-?\\d*)|(\\\"-?\\d*\\\"))| -y1 (?<y1>(-?\\d*)|(\\\"-?\\d*\\\"))| -x2 (?<x2>(-?\\d*)|(\\\"-?\\d*\\\"))| -y2 (?<y2>(-?\\d*)|(\\\"-?\\d*\\\"))| -t (?<type>(\\\"[^\\\"]*\\\")|\\S*)){5}$"),
    PUT_MAIN_CASTLE("(?=.* -x )(?=.* -y )(?=.* -color )(?=.* -d )^main castle( -x (?<x>(\\-)?\\d+)| -y (?<y>(\\-)?\\d+)| -color (?<color>(((\"[^\"]*\")|\\S*)))| -d (?<direction>[nswer])){4}$"),
    NEXT_TURN("next turn"),
    CLEAR_BLOCK("(?=.* -x )(?=.* -y )^clear( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    DROP_ROCK("(?=.* -x )(?=.* -y )(?=.* -d )^droprock( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -d (?<direction>[nswer])){3}$"),
    SELECT_BUILDING("(?=.* -x )(?=.* -y )^select building( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    DROP_UNIT("(?!.* -x .* -x)(?!.* -y .* -y)(?!.* -t .* -t)(?=.* -x )(?=.* -y )(?=.* -t )^dropunit( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -t (?<type>((\\\"[^\\\"]*\\\")|\\S*))| -c (?<count>(\\-)?\\d+)){3,4}$"),
    SELECT_UNIT("(?=.* -x )(?=.* -y )(?=.* -t )^select unit( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -t (?<type>((\\\"[^\\\"]*\\\")|\\S*))){3}$"),
    //Map Menu Commands
    SHOW_MAP("(?=.* -x )(?=.* -y )^show map( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    MOVE_MAP("^map(( up( (?<up>\\d+))?)|( right( (?<right>\\d+))?)|( down( (?<down>\\d+))?)|( left( (?<left>\\d+))?))*$"),
    SHOW_DETAILS("(?=.* -x )(?=.* -y )^show details( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
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
    REJECT_TRADE_REQUEST("(?=.* -m )(?=.* -i )^trade reject(( -i (?<id>((-?\\d*)|(\\\"-?\\d*\\\"))))" +
            "|( -m (?<message>(((\\\"[^\\\"]*\\\")|\\S*))))){2}$"),
    TRADE_LIST("^trade list$"),
    TRADE_HISTORY("^trade history$"),
    SHOW_GOLD("^show gold$"),
    SET_THE_GATE("(?=.* -x )(?=.* -y )^(?<command>(open|close)) the gate( -x (?<x>(\\-)?\\d+)| -y (?<y>(\\-)?\\d+)){2}$"),
    //Soldier Menu Commands
    MOVE_UNITES_WITH_TYPE_SINA("(?=.* -x )(?=.* -y )(?=.* -t )^move unit to( -x (?<x>((-?\\d*)|(\\\"-?\\d*\\\")))| -y (?<y>((-?\\d*)|(\\\"-?\\d*\\\")))| -type (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))){3}$"),
    MOVE_UNITES_WITH_TYPE("(?=.* -x )(?=.* -y )(?=.* -t )^move unit to( -x (?<x>((-?\\d*)|(\\\"-?\\d*\\\")))| -y (?<y>((-?\\d*)|(\\\"-?\\d*\\\")))| -t (?<type>(((\\\"[^\\\"]*\\\")|\\S*)))){3}$"),
    MOVE_UNITES_WITHOUT_TYPE("(?=.* -x )(?=.* -y )^move unit to( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    SHOW_SOLDIERS("^show soldiers$"),
    SHOW_HORSE("^show horses$"),
    PATROL("(?=.* -x1 )(?=.* -y1 )(?=.* -x2 )(?=.* -y2 )^patrol unit( -x1 (?<x1>(-?\\d*)|(\\\"-?\\d*\\\"))| -y1 (?<y1>(-?\\d*)|(\\\"-?\\d*\\\"))| -x2 (?<x2>(-?\\d*)|(\\\"-?\\d*\\\"))| -y2 (?<y2>(-?\\d*)|(\\\"-?\\d*\\\"))){4}$"),
    SET_STATE("(?=.* -x )(?=.* -y )(?=.* -s )^set( -x (?<x>(\\-)?\\d+)| -y (?<y>(\\-)?\\d+)| -s (?<state>(((\\\"[^\\\"]*\\\")|\\S*)))){3}$"),
    POUR_OIL("^pour oil -d (?<direction>[nswer])$"),
    SHOW_BUILDINGS("^show buildings$"),
    STOP_PATROL("^stop patrolling$"),
    DIG_DITCH("(?=.* -x )(?=.* -y )^dig ditch at( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    FILL_DITCH("(?=.* -x )(?=.* -y )^fill the ditch at( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    STOP_DIGGING("^stop digging$"),
    REMOVE_DITCH("(?=.* -x )(?=.* -y )^remove the ditch at( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    DROP_STAIR("(?=.* -x )(?=.* -y )(?=.* -d )^drop stair( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))| -d (?<direction>[nswer])){3}$"),
    DIG_TUNNEL("(?=.* -x )(?=.* -y )^dig tunnel at( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    BUILD_EQUIPMENT("^build equipment -q (?<equipmentName>(((\\\"[^\\\"]*\\\")|\\S*)))$"),
    ATTACK_ENEMY("^attack -e (?<x>((-?\\d*)|(\\\"-?\\d*\\\"))) (?<y>((-?\\d*)|(\\\"-?\\d*\\\")))$"),
    ATTACK("(?=.* -x )(?=.* -y )^attack(( -x (?<x>((-?\\d*)|(\\\"-?\\d*\\\"))))|( -y (?<y>((-?\\d*)|(\\\"-?\\d*\\\"))))){2}$"),
    PUT_LADDER("(?=.* -x )(?=.* -y )^put ladder at( -x (?<x>(-?\\d*)|(\\\"-?\\d*\\\"))| -y (?<y>(-?\\d*)|(\\\"-?\\d*\\\"))){2}$"),
    THROW_LADDER("^throw ladders out"),
    DISBAND("^disband unit$"),
    ;

    private String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, Commands commandRegex) {
        return Pattern.compile(commandRegex.regex).matcher(command);
    }

    private static boolean existFlag(String command, String flag) {
        if (Pattern.compile(" -" + flag + " ").matcher(command).find()) return true;
        return false;
    }
}
