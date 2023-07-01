package org.example.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

public class ChatRoom{
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatType chatType;
    private String name;
    private int id;
    private static int count = 0;
    int messageCounter = 0;

    public ChatRoom(ArrayList<User> users, ChatType chatType) {
        this.users = users;
        this.chatType = chatType;
        id = ++count;
    }

    public ChatRoom(ArrayList<User> users, String name){
        this.users = users;
        this.name = name;
        this.chatType = ChatType.ROOM;
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

    public int getId() {
        return id;
    }

    public boolean hasSomeOne(String username){
        for(User user : users) {
            if(Objects.equals(user.getUsername(), username))
                return true;
        }
        return false;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (User user : users) {
            JsonObject userJson = user.toJson();
            jsonArray.add(userJson);
        }
        jsonObject.add("users", jsonArray);
        JsonArray messagesJson = new JsonArray();
        for (Message message : messages) {
            messagesJson.add(message.toJson());
        }
        jsonObject.add("messages", messagesJson);
        jsonObject.addProperty("chat type", chatType.toString().toLowerCase());
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("id", id);
        return jsonObject;
    }

    public void setId(int id) {
        this.id = id;
        count = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
