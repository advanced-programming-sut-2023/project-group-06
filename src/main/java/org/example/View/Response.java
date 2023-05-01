package org.example.View;

public enum Response {
    INVALID_INPUT("Invalid input!"),
    THERE_IS_NO_BUILDING_HERE("There is no building here!"),
    YOU_CANT_SELECT_THIS_BUILDING("You can't select this building!"),
    SELECT_BUILDING_SUCCESSFUL("Select building successful!"),
    INVALID_GROUND("The ground is not proper!"),
    BUILDING_ALREADY_EXIST("A building already exist here!"),
    DROP_BUILDING_SUCCESSFUL("Drop building successful!"),
    DROP_TREE_SUCCESSFUL("Drop tree successful!"),
    INITIALIZE_MAP_SUCCESSFUL("Map initialized successfully!"),
    SET_TEXTURE_SUCCESSFUL("Set texture successful!"),
    POPULATION_EXCEEDED("Population exceeded!"),
    TEXTURE_UNDER_UNIT("You can't change the ground under a unit!"),
    NOT_ENOUGH_MONEY("You don't have enough money to build this building!"),
    CANT_PLAY_ALONE("You can't play alone, choose some opponents!"),
    NO_REPEATED_NAME("You can't repeat a name!"),
    NO_SUCH_MAP("There is no default map with this name!"),
    MAP_IS_SET("Map is set!"),
    PUT_MAIN_CASTLE("Put your main castle first!"),
    ENTER_DIRECTION("Enter building's direction!"),
    CANT_PUT_STOCKPILE("There is nowhere to put your stockpile near the main castle!"),
    NO_DIRECTION("This building doesn't have a direction!"),
    ADJACENT_STORAGES("You must put storage, adjacent to other storages!"),
    DROP_MAIN_CASTLE_SUCCESSFUL("Main castle put successfully!"),
    GAME_STARTED_SUCCESSFULLY("Game started successfully!"),
    CLEAR_SUCCESSFUL("Clear block successful!"),
    CLEAR_MAIN_CASTLE("You can't remove your main castle!"),
    USER_NOT_FOUND("User not found!"),
    INVALID_COMMAND("Invalid command!"),
    EMPTY_EMAIL("Email field can't be empty!"),
    EMPTY_USERNAME("Username field can't be empty!"),
    EMPTY_PASSWORD("Password field can't be empty!"),
    EMPTY_NICKNAME("Nickname field can't be empty!"),
    EMPTY_SLOGAN("Slogan field can't be empty!"),
    EMPTY_QUESTION_NUMBER("Question number field can't be empty!"),
    EMPTY_ANSWER("Answer field can't be empty!"),
    EMPTY_CONFIRMATION("Confirmation field can't be empty!"),
    INVALID_USERNAME_FORMAT("Invalid username format!"),
    USERNAME_EXISTS("An account with this username already exists!"),
    SHORT_PASSWORD("Password is short!"),
    PASSWORD_CAPITAL("Password must contain at least one capital letter!"),
    PASSWORD_LOWER("Password must contain at least one lowercase letter!"),
    PASSWORD_NUMBER("Password must contain at least one number!"),
    PASSWORD_SYMBOL("Password must contain at least one symbol!"),
    PASSWORD_CONFIRMATION("Password confirmation failed!"),
    EMAIL_EXISTS("An account with this email already exists!"),
    INVALID_EMAIL_FORMAT("Invalid email format!"),
    PICK_SECURITY_QUESTION("Pick your security question:" + '\n' +
            "1. What is my father's name?" + '\n' +
            "2. What is my first pet's name?" + '\n' +
            "3. What is my mother's last name?"),
    INVALID_QUESTION_NUMBER("Invalid question number!"),
    ANSWER_CONFIRMATION("Answer confirmation failed, try again!"),
    USER_CREATED("User created successfully!"),
    ENTER_MAIN_MENU("Entered main menu successfully!"),
    ENTER_SIGN_UP_MENU("Entered sign up menu!"),
    ENTER_LOGIN_MENU("Entered login menu successfully!"),
    ENTER_MAP_MENU("Entered map menu!"),
    ENTER_TRADE_MENU("Entered trade menu!"),
    ENTER_SHOP_MENU("Entered shop menu!"),
    ENTER_KINGDOM_MENU("Entered kingdom menu!"),
    USERNAME_DOES_NOT_EXIST("No user with this id exists!"),
    WRONG_PASSWORD("Incorrect password!"),
    LOGIN_SUCCESSFUL("Logged in successfully!"),
    LOGOUT_SUCCESSFUL("Logged out successfully!"),
    WRONG_SECURITY_ANSWER("Wrong answer!"),
    PASSWORD_CHANGE("Password changed successfully!"),
    USERNAME_CHANGE("Username changed successfully!"),
    EMAIL_CHANGE("Email changed successfully!"),
    SLOGAN_CHANGE("Slogan changed successfully!"),
    NICKNAME_CHANGE("Nickname changed successfully!"),
    SAME_USERNAME("Please enter a new username!"),
    SAME_PASSWORD("Please enter a new password!"),
    SAME_NICKNAME("Please enter a new password!"),
    SAME_EMAIL("Please enter a new password!"),
    SAME_SLOGAN("Please enter a new password!"),
    INCORRECT_OLD_PASSWORD("Current password is incorrect!"),

