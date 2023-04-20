package org.example.View;

public enum Response {
    INVALID_GROUND("The ground is not proper!"),
    BUILDING_ALREADY_EXIST("A building already exist here!"),
    DROP_BUILDING_SUCCESSFUL("Drop building successful!"),
    DROP_TREE_SUCCESSFUL("Drop tree successful!"),
    INITIALIZE_MAP_SUCCESSFUL("Map initialized successfully!"),
    SET_TEXTURE_SUCCESSFUL("Set texture successful!"),
    POPULATION_EXCEEDED("Population exceeded!"),
    NOT_ENOUGH_MONEY("You don't have enough money to build this building!"),
    CANT_PLAY_ALONE("You can't play alone, choose some opponents!"),
    PUT_MAIN_CASTLE("Put your main castle first!"),
    DROP_MAIN_CASTLE_SUCCESSFUL("Main castle put successfully!"),
    GAME_STARTED_SUCCESSFULLY("Game started successfully!"),
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
    NEXT_TURN("next person is now playing"),
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
    CLOSE_BUILDING_MENU("Closed building menu!"),

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
        return null;
    }
}
