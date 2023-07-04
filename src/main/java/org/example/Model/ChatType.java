package org.example.Model;

public enum ChatType{
    PRIVATE,
    PUBLIC,
    ROOM,
    GAME;

    public static ChatType getChatTypeByString(String name) {
        for (ChatType value : ChatType.values()) {
            if (name.equalsIgnoreCase(value.toString())) return value;
        }
        return null;
    }
}