    RE_ENTER_PASSWORD("Please re-enter your password!"),
    SLOGAN_REMOVE("Slogan removed successfully!"),
    SLOGAN_ALREADY_NULL("This user has no slogan!"),
    SLOGAN_IS_EMPTY("Slogan is empty!"),
    ENTER_PROFILE_MENU("Entered profile menu successfully!"),
    FOOD_RATE_NUMBER_INVALID("Rate number is invalid!"),
    SET_FOOD_RATE_SUCCESSFUL("Food rate is set successfully!"),
    TAX_RATE_NUMBER_INVALID("Rate number is invalid!"),
    SET_TAX_RATE_SUCCESSFUL("Tax rate is set successfully!"),
    OUT_OF_FOOD("You're out of food , you can't set food rate!"),
    OUT_OF_MONEY("You have no money , you can't set tax rate!"),
    FEAR_RATE_NUMBER_INVALID("Rate number is invalid!"),
    SET_FEAR_RATE_SUCCESSFUL("Fear rate is set successfully!"),
    SET_TEXTURE_UNDER_BUILDING("You can't change the ground under a building!"),
    INVALID_TYPE("Invalid type!"),
    INVALID_COORDINATES("Invalid coordinates!"),
    NOT_ENOUGH_RESOURCES("You don't have enough resource to build this building!"),
    NOT_ENOUGH_ENGINEERS("You don't have enough engineers to build this building!"),
    CREATION_INTERRUPTION("User creation interrupted!"),
    TRY_AGAIN_LATER("Try again later in %d seconds!"),
    CAPTCHA_WRONG("Captcha is wrong!"),
    EMPTY_MAP_NAME("Map name can't be empty!"),
    SAVE_MAP_SUCCESSFUL("Map saved successfully!"),
    NEXT_TURN("turn %d : %s is now playing\n"),
    EMPTY_TYPE("type field can't be empty!"),
    EMPTY_COUNT("count field can't be empty!"),
    INVALID_SOLDIER_TYPE("Invalid soldier type!"),
    INVALID_COUNT("Invalid count!"),
    NOT_ENOUGH_GOLD_UNIT("You don't have enough gold to create this unit!"),
    NOT_ENOUGH_RESOURCE_UNIT("You don't have enough resources to create this unit!"),
    NOT_ENOUGH_WEAPON_UNIT("You don't have enough weapon to create this unit!"),
    NOT_ENOUGH_PEASANT("You don't have enough peasants!"),
    CANT_CREATE_UNIT_IN_BUILDING("This unit can't be created in this building!"),
    UNIT_CREATED_SUCCESSFULLY("Unit created successfully!"),
    CANT_CREATE_ANY_UNIT_IN_BUILDING("You can't create any unit in this building!"),
    EXIST_ENEMY_NEAR_TILE("There is an enemy on or near this building, you can't repair it"),
    NOT_REPAIRABLE("You can't repair this building!"),
    FULL_HIT_POINT("Hit point is already full!"),
    REPAIRED("Building repaired successfully!"),
    CLOSE_BUILDING_MENU("Building menu closed!"),
    CLOSE_SOLDIER_MENU("Soldier menu closed!"),
    INVALID_WEAPON_TYPE("Invalid weapon type!"),
    NOT_ENOUGH_RESOURCES_WEAPON("You don't have enough resources to create this amount of weapons!"),
    CANT_CREATE_ANY_WEAPON_BUILDING("You can't create any weapon in this building!"),
    NO_SOLDIER_ON_THE_TILE("You have no soldier on this tile!"),
    SELECT_SOLDIER_SUCCESSFUL("Select soldier successful!"),
    WEAPON_BUILDING_MISMATCHING("You can't create this weapon in this building!"),
    WEAPON_CREATED("Weapon created successfully!"),
    CLOSE_SHOP_MENU("Shop menu closed!"),
    INVALID_RESOURCE_TYPE("Invalid resource type!"),
    NOT_ENOUGH_STORAGE("You don't have the necessary storage to do this!"),
    NOT_ENOUGH_GOLD_BUY("You don't have enough gold to buy this!"),
    BUY("You bought those items successfully!"),
    NOT_THIS_MUCH_RESOURCES("You don't have this much resources to sell!"),
    SELL("You sold these items successfully!"),
    CLOSE_TRADE_MENU("Trade menu closed!"),
    EMPTY_RESOURCE_TYPE("Resource type field can't be empty!"),
    EMPTY_RESOURCE_AMOUNT("Resource amount field can't be empty!"),
    EMPTY_PRICE("Price field can't be empty!"),
    EMPTY_MESSAGE("Message field can't be empty!"),
    INVALID_PRICE("Invalid price!"),
    INVALID_AMOUNT("Invalid amount!"),
    TRADE_REQUEST_CREATED("Trade request successfully sent!"),
    EMPTY_ID("Id field can't be empty!"),
    TRADE_REQUEST_NOT_SENT("No trade request with this id has been sent to you!"),
    NOT_ENOUGH_GOLD_TRADE("You don't have enough gold to send this trade!"),
    NOT_ENOUGH_RESOURCE_TRADE("You don't have enough resources to accept this trade!"),
    TRADE_REQUEST_OWNER("You can't accept a trade request from yourself!"),
    TRADE_REQUEST_ACCEPTED("You accepted this trade request successfully!"),
    NO_KINGDOM_USERNAME("No kingdom exists with this username!"),
    KINGDOM_YOURSELF("You can't send a trade to yourself!"),
    GATE_OPEN("The gate is opened!"),
    GATE_CLOSE("The gate is closed!"),
    CANT_OPEN("Can't open the gate!"),
    CANT_CLOSE("Can't close the gate!"),
    ALREADY_OPEN("The gate is already opened!"),
    ALREADY_CLOSE("The gate is already closed!"),
    NO_GATE_HERE("There is no gate here!"),
    MOVE_SUCCESSFUL("Move successful!"),
    CANT_GO_THERE("The unit can't go there!"),
    NO_UNITS_WITH_THAT_TYPE("There are no units with that type here!"),
    NOT_ENOUGH_HORSES("You don't have enough horses to create these units!"),
    NOT_ENOUGH_SPACE("You don't have enough space to do this!"),
    NOT_ENOUGH_OIL("You don't have enough oil  to do this!"),
    INAPPROPRIATE_UNIT("You can't do this action with this unit!"),
    EMPTY_DIRECTION("Direction field can't be empty!"),
    OUT_OF_BOUNDARIES("This direction is out of boundaries!"),
    POUR_OIL("Poured oil successfully!"),
    NO_OIL("None of the soldiers have oil to pour!"),
    CLOSE_THE_GATE_FIRST("Close The gate first!"),
    CANT_PUT_THIS_ON_TROOPS("You can't put this on units!"),
    DROP_ROCK_SUCCESSFUL("Drop rock successful!"),
    PATROL_SUCCESSFUL("The unit will patrol between the places!"),
    NO_ARMED_OIL_ENGINEER("No armed oil engineer exists in this tile!"),
    ;
    public final String message;

    private Response(String message) {
        this.message = message;
    }
    public static Response getEmptyResponseByName(String name) {
        if (name.equals("email")) return EMPTY_EMAIL;
        if (name.equals("username")) return EMPTY_USERNAME;
        if (name.equals("password")) return EMPTY_PASSWORD;
        if (name.equals("nickname")) return EMPTY_NICKNAME;
        if (name.equals("slogan")) return EMPTY_SLOGAN;
        if (name.equals("questionNumber")) return EMPTY_QUESTION_NUMBER;
        if (name.equals("answer")) return EMPTY_ANSWER;
        if (name.equals("answerConfirmation")) return EMPTY_CONFIRMATION;
        if (name.equals("type")) return EMPTY_TYPE;
        if (name.equals("count")) return EMPTY_COUNT;
        if (name.equals("message")) return EMPTY_MESSAGE;
        if (name.equals("price")) return EMPTY_PRICE;
        if (name.equals("resourceAmount")) return EMPTY_RESOURCE_AMOUNT;
        if (name.equals("resourceType")) return EMPTY_RESOURCE_TYPE;
        if (name.equals("id")) return EMPTY_ID;
        if (name.equals("direction")) return EMPTY_DIRECTION;
        return null;
    }
}
