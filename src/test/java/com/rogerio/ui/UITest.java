package com.rogerio.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.rogerio.model.Target;
import com.rogerio.ui.MainUI;

public class UITest {
	
	private MainUI context;
	
	@Before
	public void setup() {
		context = new MainUI(4, 3);
	}
	
	@Test
	public void shouldDoNothing() {
		context.getControlUI().onFire(null);
		assertEquals("", context.getHeaderUI().getTxtMessage().getText());
	}
	
	private void selectingTarget(Object[][] matrix, int find) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				//selecting the target
				if (Integer.parseInt(matrix[i][j].toString()) == find) {
					context.getControlUI().updateCoordinates(new Target(i, j).getCoord());
				}
			}
		}
	}

	@Test
	public void shouldHit() {
		//find ship to hit
		Object[][] matrix = context.getBoardUI().getBoard();
		selectingTarget(matrix, 1);
		
		//fire!
		context.getControlUI().onFire(null);
		
		//test
		assertEquals("HIT!", context.getHeaderUI().getTxtMessage().getText());
	}
	
	@Test
	public void shouldMiss() {
		//find ship to hit
		Object[][] matrix = context.getBoardUI().getBoard();
		selectingTarget(matrix, 0);
		
		//fire!
		context.getControlUI().onFire(null);
		
		//test
		assertEquals("You missed.", context.getHeaderUI().getTxtMessage().getText());
	}
	
	@Test
	public void shouldHitAgain() {
		//find ship to hit
		Object[][] matrix = context.getBoardUI().getBoard();
		selectingTarget(matrix, 1);
		
		//fire!
		context.getControlUI().onFire(null);
		context.getControlUI().onFire(null);
		
		//test
		assertEquals("Oops, you already hit that location!", context.getHeaderUI().getTxtMessage().getText());
	}
	
	@Test
	public void shouldSinkShip() {
		//find ship to hit
		Object[][] matrix = context.getBoardUI().getBoard();
		selectingTarget(matrix, 1);
		context.getControlUI().onFire(null);
		
		matrix = context.getBoardUI().getBoard();
		selectingTarget(matrix, 1);
		context.getControlUI().onFire(null);
		
		matrix = context.getBoardUI().getBoard();
		selectingTarget(matrix, 1);
		context.getControlUI().onFire(null);
		
		//test
		assertEquals("You sank my battleship!", context.getHeaderUI().getTxtMessage().getText());
	}
	
}
