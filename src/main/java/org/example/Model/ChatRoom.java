package org.example.Model;

import java.util.ArrayList;
import java.util.Objects;

public class ChatRoom{
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatType chatType;
    private String name;

    public ChatRoom(ArrayList<User> users, ChatType chatType) {
        this.users = users;
        this.chatType = chatType;
        for(User user : users) user.getChats().add(this);
    }

    public ChatRoom(ArrayList<User> users, String name){
        this.users = users;
        this.name = name;
        this.chatType = ChatType.ROOM;
        for(User user : users) user.getChats().add(this);
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

    public String getName(){
        return name;
    }

    public boolean hasSomeOne(String username){
        for(User user : users) {
            if(Objects.equals(user.getUsername(), username))
                return true;
        }
        return false;
    }
}
