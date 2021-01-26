package com.rogerio.model;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.rogerio.util.LoadImage;

/**
 * @author petruki (Roger Floriano)
 */
public class Target {
	
	public static final String[] CHARS = {"A", "B", "C", "D", "E", "F", "G"};
	
	private int shipId;
	
	private int rowCoord;
	
	private int colCoord;
	
	private SlotType slotType;
	
	private Icon icon;
	
	/**
	 * Constructor used when interacting with the board
	 */
	public Target(int rowCoord, int colCoord) {
		this(0, rowCoord, colCoord, null, null);
	}
	
	/**
	 * Constructor used when onFire is used
	 */
	public Target(int shipId, int rowCoord, int colCoord, SlotType slotType, Icon icon) {
		this.shipId = shipId;
		this.rowCoord = rowCoord;
		this.colCoord = colCoord;
		this.slotType = slotType;
		this.icon = icon;
	}
	
	/**
	 * Creates a new target with either miss or hit icon
	 * 
	 * @param slot board position id
	 */
	public static Target createTarget(int slot, int rowCoord, int colCoord) {
		if (slot == 0) {
			return new Target(slot, rowCoord, colCoord, SlotType.MISSED, new ImageIcon(LoadImage.load("miss.png")));
		} else {
			return new Target(slot, rowCoord, colCoord, SlotType.HIT, new ImageIcon(LoadImage.load("ship.png")));
		}
	}
	
	public int getRowCoord() {
		return rowCoord;
	}
	
	public int getColCoord() {
		return colCoord;
	}
	
	public String getCoord() {
		return CHARS[this.rowCoord] + this.colCoord;
	}

	public SlotType getSlotType() {
		return slotType;
	}

	public Icon getIcon() {
		return icon;
	}

	public int getShipId() {
		return shipId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Target other = (Target) obj;
		if (colCoord != other.colCoord)
			return false;
		if (rowCoord != other.rowCoord)
			return false;
		if (slotType != other.slotType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Target [rowCoord=" + rowCoord + ", colCoord=" + colCoord + "]";
	}
	
}
