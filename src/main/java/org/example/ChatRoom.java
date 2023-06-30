package org.example;

import java.util.ArrayList;

public class ChatRoom {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatType chatType;


    public ChatRoom(ArrayList<User> users, ChatType chatType) {
        this.users = users;
        this.chatType = chatType;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ChatType getChatType() {
        return chatType;
    }
}
