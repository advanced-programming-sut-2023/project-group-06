package org.example;

public class Message {
    private User owner;
    private String content;
    private String time;
    private ChatRoom chatRoom;

    public Message(User owner, String content, String time, ChatRoom chatRoom) {
        this.owner = owner;
        this.content = content;
        this.time = time;
        this.chatRoom = chatRoom;
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
}
