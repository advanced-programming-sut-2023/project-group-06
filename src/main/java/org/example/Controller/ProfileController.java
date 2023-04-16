package org.example.Controller;

import org.example.Main;
import org.example.Model.Data;
import org.example.Model.User;
import org.example.View.Response;

import java.util.regex.Matcher;

public class ProfileController {

    public static Response changeUsername(Matcher matcher){
        String username = matcher.group("username");
        if (!Controller.isUsernameValid(username)) return Response.INVALID_USERNAME_FORMAT;
        if (Data.getCurrentUser().getUsername().equals(username)) return Response.SAME_USERNAME;
        if (Data.getUserByName(username) != null) return Response.USERNAME_EXISTS;
        Data.getCurrentUser().setUsername(username);
        return Response.USERNAME_CHANGE;
    }

    public static Response changeNickname(Matcher matcher){
        String nickname = matcher.group("nickname");
        if (Data.getCurrentUser().getNickname().equals(nickname)) return Response.SAME_NICKNAME;
        Data.getCurrentUser().setNickname(nickname);
        return Response.NICKNAME_CHANGE;
    }

    public static Response changePassword(Matcher matcher){
        String newPassword = matcher.group("newPassword");
        String oldPassword = matcher.group("oldPassword");
        if (!Data.getCurrentUser().changePassword(newPassword,oldPassword)) return Response.INCORRECT_OLD_PASSWORD;
        if (oldPassword.equals(newPassword)) return Response.SAME_PASSWORD;
        if (!Controller.isLongPassword(newPassword)) return Response.SHORT_PASSWORD;
        if (!Controller.containCapitalLetter(newPassword)) return Response.PASSWORD_CAPITAL;
        if (!Controller.containLowercaseLetter(newPassword)) return Response.PASSWORD_LOWER;
        if (!Controller.containNumber(newPassword)) return Response.PASSWORD_NUMBER;
        if (!Controller.containSymbol(newPassword)) return Response.PASSWORD_SYMBOL;
        return Response.RE_ENTER_PASSWORD;
    }

    public static Response confirmReEnteredPassword(String oldPassword, String newPassword, String reEnteredPassword) {
        if (!newPassword.equals(reEnteredPassword)) return Response.PASSWORD_CONFIRMATION;
        Data.getCurrentUser().changePassword(newPassword,oldPassword);
        return Response.PASSWORD_CHANGE;
    }

    public static Response changeEmail(Matcher matcher){
        String email = matcher.group("email").toLowerCase();
        if (Data.getCurrentUser().getEmail().equals(email)) return Response.SAME_EMAIL;
        if ((Data.getUserByEmail(email) != null)) return Response.EMAIL_EXISTS;
        if (!Controller.isValidEmail(email)) return Response.INVALID_EMAIL_FORMAT;
        Data.getCurrentUser().setEmail(email);
        return Response.EMAIL_CHANGE;
    }

    public static Response changeSlogan(Matcher matcher){
        String slogan = matcher.group("slogan");
        if ((Data.getCurrentUser().getSlogan() != null) && Data.getCurrentUser().getSlogan().equals(slogan)) return Response.SAME_SLOGAN;
        Data.getCurrentUser().setSlogan(slogan);
        return Response.SLOGAN_CHANGE;
    }

    public static Response removeSlogan() {
        if (Data.getCurrentUser().getSlogan() == null) return Response.SLOGAN_ALREADY_NULL;
        Data.getCurrentUser().setSlogan(null);
        return Response.SLOGAN_REMOVE;
    }

    public static int showHighScore() {
        return Data.getCurrentUser().getHighScore();
    }

    public static int showRank() {
        return Data.getCurrentUser().getRank();
    }

    public static String showSlogan() {
        if (Data.getCurrentUser().getSlogan() == null) return Response.SLOGAN_IS_EMPTY.message;
        return Data.getCurrentUser().getSlogan();
    }

    public static String showInfo(){
        return "username: " + Data.getCurrentUser().getUsername() + '\n' +
                "password: " + Data.getCurrentUser().getPassword() + '\n' +
                "nickname: " + Data.getCurrentUser().getNickname() + '\n' +
                "email: " + Data.getCurrentUser().getEmail() + '\n' +
                "slogan: " + Data.getCurrentUser().getSlogan();
    }
}
