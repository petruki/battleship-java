package com.github.petruki.battleship.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author petruki (Rogerio Floriano)
 *
 */
public class ScoreboardOnline {
	
	private List<Player> players;
	
	public ScoreboardOnline() {
		players = new ArrayList<>();
	}

	public List<Player> getPlayers() {
		players.sort(Comparator.comparing(Player::getScore).reversed());
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public Player getPlayer(String player) {
		Player playerFound;
		
		if (players.isEmpty()) {
			playerFound = new Player(player);
			players.add(playerFound);
		} else {
			for (Player p : players) {
				if (p.getUsername().equals(player)) {
					return p;
				}
			}
			
			playerFound = new Player(player);
			players.add(playerFound);
		}

		return playerFound;
	}

	@Override
	public String toString() {
		return "ScoreboardOnline [players=" + players + "]";
	}

}
