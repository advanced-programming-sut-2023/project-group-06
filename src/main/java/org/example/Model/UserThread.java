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
        try {
            String bool = user.getClient().dataInputStream.readUTF();
            user.sendToServer();
            if (!bool.equals("true")) {
                user.createRoom(Data.getPublicRoom());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (user.getClient() != null && user.getClient().isClientActive == true) {
            try {
                updateData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateData() throws IOException {
        if (user.getClient() == null) return;
        String input = user.getClient().dataInputStream.readUTF();
        if (input.equals("salam")) return;
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(input);
        JsonArray chatRooms = json.get("chat rooms").getAsJsonArray();
        JsonArray notRespondedFriendRequestsReceivedByMe = json.get("friend requests received by me").getAsJsonArray();
        JsonArray friends = json.get("friends").getAsJsonArray();
        JsonArray clients = json.get("all clients").getAsJsonArray();
        JsonArray allWaitingGames = json.get("all waiting games").getAsJsonArray();
        handleChatRoom(chatRooms);
        handleFriendRequests(notRespondedFriendRequestsReceivedByMe);
        handleFriends(friends);
        handleClients(clients);
        handleWaitingGames(allWaitingGames);
    }

    private void handleWaitingGames(JsonArray allWaitingGames) {
        ArrayList<WaitingGame> waitingGames = new ArrayList<>();
        for (JsonElement waitingGame : allWaitingGames) {
            JsonObject gameObj = waitingGame.getAsJsonObject();
//            long startTime;
//            int capacity;
//            ArrayList<Client> players = new ArrayList<>();
//            int id;
//            Client admin;
            boolean isPublic = gameObj.get("is public").getAsBoolean();
            long startTime = gameObj.get("start time").getAsLong();
            int capacity = gameObj.get("capacity").getAsInt();
            ArrayList<User> players = new ArrayList<>();
            JsonArray playersObj = gameObj.get("players").getAsJsonArray();
            for (JsonElement jsonElement : playersObj) {
                String username = jsonElement.getAsJsonObject().getAsString();
                players.add(Data.getUserByName(username));
            }
            int id = gameObj.get("id").getAsInt();
            User admin = Data.getUserByName(gameObj.get("admin").getAsString());
            waitingGames.add(new WaitingGame(id, players, capacity, admin, startTime, isPublic));
        }
        user.setAllWaitingGames(waitingGames);
    }

    private void handleClients(JsonArray clients) {
        ArrayList<String> users = new ArrayList<>();
        for (JsonElement client : clients) {
            JsonObject clientObj = client.getAsJsonObject();
            String clientUsername = clientObj.get("name").getAsString();
            users.add(clientUsername);
        }
        user.setAllClients(users);
    }

    private void handleFriends(JsonArray friends) {
        ArrayList<User> myFriends = new ArrayList<>();
        user.setOnline(true);
        for (JsonElement friend : friends) {
            JsonObject friendObject = friend.getAsJsonObject();
            String username = friendObject.get("username").getAsString();
            long lastSeen = friendObject.get("last seen").getAsLong();
            boolean isOnline = friendObject.get("is online").getAsBoolean();
            User user1 = Data.getUserByName(username);
            user1.setOnline(isOnline);
            user1.setLastSeen(lastSeen);
            myFriends.add(user1);
        }
        user.setMyFriends(myFriends);
    }

    private void handleFriendRequests(JsonArray notRespondedFriendRequestsReceivedByMe) {
        ArrayList<FriendRequest> friendRequests = new ArrayList<>();
        for (JsonElement jsonElement : notRespondedFriendRequestsReceivedByMe) {
            JsonObject friendReqObj = jsonElement.getAsJsonObject();
            String senderName = friendReqObj.get("name").getAsString();
            friendRequests.add(new FriendRequest(Data.getUserByName(senderName), user));
        }
        user.setFriendRequestsReceivedByMe(friendRequests);
    }

    private void handleChatRoom(JsonArray chatRooms) throws IOException {
        for (JsonElement roomElement : chatRooms) {
            JsonObject roomObject = roomElement.getAsJsonObject();
            JsonArray users = roomObject.get("users").getAsJsonArray();
            ArrayList<User> users1 = new ArrayList<>();
            boolean isMember = false;
            for (JsonElement userElement : users) {
                String username = userElement.getAsString();
                if (username.equals(user.getUsername())) isMember = true;
                users1.add(Data.getUserByName(username));
            }
            if (isMember) {
                int id = roomObject.get("id").getAsInt();
                String name = roomObject.get("name").getAsString();
                ChatType chatType = ChatType.getChatTypeByString(roomObject.get("chat type").getAsString());
                ChatRoom chatRoom = user.getChatWithId(id);
                if (chatRoom == null) {
                    if (chatType != ChatType.PUBLIC) chatRoom = new ChatRoom(users1, chatType, 0);
                    else chatRoom = Data.getPublicRoom();
                    for (User user1 : users1) {
                        user1.getChats().add(chatRoom);
                    }
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
                chatRoom.setMessages(messages);
            }
        }
    }

    public UserThread(User user) {
        this.user = user;
    }
}
