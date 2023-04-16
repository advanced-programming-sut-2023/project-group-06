package org.example;

import org.example.Controller.Controller;
import org.example.View.LoginMenu;
import org.example.View.SignUpMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Controller.run(scanner);
    }
}