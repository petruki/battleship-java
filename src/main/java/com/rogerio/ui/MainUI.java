package com.rogerio.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.rogerio.controller.GameController;
import com.rogerio.ui.main.TableRender;
import com.rogerio.util.LoadImage;

@SuppressWarnings("serial")
public class MainUI extends JFrame {

	private GameController gameController;
	private BackgroundPanel contentPane;

	private ControlUI controlUI;
	private HeaderUI headerUI;
	private BoardUI boardUI;

	public MainUI() {
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle("Battleship Java");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 880);
		setResizable(false);
		centerUI();
		
		gameController = new GameController();
		controlUI = new ControlUI(this);
		headerUI = new HeaderUI(this);
		boardUI = new BoardUI(this);

		contentPane = new BackgroundPanel(LoadImage.load("board.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		getContentPane().add(boardUI);
		getContentPane().add(controlUI);
		getContentPane().add(headerUI);
		getContentPane().add(new ScoreUI());
		
		onStartNewGame(null);
	}

	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	public void onStartNewGame(ActionEvent event) {
		headerUI.getTxtMessage().setText("");
		boardUI.setModel(boardUI.getTableModel().getTableModel(gameController.generateMatrix(7, 7, 10)));
		new TableRender(boardUI);
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
	}
}
