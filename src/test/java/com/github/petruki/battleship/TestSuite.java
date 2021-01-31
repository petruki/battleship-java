package com.github.petruki.battleship;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.github.petruki.battleship.controller.GameControllerTest;
import com.github.petruki.battleship.ui.UITest;

/**
 * Run all tests
 * 
 * @author petruki
 */
@RunWith(Suite.class)
@SuiteClasses({ GameControllerTest.class, UITest.class })
public class TestSuite {

}
