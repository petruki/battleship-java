package com.github.petruki.battleship.ui;import java.awt.event.ActionEvent;

import com.github.petruki.battleship.controller.GameController;
import com.github.petruki.battleship.model.GameSettings;

public interface MainUIActionEvent {
	
	void onStartNewGame(ActionEvent event);
	
	void changeSettings(GameSettings gameSettings);
	
	void onGameEnded();
	
	void onSwitchModes();
	
	void onGameFinished();
	
	GameSettings getSettings();
	
	GameController getGameController();
	
	ScoreUI getScoreUI();
	
	AbstractControlUI getControlUI();
	
	AbstractBoardUI getBoardUI();
	
	AbstractHeaderUI getHeaderUI();

}
