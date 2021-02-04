package com.github.petruki.battleship.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.petruki.battleship.model.SlotType;
import com.github.petruki.battleship.model.Target;

public class GameControllerTest {
	
	private OfflineGameController gameController;
	private Object[][] fixture1;
	
	@Before
	public void setup() {
		gameController = new OfflineGameController(3);
	}
	
	private void generateFixture() {
		fixture1 = new Object[][] {
			{0, 1, 1, 1, 0, 0, 0},
			{0, 2, 0, 0, 0, 0, 0},
			{0, 2, 0, 3, 0, 0, 0},
			{0, 2, 0, 3, 0, 0, 0},
			{0, 0, 0, 3, 0, 0, 0},
			{4, 4, 4, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
		};
	}
	
	@Test
	public void shouldGenerateMatrix() {
		//given
		int ships = 4;
		int shipSize = 3;
		Object[][] matrix = gameController.generateMatrix(7, 7, ships, shipSize);
		
		int targets = 0;
		for (Object[] row : matrix) {
			for (Object cell : row) {
				if (Integer.parseInt(cell.toString()) != 0) {
					targets++;
				}
			}
		}
		
		//test
		assertEquals((ships * shipSize), targets);
	}
	
	@Test
	public void shouldMiss() throws Exception {
		//given
		generateFixture();
		
		//test
		Target target = gameController.onFire(fixture1, "A0");
		assertEquals(SlotType.MISSED, target.getSlotType());
	}
	
	@Test
	public void shouldHit() throws Exception {
		//given
		generateFixture();
		
		//test
		Target target = gameController.onFire(fixture1, "A1");
		assertEquals(SlotType.HIT, target.getSlotType());
	}
	
	@Test
	public void shouldMarkAsHit() throws Exception {
		//given
		generateFixture();
		
		//test
		gameController.onFire(fixture1, "A1");
		assertEquals(-1, fixture1[0][1]);
	}
	
	@Test
	public void shouldAddNewShip() {
		//given
		generateFixture();
		
		//test
		assertTrue(gameController.addShip(fixture1, 0, 4, 3, 5));
		
		int targets = 0;
		for (Object[] row : fixture1) {
			for (Object cell : row) {
				if (Integer.parseInt(cell.toString()) == 5) {
					targets++;
				}
			}
		}
		
		assertEquals(3, targets);
	}
	
	@Test
	public void shouldNotAddNewShip() {
		//given
		generateFixture();
		
		//test
		assertFalse(gameController.addShip(fixture1, 0, 1, 3, 5));
	}
	
	@Test
	public void shouldCollide() {
		//given
		generateFixture();
		
		//test - overlap existing ship
		assertTrue(gameController.hasCollided(fixture1, 2, 3, 3, false));
		assertTrue(gameController.hasCollided(fixture1, 3, 2, 3, true));
		
		//test - exceed board length
		assertTrue(gameController.hasCollided(fixture1, 0, 5, 3, false));
		assertTrue(gameController.hasCollided(fixture1, 6, 6, 3, true));
	}
	
	@Test
	public void shouldNotCollide() {
		//given
		generateFixture();
		
		//test
		assertFalse(gameController.hasCollided(fixture1, 0, 4, 3, false));
		assertFalse(gameController.hasCollided(fixture1, 0, 4, 3, true));
		assertFalse(gameController.hasCollided(fixture1, 0, 5, 3, true));
		assertFalse(gameController.hasCollided(fixture1, 6, 0, 3, false));
	}
	
	@Test
	public void shouldSinkShip() throws Exception {
		//given
		generateFixture();

		//test
		gameController.onFire(fixture1, "A1");
		gameController.onFire(fixture1, "A2");
		gameController.onFire(fixture1, "A3");
		
		assertTrue(gameController.hasSink(fixture1, 1));
	}
	
	@Test(expected = Exception.class)
	public void shouldReturnError() throws Exception {
		//given
		generateFixture();

		//test
		gameController.onFire(fixture1, "A9");
	}

}
