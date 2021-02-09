package com.github.petruki.battleship.ui;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.model.SlotType;
import com.github.petruki.battleship.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class ControlUI extends AbstractControlUI {
	
	private static final Logger logger = LogManager.getLogger(ControlUI.class);
	
	public ControlUI(final MainUI context) {
		super(context);
	}
	
	@Override
	public void onFire(ActionEvent event) {
		try {
			final AbstractBoardUI boardUI = context.getBoardUI();
			final AbstractHeaderUI headerUI = context.getHeaderUI();
			final Target result = context
					.getGameController()
					.onFire(boardUI.getBoard(), txtCoordinate.getText());
			
			if (result.getSlotType() == SlotType.HIT) {
				onHit(boardUI, headerUI, result);
			} else {
				onMiss(headerUI);
			}
			
			if (context.getGameController().activateRadar()) {
				headerUI.updateText("Radar is activated for 5 seconds!");
			}
			
			//updates the board
			boardUI.updateBoard(result, result.getRowCoord(), result.getColCoord());
		} catch (Exception e) {
			logger.error(e);
			if (event != null)
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onHit(final AbstractBoardUI boardUI, final AbstractHeaderUI headerUI, 
			final Target result) {
		if (boardUI.getModel().getValueAt(result.getRowCoord(), result.getColCoord()).equals(result))
			headerUI.updateText("Oops, you already hit that location!");
		else {
			if (context.getGameController().hasSink(boardUI.getBoard(), result.getShipId())) {
				headerUI.updateText("You sank my battleship!");
			} else {
				headerUI.updateText("HIT!");
			}

			headerUI.updateScoreUI(true);
		}
	}

	private void onMiss(final AbstractHeaderUI headerUI) {
		headerUI.updateText("You missed.");
		headerUI.updateScoreUI(false);
	}
	
	public void updateCoordinates(String coordinates) {
		txtCoordinate.setText(coordinates);
	}

}
