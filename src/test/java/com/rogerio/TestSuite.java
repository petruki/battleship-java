package com.rogerio;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.rogerio.controller.GameControllerTest;
import com.rogerio.ui.UITest;

/**
 * Run all tests
 * 
 * @author petruki
 */
@RunWith(Suite.class)
@SuiteClasses({ GameControllerTest.class, UITest.class })
public class TestSuite {

}
