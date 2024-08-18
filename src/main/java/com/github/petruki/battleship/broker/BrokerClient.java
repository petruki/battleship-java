package com.github.petruki.battleship.broker;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.broker.data.BrokerData;
import com.github.petruki.battleship.model.Player;
import com.google.gson.Gson;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

public class BrokerClient {
	
	private static final Logger logger = LogManager.getLogger(BrokerClient.class);
	
	private static BrokerClient instance;
	private Socket socket;
	private final List<String> events;
	private Player player;
	
	private BrokerClient() {
		events = new ArrayList<>();
	}
	
	public static BrokerClient getInstance() {
		if (instance == null)
			instance = new BrokerClient();
		return instance;
	}
	
    private void initSocket() {
        try {
            IO.Options opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};
            socket = IO.socket("http://localhost:3000", opts);
        } catch (URISyntaxException e) {
        	logger.error(e.getMessage());
        }
    }
    
    public void connect() {
        if (socket == null || !socket.connected()) {
            initSocket();
            socket.connect();
        }
    }
    
    public void subscribe(String event, Emitter.Listener listener) {
        socket.on(event, listener);
        events.add(event);
    }
    
    public void unsubscribeAll() {
        for (String event : events)
            socket.off(event);
        events.clear();
    }
    
    public void emitEvent(BrokerData brokerData) {
        if (socket.connected()) {
            Gson gson = new Gson();
            socket.emit(brokerData.getAction(), gson.toJson(brokerData));
        }
    }

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
