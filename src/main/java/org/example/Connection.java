package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    Socket socket;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    long lastTime = -2L;
    Client client = null;
    private boolean isConnectionAlive = true;


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
        Client client2 = new Gson().fromJson(userJson, Client.class);
        client2.setHashedPassword(password);
        client2.setAvatar(avatar);
        client2.setAnswerToQuestion(answerToQuestion);
        System.out.println(json);
        String username = userJson.get("username").getAsString();
        Client client1 = Data.getClientByName(username);
        if (client1 == null) {
            Data.addClient(client2);
            client = client2;
        } else {
            client1.override(client2);
            client = client1;
        }
        while (isConnectionAlive) {
            try {
                handleClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
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
        String input = dataInputStream.readUTF();
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
        if (time == -1) isConnectionAlive = false;
        else if (lastTime == -2L) return;
        else if (time > lastTime + 100000) {
            System.out.println("Client with username " + client.getUsername() + " has been disconnected!");
            //todo
        }
    }

    private void handleClientCommand(JsonObject json) {
        String command = json.get("command").getAsJsonObject().get("command").getAsString();
        //todo
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
}
