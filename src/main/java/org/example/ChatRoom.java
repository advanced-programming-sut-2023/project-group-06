package org.example;

import java.util.ArrayList;

public class ChatRoom {
    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatType chatType;


    public ChatRoom(ArrayList<Client> clients, ChatType chatType) {
        this.clients = clients;
        this.chatType = chatType;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
    }

    public ArrayList<Client> getUsers() {
        return clients;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ChatType getChatType() {
        return chatType;
    }
}
