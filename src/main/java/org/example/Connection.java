package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;

public class Connection extends Thread {
    Socket socket;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;


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
        while (true) {
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
        if (isData(input)) {
            input = makeValidData(input);
            System.out.println(input);
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(input);
            String avatar = json.get("image").getAsString();
            String password = json.get("password").getAsString();
            String answerToQuestion = json.get("answerToQuestion").getAsString();
            User user = new Gson().fromJson(input, User.class);
            user.setHashedPassword(password);
            user.setAvatar(avatar);
            user.setAnswerToQuestion(answerToQuestion);
            String username = json.get("username").getAsString();
            if (Data.getUserByName(username) == null) {
                Data.addUser(user);
            } else {
                Data.getUserByName(username).override(user);
            }

        }
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

    private boolean isData(String input) {
        return true;
        //todo
    }

    private String makeValidData(String input) {
        return input;
        //todo
    }
}
