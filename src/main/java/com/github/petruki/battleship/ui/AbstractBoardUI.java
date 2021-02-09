package com.github.petruki.battleship.ui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.github.petruki.battleship.model.Target;
import com.github.petruki.battleship.ui.board.TableListener;
import com.github.petruki.battleship.ui.board.TableModel;

@SuppressWarnings("serial")
public abstract class AbstractBoardUI extends JTable {
	
	protected TableModel tableModel;
	protected final MainUIActionEvent context;
	
	public AbstractBoardUI(final MainUIActionEvent context) {
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

			@Override
			public void boardSelecetedAndFire(Target target) {
				context.getControlUI().updateCoordinates(target.getCoord());
				context.getControlUI().onFire(null);
			}
		});
	}

	public Object[][] getBoard() {
		return tableModel.getBoard();
	}
	
	public void updateBoard(Object value, int row, int column) {
		tableModel.getTableModel().setValueAt(value, row, column);
	}
	
	public abstract int setBoard(Object[][] matrix);
}