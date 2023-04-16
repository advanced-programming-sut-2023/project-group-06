package org.example.Controller;

import org.example.Model.Data;
import org.example.Model.Game;
import org.example.Model.Kingdom;
import org.example.Model.User;
import org.example.View.Response;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainController {
    public static Response startGame(Matcher matcher){
        String username2 = matcher.group("username2");
        String username3 = matcher.group("username3");
        String username4 = matcher.group("username4");
        String username5 = matcher.group("username5");
        String username6 = matcher.group("username6");
        String username7 = matcher.group("username7");
        String username8 = matcher.group("username8");
        //check format
        ArrayList<User> users = new ArrayList<>();
        users.add(Controller.currentUser);
        if(addGamePlayer(users, username2) != null ||
                addGamePlayer(users, username2) != null ||
                addGamePlayer(users, username3) != null ||
                addGamePlayer(users, username4) != null ||
                addGamePlayer(users, username5) != null ||
                addGamePlayer(users, username6) != null ||
                addGamePlayer(users, username7) != null ||
                addGamePlayer(users, username8) != null)
            return Response.USER_NOT_FOUND;
        Game game = new Game(users);
        return Response.GAME_STARTED_SUCCESSFULLY;
    }

    public static String addGamePlayer(ArrayList<User> players, String username){
        if(username == null)
            return null;
        if(Data.getUserByName(username) == null)
            return "failed";
        players.add(Data.getUserByName(username));
        return null;
    }

    public static Response setTextureOneTile(Matcher matcher){
        return null;
        //todo
        //after start game
    }

    public static Response setTextureMultipleTiles(Matcher matcher){
        return null;
        //todo
        //after start game
    }

    public static Response setMapSize(Matcher matcher){
        return null;
        //todo
        //after start game
    }

    public static Response clearBlock(Matcher matcher){
        return null;
        //todo
        //after start game
    }

    public static Response dropRuck(Matcher matcher){
        return null;
        //todo
        //after start game
    }

    public static Response dropTree(Matcher matcher){
        return null;
        //todo
        //after start game
    }

    public static Response enterProfileMenu(){
        return null;
        //todo
    }

    public static Response logout(){
        return null;
        //todo
    }
}
