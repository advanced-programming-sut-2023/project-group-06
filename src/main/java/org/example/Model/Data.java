package org.example.Model;

import java.util.ArrayList;

public class Data {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
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

    public static void sortUsersByHighScore() {
        //todo
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static int getUserRank(User user){
        sortUsersByHighScore();
        return 0;
        //todo
    }
}
