package org.example.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

public class UserThread extends Thread {
    User user;
    @Override
    public void run() {
        while (true) {
            try {
                updateData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateData() throws IOException {
        if (user.getClient() == null) return;
        String input = user.getClient().dataInputStream.readUTF();
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(input);
        JsonArray chatRooms = json.get("chat rooms").getAsJsonArray();
        for (JsonElement roomElement : chatRooms) {
            JsonObject roomObject = roomElement.getAsJsonObject();
            JsonArray users = roomObject.get("users").getAsJsonArray();
            ArrayList<User> users1 = new ArrayList<>();
            boolean isMember = false;
            for (JsonElement userElement : users) {
                JsonObject userObj = userElement.getAsJsonObject();
                String username = userObj.get("username").getAsString();
                if (username.equals(user.getUsername())) isMember = true;
                users1.add(Data.getUserByName(username));
            }
            if (isMember) {
                int id = roomObject.get("id").getAsInt();
                String name = roomObject.get("name").getAsString();
                ChatType chatType = ChatType.getChatTypeByString(roomObject.get("chat type").getAsString());
                ChatRoom chatRoom = user.getChatWithId(id);
                if (chatRoom == null) {
                    chatRoom = new ChatRoom(users1, chatType);
                    chatRoom.setId(id);
                    chatRoom.setName(name);
                }
                ArrayList<Message> messages = new ArrayList<>();
                JsonArray messagesJson = roomObject.get("messages").getAsJsonArray();
                for (JsonElement jsonElement : messagesJson) {
                    JsonObject messageObject = jsonElement.getAsJsonObject();
                    User owner = Data.getUserByName(messageObject.get("owner").getAsString());
                    String content = messageObject.get("content").getAsString();
                    String time = messageObject.get("time").getAsString();
                    int messageId = messageObject.get("id").getAsInt();
                    Message message = new Message(owner, content, time, chatRoom);
                    messages.add(message);
                    message.setId(messageId);

                }
            }
        }
    }

    public UserThread(User user) {
        this.user = user;
    }
}
