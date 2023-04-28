package org.example.Controller;

import org.example.Controller.GameControllers.GameController;
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
        String username2 = Controller.makeEntryValid(matcher.group("username2"));
        String username3 = Controller.makeEntryValid(matcher.group("username3"));
        String username4 = Controller.makeEntryValid(matcher.group("username4"));
        String username5 = Controller.makeEntryValid(matcher.group("username5"));
        String username6 = Controller.makeEntryValid(matcher.group("username6"));
        String username7 = Controller.makeEntryValid(matcher.group("username7"));
        String username8 = Controller.makeEntryValid(matcher.group("username8"));
        //check format
        ArrayList<User> users = new ArrayList<>();
        users.add(Data.getCurrentUser());
        if(addGamePlayer(users, username2) != null ||
                addGamePlayer(users, username3) != null ||
                addGamePlayer(users, username4) != null ||
                addGamePlayer(users, username5) != null ||
                addGamePlayer(users, username6) != null ||
                addGamePlayer(users, username7) != null ||
                addGamePlayer(users, username8) != null)
            return Response.USER_NOT_FOUND;
        if(users.size() == 1)
            return Response.CANT_PLAY_ALONE;
        for(int i = 1; i < users.size(); i++){
            for(int j = 0; j < i; j++)
                if(users.get(i) == users.get(j))
                    return Response.NO_REPEATED_NAME;
        }
        Game game = new Game(users);
        GameController.currentGame = game;
        GameController.currentPlayer = game.currentPlayer();
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
}
