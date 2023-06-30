package org.example.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    private int time = 0;


    public Client(String host, int port) throws IOException {
        System.out.println("client");
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        System.out.println(socket.getPort());
//        while (true)
//            handleClient(scanner);
    }

    private synchronized void handleClient(Scanner scanner) throws IOException {
        dataOutputStream.writeUTF(scanner.nextLine());
        System.out.println(dataInputStream.readUTF());
    }

    public String toString() {
        return "0";
    }
}
