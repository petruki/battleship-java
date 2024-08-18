package com.github.petruki.battleship.ui.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.github.petruki.battleship.controller.GameController;
import com.github.petruki.battleship.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
public class TableRender implements TableCellRenderer {
	
	private final GameController gameController;
	
	public TableRender(JTable table, GameController gameController) {
		this.gameController = gameController;
		setupTable(table);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		JPanel cell = new JPanel();
		cell.setOpaque(false);
		cell.setLayout(new GridBagLayout());
		
		if (isSelected && hasFocus) {
			cell.setOpaque(true);
			cell.setBackground(new Color(0, 255, 0, 80));
			
			if (!value.toString().equals("0") && gameController.isRadarActivated()) {
				cell.setBackground(new Color(255, 255, 255, 80));
			}
		}
		
		setIcon(cell, table, row, column, isSelected);
		return cell;
	}
	
	private void setIcon(JPanel cell, JTable table, int row, int column, boolean isSelected) {
		if (table.getModel().getValueAt(row, column) instanceof Target target) {
			if (isSelected) {
				cell.setBackground(new Color(255, 0, 0, 80));
			}

			cell.add(new JLabel(target.getIcon()));
		}
	}
	
	/**
	 * Initialize the render
	 */
	public void setupTable(JTable table) {
		String columnName;
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnName = table.getColumnName(i);
			table.getColumn(columnName).setCellRenderer(this);
		}
	}

}
