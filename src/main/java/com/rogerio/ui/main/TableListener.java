package com.rogerio.ui.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.rogerio.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
public abstract class TableListener extends MouseAdapter {
	
	private final JTable table;
	
	public TableListener(final JTable table) {
		this.table = table;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		boardSelected(new Target(table.getSelectedRow(), table.getSelectedColumn()));
	}
	
	public abstract void boardSelected(Target target);

}
