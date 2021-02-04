package com.github.petruki.battleship.ui.mp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.broker.BrokerClient;
import com.github.petruki.battleship.broker.BrokerEvents;
import com.github.petruki.battleship.broker.data.ShotDTO;
import com.github.petruki.battleship.model.Player;
import com.github.petruki.battleship.model.ScoreboardOnline;
import com.github.petruki.battleship.model.SlotType;
import com.github.petruki.battleship.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class ControlMPUI extends JPanel {
	
	private static final Logger logger = LogManager.getLogger(ControlMPUI.class);
	
	private JTextPane txtCoordinate;
	private final MainMPUI context;
	
	public ControlMPUI(final MainMPUI context) {
		this.context = context;
		buildPanel();
	}
	
	private void buildPanel() {
		setBackground(new Color(83, 175, 19));
		setBounds(864, 98, 130, 96);
		setLayout(null);

		txtCoordinate = new JTextPane();
		txtCoordinate.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtCoordinate.setBounds(10, 11, 110, 23);
		add(txtCoordinate);

		JButton btnFire = new JButton("Fire!");
		btnFire.setForeground(new Color(255, 255, 255));
		btnFire.setBackground(new Color(128, 0, 0));
		btnFire.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnFire.setBounds(10, 45, 107, 40);
		btnFire.setFocusable(false);
		btnFire.addActionListener(this::onFire);
		add(btnFire);
	}
	
	private void onHit(final BoardMPUI boardUI, final HeaderMPUI headerUI, final Target result, String player) {
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

	private void onMiss(final HeaderMPUI headerUI, String player) {
		ScoreboardOnline onlineScore = context.getGameController().getOnlineScoreBoard();
		Player playerScore = onlineScore.getPlayer(player);
		
		playerScore.removePoint();
		headerUI.updateText(String.format("%s missed.", player));
		headerUI.updateScoreUI(false);
	}
	
	public void onFire(ActionEvent event) {
		try {
			ShotDTO shotDTO = new ShotDTO(BrokerEvents.PLAYER_HAS_SHOT);
			final BoardMPUI boardUI = context.getBoardUI();
			final HeaderMPUI headerUI = context.getHeaderUI();
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
				JOptionPane.showMessageDialog(context, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void onFireByOponent(ShotDTO shotDTO) {
		try {
			final BoardMPUI boardUI = context.getBoardUI();
			final HeaderMPUI headerUI = context.getHeaderUI();
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
	
	public void updateCoordinates(String coordinates) {
		txtCoordinate.setText(coordinates);
	}
	
	public String getCoordinates() {
		return txtCoordinate.getText();
	}

}
