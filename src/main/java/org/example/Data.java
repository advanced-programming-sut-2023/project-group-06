package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Data {
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    static boolean hasAnyOneLoggedInYet = false;

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
            if (chatRoom.getId() == id) return chatRoom;
        }
        return null;
    }
}
