package com.rogerio.model;

public class Scoreboard {
	
	private int hit;
	
	private int miss;
	
	private int targets;
	
	public Scoreboard(int targets) {
		this.targets = targets;
		this.hit = 0;
		this.miss = 0;
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
