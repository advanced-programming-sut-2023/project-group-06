package org.example.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.Model.Data;
import org.example.Model.User;
import org.example.View.Response;
import org.example.View.SignUpMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    public static Response createUser(Matcher matcher) throws IOException {
        matcher.find();
        String[] groupNames = {"username","nickname","password","email","slogan"};
        String nullGroupName = Controller.nullGroup(matcher,groupNames);
        if (nullGroupName != null) return Response.getEmptyResponseByName(nullGroupName);
        String username = Controller.makeEntryValid(matcher.group("username"));
        String password = Controller.makeEntryValid(matcher.group("password"));
        String passwordConfirmation = Controller.makeEntryValid(matcher.group("passwordConfirmation"));
        String email = Controller.makeEntryValid(matcher.group("email")).toLowerCase();
        String nickname = Controller.makeEntryValid(matcher.group("nickname"));
        String slogan = Controller.makeEntryValid(matcher.group("slogan"));
        if (slogan != null) slogan = Controller.makeEntryValid(slogan);
        else slogan = "";
        if (!Controller.isUsernameValid(username)) return Response.INVALID_USERNAME_FORMAT;
        if (Data.getUserByName(username) != null) return Response.USERNAME_EXISTS;
        if (!Controller.isLongPassword(password)) return Response.SHORT_PASSWORD;
        if (!Controller.containCapitalLetter(password)) return Response.PASSWORD_CAPITAL;
        if (!Controller.containLowercaseLetter(password)) return Response.PASSWORD_LOWER;
        if (!Controller.containNumber(password)) return Response.PASSWORD_NUMBER;
        if (!Controller.containSymbol(password)) return Response.PASSWORD_SYMBOL;
        if (!password.equals(passwordConfirmation)) return Response.PASSWORD_CONFIRMATION;
        if (Data.getUserByEmail(email) != null) return Response.EMAIL_EXISTS;
        if (!Controller.isValidEmail(email)) return Response.INVALID_EMAIL_FORMAT;
        User newUser = new User(username, password, nickname, email, slogan);
        return Response.PICK_SECURITY_QUESTION;
    }
    public static Response securityQuestion(Matcher matcher, String username) throws IOException {
        matcher.find();
        String[] groupNames = {"questionNumber","answer","answerConfirmation"};
        String nullGroup = Controller.nullGroup(matcher,groupNames);
        if (nullGroup != null) return Response.getEmptyResponseByName(nullGroup);
        int questionIndex = Integer.parseInt(Controller.makeEntryValid(matcher.group("questionNumber")));
        if ((questionIndex < 1) || (questionIndex > 3)) return Response.INVALID_QUESTION_NUMBER;
        String answer = Controller.makeEntryValid(matcher.group("answer"));
        String answerConfirmation = Controller.makeEntryValid(matcher.group("answerConfirmation"));
        if (!answer.equals(answerConfirmation)) return Response.ANSWER_CONFIRMATION;
        Data.getUserByName(username).setAnswerToQuestion(DigestUtils.sha256Hex(answer));
        Data.getUserByName(username).setQuestionIndex(questionIndex - 1);
//        String answerToCaptcha = SignUpMenu.getCaptcha(scanner, Controller.getCaptcha());
//        if (!Controller.isCaptchaCorrect(answerToCaptcha)) {
//            Data.removeUser(Data.getUserByName(username));
//            return Response.CAPTCHA_WRONG;
//        }
        Data.saveData("src/main/java/org/example/Model/Data.json");
        System.out.println(Data.getUserByName("sina"));
        return Response.USER_CREATED;
    }

    public static void back(String username) {
        Data.removeUser(Data.getUserByName(username));
    }

    public static String passwordGenerator(){
        int numberOfCapitalLetters = (int) (Math.random() * 3 + 2);
        int numberOfLowercaseLetters = (int) (Math.random() * 3 + 2);
        int numberOfSymbols = (int) (Math.random() * 2 + 1);
        int numberOfNumbers = (int) (Math.random() * 2 + 1);
        char[] validSymbols = "@#$%!&*+.".toCharArray();
        char[] numbers = "0123456789".toCharArray();
        char[] lowercaseLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        ArrayList<Character> passwordLetters = new ArrayList<>();
        for (int i = 0; i < numberOfCapitalLetters; i++) passwordLetters.add(capitalLetters[(int)(Math.random() * 26)]);
        for (int i = 0; i < numberOfSymbols; i++) passwordLetters.add(validSymbols[(int)(Math.random() * 9)]);
        for (int i = 0; i < numberOfNumbers; i++) passwordLetters.add(numbers[(int)(Math.random() * 10)]);
        for (int i = 0; i < numberOfLowercaseLetters; i++) passwordLetters.add(lowercaseLetters[(int)(Math.random() * 26)]);
        Collections.shuffle(passwordLetters);
        String randomPassword = "";
        for (int i = 0; i < passwordLetters.size(); i++) {
            randomPassword += passwordLetters.get(i);
        }
        return randomPassword;
    }

}