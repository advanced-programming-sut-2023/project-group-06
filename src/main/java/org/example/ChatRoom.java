package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ChatRoom {
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatType chatType;
    private String name;
    private int id;


    public ChatRoom(ArrayList<String> usernames, ChatType chatType, String name, int id) {
        this.usernames = usernames;
        this.chatType = chatType;
        this.name = name;
        this.id = id;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public Message getMessageById(int id) {
        for (Message message : messages) {
            if (message.getId() == id) return message;
        }
        return null;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (String user : usernames) {
            jsonArray.add(user);
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
}
