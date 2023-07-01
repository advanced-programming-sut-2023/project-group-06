package org.example;

public enum ChatType {
    PRIVATE,
    PUBLIC,
    ROOM;

    public static ChatType getChatTypeByString(String name) {
        for (ChatType value : ChatType.values()) {
            if (name.equalsIgnoreCase(value.toString())) return value;
        }
        return null;
    }
}
