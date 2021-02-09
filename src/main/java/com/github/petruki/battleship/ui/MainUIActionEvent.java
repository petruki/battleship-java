package com.github.petruki.battleship.ui;import java.awt.event.ActionEvent;

import com.github.petruki.battleship.controller.GameController;
import com.github.petruki.battleship.model.GameSettings;

public interface MainUIActionEvent {
	
	public void onStartNewGame(ActionEvent event);
	
	public void changeSettings(GameSettings gameSettings);
	
	public void onGameEnded();
	
	public void onSwitchModes();
	
	public void onGameFinished();
	
	public GameSettings getSettings();
	
	public GameController getGameController();
	
	public ScoreUI getScoreUI();
	
	public AbstractControlUI getControlUI();
	
	public AbstractBoardUI getBoardUI();
	
	public AbstractHeaderUI getHeaderUI();

}
