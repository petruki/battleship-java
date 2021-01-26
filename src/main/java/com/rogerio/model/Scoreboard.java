package com.rogerio.model;

/**
 * @author petruki (Roger Floriano)
 */
public class Scoreboard {
	
	private int hit;
	
	private int miss;
	
	private int targets;
	
	public Scoreboard(int targets) {
		this.targets = targets;
		this.hit = 0;
		this.miss = 0;
	}
	
	/**
	 * Add a hit and verifies if all target are gone
	 * 
	 * @return true if all targets were hit
	 */
	public boolean addHit() {
		this.hit++;
		this.targets--;
		
		if (this.targets == 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Add a miss to the scoreboard
	 */
	public void addMiss() {
		this.miss++;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getMiss() {
		return miss;
	}

	public void setMiss(int miss) {
		this.miss = miss;
	}

	public int getTargets() {
		return targets;
	}

	public void setTargets(int targets) {
		this.targets = targets;
	}

}
