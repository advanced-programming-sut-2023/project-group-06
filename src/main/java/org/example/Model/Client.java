package org.example.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    int toggle = 0;
    public boolean isClientAlive = true;

    public Client(String host, int port) throws IOException {
        System.out.println("client");
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        System.out.println(socket.getPort());
//        while (isClientAlive)
//            handleClient();
    }

    private synchronized void handleClient() throws IOException {
//        dataOutputStream.writeUTF(scanner.nextLine());
//        System.out.println(dataInputStream.readUTF());
    }

}
