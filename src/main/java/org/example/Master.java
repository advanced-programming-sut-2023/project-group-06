package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
    public Master(int port) {
        System.out.println("Starting Master service...");
        DeleteGameThread deleteGameThread = new DeleteGameThread();
        deleteGameThread.start();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
            }
        } catch (IOException e) {
            System.out.println("Master failed!");
        }
    }
}
