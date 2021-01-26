package com.rogerio.ui.main;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class TableModel {
	
	private DefaultTableModel tableModel;
	private Object[][] matrix;
	
	@SuppressWarnings("serial")
	public DefaultTableModel getTableModel(Object[][] matrix) {
		this.matrix = matrix;
		tableModel = new DefaultTableModel(this.matrix, new Object[this.matrix.length]) {
			@Override
			public Class<?> getColumnClass(int column) {
				return ImageIcon.class;
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return tableModel;
	}
	
	public Object[][] getBoard() {
		return matrix;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

}
