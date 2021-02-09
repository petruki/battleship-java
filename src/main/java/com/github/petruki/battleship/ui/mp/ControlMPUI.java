package com.github.petruki.battleship.ui.mp;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.broker.BrokerClient;
import com.github.petruki.battleship.broker.BrokerEvents;
import com.github.petruki.battleship.broker.data.ShotDTO;
import com.github.petruki.battleship.model.Player;
import com.github.petruki.battleship.model.ScoreboardOnline;
import com.github.petruki.battleship.model.SlotType;
import com.github.petruki.battleship.model.Target;
import com.github.petruki.battleship.ui.AbstractBoardUI;
import com.github.petruki.battleship.ui.AbstractControlUI;
import com.github.petruki.battleship.ui.AbstractHeaderUI;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class ControlMPUI extends AbstractControlUI {
	
	private static final Logger logger = LogManager.getLogger(ControlMPUI.class);
	
	public ControlMPUI(final MainMPUI context) {
		super(context);
	}
	
	@Override
	public void onFire(ActionEvent event) {
		try {
			ShotDTO shotDTO = new ShotDTO(BrokerEvents.PLAYER_HAS_SHOT);
			final AbstractBoardUI boardUI = context.getBoardUI();
			final AbstractHeaderUI headerUI = context.getHeaderUI();
			final Target result = context
					.getGameController()
					.onFire(boardUI.getBoard(), txtCoordinate.getText());
			
			if (result.getSlotType() == SlotType.HIT) {
				onHit(boardUI, headerUI, result, BrokerClient.getInstance().getPlayer().getUsername());
			} else {
				onMiss(headerUI, BrokerClient.getInstance().getPlayer().getUsername());
			}
			
			//updates the board
			boardUI.updateBoard(result, result.getRowCoord(), result.getColCoord());
			boardUI.setEnabled(false);
			setVisible(false);
			
			//notify other players
			shotDTO.setCoord(txtCoordinate.getText());
			shotDTO.setPlayer(BrokerClient.getInstance().getPlayer().getUsername());
			BrokerClient.getInstance().emitEvent(shotDTO);
		} catch (Exception e) {
			logger.error(e);
			if (event != null)
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onHit(final AbstractBoardUI boardUI, final AbstractHeaderUI headerUI, 
			final Target result, String player) {
		ScoreboardOnline onlineScore = context.getGameController().getOnlineScoreBoard();
		Player playerScore = onlineScore.getPlayer(player);
		
		if (boardUI.getModel().getValueAt(result.getRowCoord(), result.getColCoord()).equals(result))
			headerUI.updateText(String.format("Oops, %s hit the same location.", player));
		else {
			playerScore.addPoint();
			if (context.getGameController().hasSink(boardUI.getBoard(), result.getShipId())) {
				headerUI.updateText("Battleship has been sank!");
			} else {
				headerUI.updateText(String.format("%s HIT the ship!", player));
			}

			headerUI.updateScoreUI(true);
		}
	}

	private void onMiss(final AbstractHeaderUI headerUI, String player) {
		ScoreboardOnline onlineScore = context.getGameController().getOnlineScoreBoard();
		Player playerScore = onlineScore.getPlayer(player);
		
		playerScore.removePoint();
		headerUI.updateText(String.format("%s missed.", player));
		headerUI.updateScoreUI(false);
	}
	
	public void onFireByOponent(ShotDTO shotDTO) {
		try {
			final AbstractBoardUI boardUI = context.getBoardUI();
			final AbstractHeaderUI headerUI = context.getHeaderUI();
			final Target result = context
					.getGameController()
					.onFire(boardUI.getBoard(), shotDTO.getCoord());
			
			if (result.getSlotType() == SlotType.HIT) {
				onHit(boardUI, headerUI, result, shotDTO.getPlayer());
			} else {
				onMiss(headerUI, shotDTO.getPlayer());
			}
			
			//updates the board
			boardUI.updateBoard(result, result.getRowCoord(), result.getColCoord());
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
