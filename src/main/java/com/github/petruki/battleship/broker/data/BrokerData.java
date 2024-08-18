package com.github.petruki.battleship.broker.data;

import com.google.gson.Gson;

public class BrokerData {

    private final String action;
    private final String room;

    public BrokerData(String room, String action) {
        this.room = room;
        this.action = action;
    }

    public static <T> T getDTO(Class<T> dtoType, Object... args) {
        Gson gson = new Gson();
        return gson.fromJson(args[0].toString(), dtoType);
    }

    public String getAction() {
        return action;
    }

    public String getRoom() {
        return room;
    }
}
