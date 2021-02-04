package com.github.petruki.battleship.broker.data;

import com.github.petruki.battleship.broker.BrokerEvents;

public class ShotDTO extends BrokerData {
	
	private String player;
	private String coord;
	
	public ShotDTO(BrokerEvents brokerEvent) {
		super(null, brokerEvent.toString());
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

}
