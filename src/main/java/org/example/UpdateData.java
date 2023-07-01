//package org.example;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//
//import java.io.IOException;
//
//public class UpdateData extends Thread {
//    final Connection connection;
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                sendData();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void sendData() throws IOException {
//        JsonObject root = new JsonObject();
//        JsonArray chatRooms = new JsonArray();
//        for (ChatRoom chatRoom : Data.getChatRooms()) {
//            chatRooms.add(chatRoom.toJson());
//        }
//        root.add("chat rooms", chatRooms);
//        connection.updateData = root;
//    }
//
//    public UpdateData(Connection connection) {
//        this.connection = connection;
//    }
//
//
//}
