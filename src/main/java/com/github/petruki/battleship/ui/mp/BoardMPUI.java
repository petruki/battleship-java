package com.github.petruki.battleship.ui.mp;

import com.github.petruki.battleship.ui.AbstractBoardUI;
import com.github.petruki.battleship.ui.board.TableRender;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class BoardMPUI extends AbstractBoardUI {
	
	
	public BoardMPUI(final MainMPUI context) {
		super(context);
	}

	@Override
	public int setBoard(Object[][] matrix) {
		setModel(tableModel.getTableModel(matrix));
		new TableRender(this, context.getGameController());
		
		int targets = 0;
		for (Object[] row : matrix) {
			for (Object cell : row) {
				if (Integer.parseInt(cell.toString()) != 0) {
					targets++;
				}
			}
		}
		
		return targets;
	}

}
