package com.rogerio.model;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.rogerio.util.LoadImage;

public class Target {
	
	public static final String[] CHARS = {"A", "B", "C", "D", "E", "F", "G"};
	
	private int rowCoord;
	
	private int colCoord;
	
	private SlotType slotType;
	
	private Icon icon;
	
	public Target(int rowCoord, int colCoord) {
		this(rowCoord, colCoord, null, null);
	}
	
	public Target(int rowCoord, int colCoord, SlotType slotType, Icon icon) {
		this.rowCoord = rowCoord;
		this.colCoord = colCoord;
		this.slotType = slotType;
		this.icon = icon;
	}
	
	public static Target createTarget(int slot, int rowCoord, int colCoord) {
		if (slot == 0) {
			return new Target(rowCoord, colCoord, SlotType.MISSED, new ImageIcon(LoadImage.load("miss.png")));
		} else {
			return new Target(rowCoord, colCoord, SlotType.HIT, new ImageIcon(LoadImage.load("ship.png")));
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
