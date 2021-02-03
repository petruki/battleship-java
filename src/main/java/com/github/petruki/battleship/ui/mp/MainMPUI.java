package com.github.petruki.battleship.ui.mp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import com.github.petruki.battleship.controller.GameController;
import com.github.petruki.battleship.controller.OnlineGameController;
import com.github.petruki.battleship.model.GameSettings;
import com.github.petruki.battleship.model.Scoreboard;
import com.github.petruki.battleship.ui.EndGameScoreUI;
import com.github.petruki.battleship.ui.MainUI;
import com.github.petruki.battleship.ui.ScoreUI;
import com.github.petruki.battleship.ui.board.BackgroundPanel;
import com.github.petruki.battleship.util.ResourceConstants;
import com.github.petruki.battleship.util.ResourcesCache;

/**
 * This is the App Context for the Multiplayer which handles all other UI objects.
 * 
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class MainMPUI extends JFrame {

	private GameSettings gameSettings;
	private GameController gameController;

	private BackgroundPanel contentPane;
	private BoardMPUI boardUI;
	private ScoreUI scoreUI;
	private ControlMPUI controlUI;
	private HeaderMPUI headerUI;
	private EndGameScoreUI endGameScoreUI;

	public MainMPUI(final GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle("Battleship Java - v1.0.5 [ONLINE]");
		setIconImage(ResourcesCache.getInstance().getImages(ResourceConstants.IMG_HIT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 880);
		setResizable(false);
		centerUI();
		
		gameController = new OnlineGameController(gameSettings.getTargets());
		controlUI = new ControlMPUI(this);
		headerUI = new HeaderMPUI(this, gameSettings.getTimeLimit());
		boardUI = new BoardMPUI(this);
		scoreUI = new ScoreUI();
		endGameScoreUI = new EndGameScoreUI();

		contentPane = new BackgroundPanel(
			ResourcesCache.getInstance().getImages(ResourceConstants.IMG_BOARD));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		getContentPane().add(boardUI);
		getContentPane().add(controlUI);
		getContentPane().add(headerUI);
		getContentPane().add(scoreUI);
		getContentPane().add(endGameScoreUI);
		
		boardUI.setVisible(false);
		controlUI.setVisible(false);
		scoreUI.setVisible(false);
	}

	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	public void onStartNewGame(ActionEvent event) {
		endGameScoreUI.setVisible(false);
		boardUI.setVisible(true);
		controlUI.setVisible(true);
		scoreUI.setVisible(true);
		
		contentPane.setBackground(ResourcesCache.getInstance()
				.getImages(ResourceConstants.IMG_BOARD));
		
		Object[][] matrix = gameController
				.generateMatrix(7, 7, gameSettings.getShips(), gameSettings.getShipSize());
		
		headerUI.reloadGame();
		boardUI.setBoard(matrix);
		scoreUI.updateScore(gameController.getScoreBoard());
	}
	
	public void onGameFinished(Scoreboard scoreBoard) {
		contentPane.setBackground(
			ResourcesCache.getInstance().getImages(
					scoreBoard.getFinalScore() > 0 ? 
							ResourceConstants.IMG_BOARD_END_GOOD : 
							ResourceConstants.IMG_BOARD_END_BAD));
		
		endGameScoreUI.showScore(scoreBoard);
		boardUI.setVisible(false);
		controlUI.setVisible(false);
		scoreUI.setVisible(false);
		headerUI.onGameFinished();
	}
	
	public void onGameEnded() {
		boardUI.setVisible(false);
		controlUI.setVisible(false);
		scoreUI.setVisible(false);
		headerUI.onGameFinished();
	}
	
	public void onSwitchToOffline() {
		dispose();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI(gameSettings);
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	public void changeSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}
	
	public GameSettings getSettings() {
		return gameSettings;
	}

	public GameController getGameController() {
		return gameController;
	}
	
	public HeaderMPUI getHeaderUI() {
		return headerUI;
	}

	public ControlMPUI getControlUI() {
		return controlUI;
	}

	public BoardMPUI getBoardUI() {
		return boardUI;
	}

	public ScoreUI getScoreUI() {
		return scoreUI;
	}

}
