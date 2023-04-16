package org.example.View;

import org.example.Model.User;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private User loggedInUser;

    public Scanner getScanner(){
        //todo
        return null;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public MenuType run(Scanner scanner){
        return null;
        //todo
    }

    public void enterMenu(String newMenu){
        //todo
    }

    public void exitMenu(){
        //todo
    }
}
