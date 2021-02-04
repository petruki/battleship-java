package com.github.petruki.battleship.broker.data;

import com.github.petruki.battleship.broker.BrokerEvents;

public class MatchDTO extends BrokerData {
	
	private int[][] matrix;
	
	public MatchDTO(BrokerEvents brokerEvent) {
		super(null, brokerEvent.toString());
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	public void setMatrix(Object[][] matrix) {
		this.matrix = new int[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				this.matrix[i][j] = Integer.parseInt(matrix[i][j].toString());
			}
		}
	}
	
	public Object[][] getMatrix(int[][] brokerMatrix) {
		Object[][] matrix = new Object[brokerMatrix.length][brokerMatrix.length];
		for (int i = 0; i < brokerMatrix.length; i++) {
			for (int j = 0; j < brokerMatrix[i].length; j++) {
				matrix[i][j] = brokerMatrix[i][j];
			}
		}
		
		return matrix;
	}

}
