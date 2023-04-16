package org.example.Controller;

import org.example.Model.Data;
import org.example.View.Response;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StartController{
    public static Response chooseMenuToEnter(String command) {
        //todo import isStayLoggedIn
        if (Data.isStayLoggedIn()) {
            //todo import all info
            return Response.ENTER_MAIN_MENU;
        }
        if (command.equals("y")) return Response.ENTER_LOGIN_MENU;
        if (command.equals("n")) return Response.ENTER_SIGN_UP_MENU;
        return null;
    }
}
