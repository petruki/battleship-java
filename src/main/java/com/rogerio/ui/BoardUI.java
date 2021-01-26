package com.rogerio.ui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.rogerio.model.Target;
import com.rogerio.ui.main.TableListener;
import com.rogerio.ui.main.TableModel;

@SuppressWarnings("serial")
public class BoardUI extends JTable {
	
	private TableModel tableModel;
	private final MainUI context;
	
	public BoardUI(final MainUI context) {
		this.context = context;
		buildBoard();
	}
	
	private void buildBoard() {
		tableModel = new TableModel();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowHeight(97);
		setBounds(170, 98, 675, 675);
		setBackground(new Color(0, 0, 0, 0));
		setShowGrid(false);
		setOpaque(false);
		
		initTableListener();
	}
	
	private void initTableListener() {
		addMouseListener(new TableListener(this) {
			@Override
			public void boardSelected(Target target) {
				context.getControlUI().getTxtCoordinate().setText(target.getCoord());
			}
		});
	}

	public TableModel getTableModel() {
		return tableModel;
	}

}
