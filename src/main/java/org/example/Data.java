package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Data {
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    static boolean hasAnyOneLoggedInYet = false;
    private static ArrayList<Game> allWaitingGames = new ArrayList<>();
    private static ArrayList<String> mapNames = new ArrayList<>();
    static {
        for (int i = 0; i < 10; i++) {
            mapNames.add("map test" + i);
        }
        System.out.println("_+_" + mapNames);
    }

    public static Client getClientByName(String username) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getUsername().equals(username)) return clients.get(i);
        }
        return null;
    }

    public static Client getClientByEmail(String email) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getEmail().equals(email)) return clients.get(i);
        }
        return null;
    }

    public static void addClient(Client client) {
        clients.add(client);
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static void removeClient(Client client) {
        clients.remove(client);
    }

    public static void sortUsersByHighScore() {
        Collections.sort(clients);
    }

    public static void setClients(ArrayList<Client> clients) {
        Data.clients = clients;
    }

    public static ArrayList<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public static void setChatRooms(ArrayList<ChatRoom> chatRooms) {
        Data.chatRooms = chatRooms;
    }

    public static ChatRoom getChatRoomById(int id) {
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getId() == id && chatRoom.getChatType() != ChatType.GAME) return chatRoom;
        }
        return null;
    }

    public static ChatRoom getGameRoomById(int id) {
        if (getGameById(id) == null) return null;
        return getGameById(id).getChatRoom();
    }

    public static void addWaitingGame(Game waitingGame) {
        Data.allWaitingGames.add(waitingGame);
    }

    public synchronized static ArrayList<Game> getAllWaitingGames() {
        return allWaitingGames;
    }

    public static Game getGameById(int id) {
        synchronized (allWaitingGames) {
            for (int i = allWaitingGames.size() - 1; i >= 0; i--) {
                Game allWaitingGame = allWaitingGames.get(i);
                if (allWaitingGame.getId() == id) return allWaitingGame;
            }
            return null;
        }
    }

    public static void removeGame(Game game) {
        synchronized (allWaitingGames) {
            allWaitingGames.remove(game);
        }
    }

    public static ArrayList<String> getMapNames() {
        return mapNames;
    }

    public synchronized static void addMapName(String mapName) {
        synchronized (mapName) {
            Data.mapNames.add(mapName);
        }
    }

    /* saveMap
    true: everything is ok
    false: error
    map will be saved in "src/main/java/org/example/Model/Maps/[fileName].bin" */
}
