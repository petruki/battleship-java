package com.rogerio.ui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.rogerio.model.Target;

public class TableRender implements TableCellRenderer {
	
	public TableRender(JTable table) {
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
			setIcon(cell, table, row, column, isSelected);
		} else {
			setIcon(cell, table, row, column, isSelected);
		}
		
		return cell;
	}
	
	private void setIcon(JPanel cell, JTable table, int row, int column, boolean isSelected) {
		if (table.getModel().getValueAt(row, column) instanceof Target) {
			if (isSelected)
				cell.setBackground(new Color(255, 0, 0, 80));
			
			Target target = (Target) table.getModel().getValueAt(row, column);
			cell.add(new JLabel(target.getIcon()));
		}
	}
	
	public void setupTable(JTable table) {
		String columnName;
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnName = table.getColumnName(i);
			table.getColumn(columnName).setCellRenderer(this);
		}
	}

}
