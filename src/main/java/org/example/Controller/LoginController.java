package org.example.Controller;

import org.example.Model.Data;
import org.example.View.Response;

import java.util.regex.Matcher;

public class LoginController {

    public static Response loginUser(Matcher matcher){
        matcher.find();
        String[] groupNames = {"username","password"};
        String nullGroup = Controller.nullGroup(matcher,groupNames);
        if (nullGroup != null) return Response.getEmptyResponseByName(nullGroup);
        boolean stayLoggedIn = false;
        String username = Controller.makeEntryValid(matcher.group("username"));
        String password = Controller.makeEntryValid(matcher.group("password"));
        if (matcher.group("stayLoggedIn") != null) stayLoggedIn = true;
        if (Data.getUserByName(username) == null) return Response.USERNAME_DOES_NOT_EXIST;
        //todo multiple wrong passwords will lead to some screen lock!
        if (!Data.getUserByName(username).isPasswordCorrect(password)) return Response.WRONG_PASSWORD;
        Data.setStayLoggedIn(stayLoggedIn);
        Data.setCurrentUser(Data.getUserByName(username));
        Data.saveData("src/main/java/org/example/Model/data.json");
        return Response.LOGIN_SUCCESSFUL;
    }
    public static Response forgotPassword(Matcher matcher, String answer) {
        matcher.find();
        String [] groupNames = {"username"};
        String nullGroup = Controller.nullGroup(matcher,groupNames);
        if (nullGroup != null) return Response.getEmptyResponseByName(nullGroup);
        String username = Controller.makeEntryValid(matcher.group("username"));
        if (!Data.getUserByName(username).isAnswerToQuestionCorrect(answer)) return Response.WRONG_SECURITY_ANSWER;
        return Response.PASSWORD_CHANGE;
    }

    public static Response isPasswordStrong (String password) {
        if (!Controller.isLongPassword(password)) return Response.SHORT_PASSWORD;
        if (!Controller.containCapitalLetter(password)) return Response.PASSWORD_CAPITAL;
        if (!Controller.containLowercaseLetter(password)) return Response.PASSWORD_LOWER;
        if (!Controller.containNumber(password)) return Response.PASSWORD_NUMBER;
        if (!Controller.containSymbol(password)) return Response.PASSWORD_SYMBOL;
        return null;
    }
    public static Response changePasswordSuccessful(String username, String answer, String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) return Response.PASSWORD_CONFIRMATION;
        Data.getUserByName(username).changePasswordBySecurityQuestion(answer,password);
        return Response.PASSWORD_CHANGE;
    }

    public static void resetLoginAttempts (String username) {
        Data.getUserByName(username).setNumberOfLoginAttempts(0);
    }
    public static int getNumberOfLoginAttempts(String username) {
        return Data.getUserByName(username).getNumberOfLoginAttempts();
    }
    public static long getLastLoginAttempt (String username) {
        return Data.getUserByName(username).getLastLoginAttemptTime();
    }
    public static boolean setLastLoginAttempt (String username, long currentTime) {
        if ((currentTime - Data.getUserByName(username).getLastLoginAttemptTime())
                < (Data.getUserByName(username).getNumberOfLoginAttempts() * 5)) {
            return false;
        }
        Data.getUserByName(username).setLastLoginAttemptTime(currentTime);
        int loginAttempts = Data.getUserByName(username).getNumberOfLoginAttempts();
        Data.getUserByName(username).setNumberOfLoginAttempts(loginAttempts + 1);
        return true;
    }
}
