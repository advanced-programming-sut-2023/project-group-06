package org.example;

import org.example.Controller.Controller;
import org.example.Controller.ProfileController;
import org.example.View.LoginMenu;
import org.example.View.Menu;
import org.example.View.SignUpMenu;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.println((new Menu()).getClass().getName());
        Controller.run(scanner);
    }
}