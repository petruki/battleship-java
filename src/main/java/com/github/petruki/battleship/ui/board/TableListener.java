package com.github.petruki.battleship.ui.board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.github.petruki.battleship.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
public abstract class TableListener extends MouseAdapter {
	
	private final JTable table;
	
	protected TableListener(final JTable table) {
		this.table = table;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		boardSelected(new Target(table.getSelectedRow(), table.getSelectedColumn()));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			boardSelectedAndFire(new Target(table.getSelectedRow(), table.getSelectedColumn()));
		}
	}
	
	public abstract void boardSelected(Target target);
	
	public abstract void boardSelectedAndFire(Target target);

}
