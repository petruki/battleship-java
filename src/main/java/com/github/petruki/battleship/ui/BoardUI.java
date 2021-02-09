package com.github.petruki.battleship.ui;

import com.github.petruki.battleship.ui.board.TableRender;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class BoardUI extends AbstractBoardUI {
	
	public BoardUI(final MainUI context) {
		super(context);
	}
	
	@Override
	public int setBoard(Object[][] matrix) {
		setModel(tableModel.getTableModel(matrix));
		new TableRender(this, context.getGameController());
		return 0;
	}

}
