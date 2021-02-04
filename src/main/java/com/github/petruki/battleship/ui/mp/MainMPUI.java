package com.github.petruki.battleship.ui.mp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import com.github.petruki.battleship.broker.BrokerClient;
import com.github.petruki.battleship.broker.BrokerEvents;
import com.github.petruki.battleship.broker.data.BrokerData;
import com.github.petruki.battleship.broker.data.MatchDTO;
import com.github.petruki.battleship.broker.data.PlayerDTO;
import com.github.petruki.battleship.broker.data.ShotDTO;
import com.github.petruki.battleship.controller.GameController;
import com.github.petruki.battleship.controller.OnlineGameController;
import com.github.petruki.battleship.model.GameSettings;
import com.github.petruki.battleship.model.ScoreboardOnline;
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
	private EndGameScoreMPUI endGameScoreUI;
	
	private final BrokerClient client;

	public MainMPUI(final GameSettings gameSettings) {
		client = BrokerClient.getInstance();
		client.connect();
		client.subscribe(BrokerEvents.NEW_ROUND.toString(), this::onRoundStarted);
		client.subscribe(BrokerEvents.SELECT_PLAYER.toString(), this::onPlayerSelected);
		client.subscribe(BrokerEvents.PLAYER_HAS_SHOT.toString(), this::onPlayerShotting);
		
		this.gameSettings = gameSettings;
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle(String.format("Battleship Java - v1.0.5 [ONLINE] - %s", client.getPlayer().getUsername()));
		setIconImage(ResourcesCache.getInstance().getImages(ResourceConstants.IMG_HIT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 880);
		setResizable(false);
		centerUI();
		
		gameController = new OnlineGameController(gameSettings.getTargets());
		controlUI = new ControlMPUI(this);
		headerUI = new HeaderMPUI(this);
		boardUI = new BoardMPUI(this);
		scoreUI = new ScoreUI();
		endGameScoreUI = new EndGameScoreMPUI();

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
		
		if (!client.getPlayer().isHost()) {
			headerUI.setInvitedPlayerView();
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.unsubscribeAll();
				client.emitEvent( new PlayerDTO(null, 
						BrokerEvents.PLAYER_HAS_DISCONNECTED));
			}
		});
	}

	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	private Object[][] onSetupMatch(Object[][] matrix) {
		endGameScoreUI.setVisible(false);
		boardUI.setVisible(true);
		scoreUI.setVisible(true);
		
		contentPane.setBackground(ResourcesCache.getInstance()
				.getImages(ResourceConstants.IMG_BOARD));
		
		if (matrix == null) {
			matrix = gameController
						.generateMatrix(7, 7, gameSettings.getShips(), gameSettings.getShipSize());
		}
		
		headerUI.reloadGame(client.getPlayer().isHost());
		boardUI.setBoard(matrix);
		boardUI.setEnabled(false);
		scoreUI.updateScore(gameController.getScoreBoard());
		
		return matrix;
	}
	
	private void onRoundStarted(Object... args) {
		MatchDTO matchDTO = BrokerData.getDTO(MatchDTO.class, args);
		onSetupMatch(matchDTO.getMatrix(matchDTO.getMatrix()));
	}
	
	private void onPlayerSelected(Object... args) {
		controlUI.setVisible(true);
		boardUI.setEnabled(true);
	}
	
	private void onPlayerShotting(Object... args) {
		ShotDTO shotDTO = BrokerData.getDTO(ShotDTO.class, args);
		
		if (!client.getPlayer().getUsername().equals(shotDTO.getPlayer()))
			controlUI.onFireByOponent(shotDTO);
	}
	
	public void onStartNewGame(ActionEvent event) {
		// bring players waiting on the lobby to join the round
		MatchDTO matchDTO = new MatchDTO(BrokerEvents.MATCH_STARTED);
		client.emitEvent(matchDTO);
		
		final Object[][] matrix = onSetupMatch(null);
		
		// share board with players and start rotation
		matchDTO = new MatchDTO(BrokerEvents.NEW_ROUND);
		matchDTO.setMatrix(matrix);
		client.emitEvent(matchDTO);
	}
	
	public void onGameFinished(ScoreboardOnline scoreBoard) {
		contentPane.setBackground(
			ResourcesCache.getInstance().getImages(ResourceConstants.IMG_BOARD_END_GOOD));
		
		endGameScoreUI.showScore(scoreBoard);
		boardUI.setVisible(false);
		controlUI.setVisible(false);
		scoreUI.setVisible(false);
		headerUI.onGameFinished(client.getPlayer().isHost());
		
		System.out.println(gameController.getOnlineScoreBoard());
	}
	
	public void onGameEnded() {
		boardUI.setVisible(false);
		controlUI.setVisible(false);
		scoreUI.setVisible(false);
		headerUI.onGameFinished(client.getPlayer().isHost());
	}
	
	public void onSwitchToOffline() {
		client.unsubscribeAll();
		client.emitEvent( new PlayerDTO(null, 
				BrokerEvents.PLAYER_HAS_DISCONNECTED));
		
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
