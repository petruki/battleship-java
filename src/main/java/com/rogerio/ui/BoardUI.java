package com.rogerio.ui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.rogerio.model.Target;
import com.rogerio.ui.board.TableListener;
import com.rogerio.ui.board.TableModel;
import com.rogerio.ui.board.TableRender;

/**
 * @author petruki (Roger Floriano)
 */
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
				context.getControlUI().updateCoordinates(target.getCoord());
			}
		});
	}
	
	public void setBoard(Object[][] matrix) {
		setModel(tableModel.getTableModel(matrix));
		new TableRender(this);
	}

	public Object[][] getBoard() {
		return tableModel.getBoard();
	}
	
	public void updateBoard(Object value, int row, int column) {
		tableModel.getTableModel().setValueAt(value, row, column);
	}

}
