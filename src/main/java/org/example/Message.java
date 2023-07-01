package org.example;

import com.google.gson.JsonObject;

public class Message {
    private String owner;
    private String content;
    private String time;
    private ChatRoom chatRoom;
    int id;

    public Message(String owner, String content, String time, ChatRoom chatRoom, int id) {
        this.owner = owner;
        this.content = content;
        this.time = time;
        this.chatRoom = chatRoom;
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public int getId() {
        return id;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("owner", owner);
        jsonObject.addProperty("content", content);
        jsonObject.addProperty("time", time);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("chat room id", chatRoom.getId());
        return jsonObject;
    }
}
