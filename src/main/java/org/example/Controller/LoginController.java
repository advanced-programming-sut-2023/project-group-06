package org.example.Controller;

import org.example.Model.Data;
import org.example.View.Response;

import java.util.regex.Matcher;

public class LoginController {

    public static Response loginUser(Matcher matcher){
        matcher.find();
        boolean stayLoggedIn = false;
        String username = matcher.group("username");
        String password = matcher.group("password");
        if (matcher.group("stayLoggedIn") != null) stayLoggedIn = true;
        if (Data.getUserByName(username) == null) return Response.USERNAME_DOES_NOT_EXIST;
        if (!Data.getUserByName(username).isPasswordCorrect(password)) return Response.WRONG_PASSWORD;
        Data.setStayLoggedIn(stayLoggedIn);
        Data.setCurrentUser(Data.getUserByName(username));
        return Response.LOGIN_SUCCESSFUL;
    }
    public static Response forgotPassword(Matcher matcher, String answer) {
        matcher.find();
        String username = matcher.group("username");
        if (!Data.getUserByName(username).isAnswerToQuestionCorrect(answer)) return Response.WRONG_SECURITY_ANSWER;
        return Response.PASSWORD_CHANGE;
    }

    public static Response changePasswordSuccessful(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) return Response.PASSWORD_CONFIRMATION;
        return Response.PASSWORD_CHANGE;
    }
}
