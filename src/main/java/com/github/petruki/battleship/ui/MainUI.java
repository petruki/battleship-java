package com.github.petruki.battleship.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.petruki.battleship.controller.GameController;
import com.github.petruki.battleship.model.GameSettings;
import com.github.petruki.battleship.model.Scoreboard;
import com.github.petruki.battleship.util.ResourceConstants;
import com.github.petruki.battleship.util.ResourcesCache;

/**
 * This is the App Context which handles all other UI objects.
 * 
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class MainUI extends JFrame {

	private GameSettings gameSettings;
	private GameController gameController;

	private BackgroundPanel contentPane;
	private ControlUI controlUI;
	private HeaderUI headerUI;
	private BoardUI boardUI;
	private ScoreUI scoreUI;
	private EndGameScoreUI endGameScoreUI;

	public MainUI(final GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle("Battleship Java - v1.0.5");
		setIconImage(ResourcesCache.getInstance().getImages(ResourceConstants.IMG_HIT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 880);
		setResizable(false);
		centerUI();
		
		gameController = new GameController(gameSettings.getTargets());
		controlUI = new ControlUI(this);
		headerUI = new HeaderUI(this, gameSettings.getTimeLimit());
		boardUI = new BoardUI(this);
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
		
		onStartNewGame(null);
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
	
	public void changeSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}
	
	public GameSettings getSettings() {
		return gameSettings;
	}

	public GameController getGameController() {
		return gameController;
	}
	
	public HeaderUI getHeaderUI() {
		return headerUI;
	}

	public ControlUI getControlUI() {
		return controlUI;
	}

	public BoardUI getBoardUI() {
		return boardUI;
	}

	public ScoreUI getScoreUI() {
		return scoreUI;
	}

	class BackgroundPanel extends JPanel {

		private Image background;

		public BackgroundPanel(Image background) {
			this.background = background;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
		}

		public void setBackground(Image background) {
			this.background = background;
			MainUI.this.repaint();
		}
	}
}
