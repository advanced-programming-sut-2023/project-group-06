package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Data {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static boolean stayLoggedIn = false;

    public static User getUserByName(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) return users.get(i);
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) return users.get(i);
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean stayLoggedIn) {
        Data.stayLoggedIn = stayLoggedIn;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static void sortUsersByHighScore() {
        Collections.sort(users);
    }

    public static int getUserRank(User user) {
        sortUsersByHighScore();
        for (int i = 1; i <= users.size(); i++) {
            if (users.get(users.size() - i) == user) return i;
        }
        return -1;
    }
}
