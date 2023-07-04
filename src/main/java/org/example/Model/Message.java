package org.example.Model;

import com.google.gson.JsonObject;

public class Message{
    private User owner;
    private String content;
    private String time;
    private ChatRoom chatRoom;
    private int id;

    public Message(User owner, String content, String time, ChatRoom chatRoom) {
        this.owner = owner;
        this.content = content;
        this.time = time;
        this.chatRoom = chatRoom;
        id = chatRoom.messageCounter + 1;
        chatRoom.messageCounter++;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("owner", owner.getUsername());
        jsonObject.addProperty("content", content);
        jsonObject.addProperty("time", time);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("chat room id", chatRoom.getId());
        jsonObject.addProperty("chat room type", chatRoom.getChatType().toString().toLowerCase());
        return jsonObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        chatRoom.messageCounter = id;
    }

    public String toString(){
        return this.content;
    }

    public boolean equals(Message message){
        return this.content.equals(message.content) && owner == message.owner && time.equals(message.time)
                && chatRoom == message.chatRoom && id == message.id;
    }
}
