package com.github.petruki.battleship.ui.mp;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.github.petruki.battleship.model.Target;
import com.github.petruki.battleship.ui.board.TableListener;
import com.github.petruki.battleship.ui.board.TableModel;
import com.github.petruki.battleship.ui.board.TableRender;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class BoardMPUI extends JTable {
	
	private TableModel tableModel;
	private final MainMPUI context;
	
	public BoardMPUI(final MainMPUI context) {
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
	
	public void setBoard(Object[][] matrix) {
		setModel(tableModel.getTableModel(matrix));
		new TableRender(this, context.getGameController());
	}

	public Object[][] getBoard() {
		return tableModel.getBoard();
	}
	
	public void updateBoard(Object value, int row, int column) {
		tableModel.getTableModel().setValueAt(value, row, column);
	}

}
