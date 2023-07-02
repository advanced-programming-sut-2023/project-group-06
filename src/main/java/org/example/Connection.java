package org.example;

import com.google.gson.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    Socket socket;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    long lastTime = -2L;
    Client client = null;
    private boolean isConnectionAlive = true;
    JsonObject updateData = null;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: " + socket.getInetAddress() + ":" + socket.getPort());
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public synchronized void run() {
//        try {
//            ClientType clientType;
//            while ((clientType = getClientType()) == ClientType.UNKNOWN) Thread.sleep(200);
//            if (clientType == ClientType.CLIENT) {
//                while (isConnectionAlive) {
//                    handleClient();
//                    Thread.sleep(200);
//                }
//            } else {
//                worker = new Worker(this);
//                workerType = worker.getWorkerType();
//                while (isConnectionAlive) {
//                    handleWorker();
//                    Thread.sleep(200);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("bbb");
//        } catch (InterruptedException e) {
//            System.out.println("ccc");
//            throw new RuntimeException(e);
//        }
//        UpdateData updateData = new UpdateData(this);
//        updateData.setDaemon(true);
//        updateData.start();
        try {
            dataOutputStream.writeUTF(Boolean.toString(Data.hasAnyOneLoggedInYet));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Data.hasAnyOneLoggedInYet = true;
        String input = null;
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(input);
        JsonObject userJson = json.get("user").getAsJsonObject();
        String avatar = userJson.get("image").getAsString();
        String password = userJson.get("password").getAsString();
        String answerToQuestion = userJson.get("answerToQuestion").getAsString();
        String username = userJson.get("username").getAsString();
        String nickname = userJson.get("nickname").getAsString();
        String email = userJson.get("email").getAsString();
        int questionIndex = userJson.get("questionIndex").getAsInt();
        String slogan = userJson.get("slogan").getAsString();
        int highScore = userJson.get("highScore").getAsInt();
        Client client2 = null;
        try {
            client2 = new Client(username, password, nickname, email, slogan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client2.setHighScore(highScore);
        client2.setQuestionIndex(questionIndex);
        client2.setAvatar(avatar);
        client2.setAnswerToQuestion(answerToQuestion);
        Client client1 = Data.getClientByName(username);
        if (client1 == null) {
            Data.addClient(client2);
            client = client2;
        } else {
            client1.override(client2);
            client = client1;
            client.setOnline(true);
        }
        while (isConnectionAlive) {
            try {
                handleClient();
            } catch (IOException e) {
                killConnection();
            }
        }
    }

    private synchronized void handleWorker() throws IOException {
//        String output = "";
//        String workerInput = dataInputStream.readUTF();
//        if (!workerInput.equals(input)) {
//            input = workerInput;
//        } else killConnection();
//        if (workerType != worker.getWorkerType()) {
//            workerType = worker.getWorkerType();
//            output = "You have became " + workerType.toString().toLowerCase() + " just now!";
//        }
//
//        if (!worker.getUnNotifiedTasks().isEmpty()) {
//            String thisOutput = "These tasks have just added to your tasks set:";
//            for (Task task : worker.getUnNotifiedTasks()) {
//                thisOutput += '\n' + "    " + task.toString();
//            }
//            worker.getUnNotifiedTasks().clear();
//            output = (output.equals("")) ? thisOutput : (output + '\n' + thisOutput);
//        }
//
//        if (!worker.getDeletedTasks().isEmpty()) {
//            String thisOutput = "These tasks have just deleted from your tasks set:";
//            for (Task task : worker.getDeletedTasks()) {
//                thisOutput += '\n' + "    " + task.toString();
//            }
//            worker.getDeletedTasks().clear();
//            output = (output.equals("")) ? thisOutput : (output + '\n' + thisOutput);
//        }
//        dataOutputStream.writeUTF(output);
    }
    private synchronized void handleClient() throws IOException {
        if (updateData != null) {
            dataOutputStream.writeUTF(new Gson().toJson(updateData));
            System.out.println("dataWriteIfNotNull:    " + updateData);
            updateData = null;
        } else dataOutputStream.writeUTF("salam");
        String input = dataInputStream.readUTF();
        System.out.println("client input: " + input);
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(input);
        handleClientCommand(json);
        updateClientData(json);
        updateLastTime(json);
        //        String data = dataInputStream.readUTF();
//        Matcher matcher;
//        try {
//            if ((matcher = Commands.getMatcher(data, Commands.CREATE_TASK)).find()) {
//                dataOutputStream.writeUTF(createTask(matcher));
//            } else if ((matcher = Commands.getMatcher(data, Commands.GET_TASK)).find()) {
//                dataOutputStream.writeUTF(getTasks());
//            } else if ((matcher = Commands.getMatcher(data, Commands.GET_NODE)).find()) {
//                dataOutputStream.writeUTF(getNodes());
//            } else if ((matcher = Commands.getMatcher(data, Commands.DELETE_TASK)).find()) {
//                dataOutputStream.writeUTF(deleteTask(matcher));
//            } else if ((matcher = Commands.getMatcher(data, Commands.CORDON)).find()) {
//                dataOutputStream.writeUTF(cordonNode(matcher));
//            } else if ((matcher = Commands.getMatcher(data, Commands.UNCORDON)).find()) {
//                dataOutputStream.writeUTF(uncordonNode(matcher));
//            } else {
//                dataOutputStream.writeUTF("wrong command!");
//            }
//        } catch (JsonSyntaxException e) {
//            dataOutputStream.writeUTF("400: Missing topic or value or command fields.");
//        }
    }

    private void updateLastTime(JsonObject json) {
        long time = json.get("isAlive").getAsJsonObject().get("time").getAsLong();
        if (time == -1) killConnection();
        else lastTime = time;
    }

    private void handleClientCommand(JsonObject json) throws IOException {
        String commandType = json.get("command type").getAsJsonObject().get("command type").getAsString();
        JsonObject context = json.get("command content").getAsJsonObject();
        if (commandType.equals("send message")) sendMessage(context);
        if (commandType.equals("delete message")) deleteMessage(context);
        if (commandType.equals("edit message")) editMessage(context);
        if (commandType.equals("create room")) createRoom(context);
        if (commandType.equals("add to group")) addToGroup(context);
        if (commandType.equals("send request")) sendRequest(context);
        if (commandType.equals("accept request")) acceptRequest(context);
        if (commandType.equals("reject request")) rejectRequest(context);
        if (commandType.equals("inactivate")) killConnection();
        dataOutputStream.writeUTF(new Gson().toJson(sendData()));
    }

    private void rejectRequest(JsonObject context) {
        String senderUsername = context.get("sender").getAsString();
        String targetUsername = context.get("target").getAsString();
        boolean isAccepted = context.get("isAccepted").getAsBoolean();
        Client target = Data.getClientByName(targetUsername);
        for (int i = target.getNotRespondedFriendRequestsReceivedByMe().size() - 1; i >= 0; i--) {
            if (target.getNotRespondedFriendRequestsReceivedByMe().get(i).equals(senderUsername)) {
                target.removeFromNotRespondedFriendRequestsReceivedByMe(i);
                break;
            }
        }
    }

    private void acceptRequest(JsonObject context) {
        String senderUsername = context.get("sender").getAsString();
        String targetUsername = context.get("target").getAsString();
        boolean isAccepted = context.get("isAccepted").getAsBoolean();
        Client target = Data.getClientByName(targetUsername);
        Client sender = Data.getClientByName(senderUsername);
        for (int i = target.getNotRespondedFriendRequestsReceivedByMe().size() - 1; i >= 0; i--) {
            if (target.getNotRespondedFriendRequestsReceivedByMe().get(i).equals(senderUsername)) {
                target.addFriend(sender);
                sender.addFriend(target);
                target.removeFromNotRespondedFriendRequestsReceivedByMe(i);
                break;
            }
        }
    }

    private void sendRequest(JsonObject context) {
        String senderUsername = context.get("sender").getAsString();
        String targetUsername = context.get("target").getAsString();
        boolean isAccepted = context.get("isAccepted").getAsBoolean();
        Client sender = Data.getClientByName(senderUsername);
        sender.addToNotDeliveredFriendRequestsSentByMe(targetUsername);
    }

    private void killConnection() {
        client.setOnline(false);
        client.setLastSeen(lastTime);
        isConnectionAlive = false;
        System.out.println("Client with username " + client.getUsername() + " just got offline!");
    }

    private void addToGroup(JsonObject context) {
        JsonArray users = context.get("users").getAsJsonArray();
        ArrayList<String> usernames = new ArrayList<>();
        for (JsonElement user : users) {
            usernames.add(user.getAsJsonObject().get("username").getAsString());
        }
        int id = context.get("id").getAsInt();
        System.out.println("ADDTOGROUP:    usernames: " + usernames + " id: " + id);
        Data.getChatRoomById(id).setUsernames(usernames);
    }

    private void createRoom(JsonObject context) {
        System.out.println("create Room : " + context);
        int id = context.get("id").getAsInt();
        JsonArray members = context.get("users").getAsJsonArray();
        ArrayList<String> usernames = new ArrayList<>();
        for (JsonElement member : members) {
            JsonObject user = member.getAsJsonObject();
            usernames.add(user.get("username").getAsString());
        }
        ChatType chatType = ChatType.getChatTypeByString(context.get("chat type").getAsString());
        String name = context.get("name").getAsString();
        ChatRoom chatRoom = new ChatRoom(usernames, chatType, name, id);
    }

    private void editMessage(JsonObject context) {
        int chatRoomId = context.get("chat room id").getAsInt();
        ChatRoom chatRoom = Data.getChatRoomById(chatRoomId);
        int id = context.get("id").getAsInt();
        Message message = chatRoom.getMessageById(id);
        String content = context.get("content").getAsString();
        message.setContent(content);
    }

    private void deleteMessage(JsonObject context) {
        int chatRoomId = context.get("chat room id").getAsInt();
        ChatRoom chatRoom = Data.getChatRoomById(chatRoomId);
        int id = context.get("id").getAsInt();
        Message message = chatRoom.getMessageById(id);
        chatRoom.getMessages().remove(message);
    }

    private void sendMessage(JsonObject context) {
        int chatRoomId = context.get("chat room id").getAsInt();
        ChatRoom chatRoom = Data.getChatRoomById(chatRoomId);
        int id = context.get("id").getAsInt();
        String username = context.get("owner").getAsString();
        String time = context.get("time").getAsString();
        String content = context.get("content").getAsString();
        chatRoom.getMessages().add(new Message(username, content, time, chatRoom, id));
    }

    private synchronized void updateClientData(JsonObject json) {
        JsonObject userJson = json.get("user").getAsJsonObject();
        String avatar = userJson.get("image").getAsString();
        String password = userJson.get("password").getAsString();
        String answerToQuestion = userJson.get("answerToQuestion").getAsString();
        Client client = new Gson().fromJson(userJson, Client.class);
        client.setHashedPassword(password);
        client.setAvatar(avatar);
        client.setAnswerToQuestion(answerToQuestion);
        String username = userJson.get("username").getAsString();
        Client client1 = Data.getClientByName(username);
        client1.override(client);
    }

    public Client getClient() {
        return client;
    }

    private void updateReceivingFriendRequests() {
        System.out.println(client.getNotRespondedFriendRequestsReceivedByMe());
        for (Client client1 : Data.getClients()) {
            if (client1 != client) {
                for (int i = client1.getNotDeliveredFriendRequestsSentByMe().size() - 1; i >= 0; i--) {
                    if (client1.getNotDeliveredFriendRequestsSentByMe().get(i).equals(client.getUsername())) {
                        client.addToNotRespondedFriendRequestsReceivedByMe(client1.getUsername());
                        client1.removeFromNotDeliveredFriendRequestsSentByMe(i);
                    }
                }
            }
        }
    }

    private JsonObject sendData() {
        updateReceivingFriendRequests();
        JsonObject root = new JsonObject();
        JsonArray chatRooms = new JsonArray();
        for (ChatRoom chatRoom : Data.getChatRooms()) {
            chatRooms.add(chatRoom.toJson());
        }
        root.add("chat rooms", chatRooms);
        JsonArray notRespondedFriendRequestsReceivedByMe = new JsonArray();
        System.out.println("array:" + client + "  " + client.getNotRespondedFriendRequestsReceivedByMe());
        for (int i = 0; i < client.getNotRespondedFriendRequestsReceivedByMe().size(); i++) {
            String s = client.getNotRespondedFriendRequestsReceivedByMe().get(i);
            JsonObject jsonString = new JsonObject();
            jsonString.addProperty("name", s);
            notRespondedFriendRequestsReceivedByMe.add(jsonString);
        }
        root.add("friend requests received by me", notRespondedFriendRequestsReceivedByMe);
        JsonArray friends = new JsonArray();
        for (Client friend : client.getFriends()) {
            friends.add(friend.toJson());
        }
        root.add("friends", friends);
        return root;
    }
}

