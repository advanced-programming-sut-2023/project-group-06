package org.example.Controller;

import org.example.Model.Data;
import org.example.Model.User;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StartController{
    public static boolean isStayLoggedIn() {
        /*todo import stayLoggedIn
        ** set current user and login directly
         */
        return false;
    }
    public static Response chooseMenuToEnter(String command) {
        if (command.equals("y")) return Response.ENTER_LOGIN_MENU;
        if (command.equals("n")) return Response.ENTER_SIGN_UP_MENU;
        return null;
    }
}
