/*
 * AllTests.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 19, 2016 at 8:09:55 PM
 */
package org.apache.bazaar;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.mycila.junit.concurrent.ConcurrentSuiteRunner;

/**
 * AllTests provides a JUnit test suite to execute all Bazaar tests.
 */
@RunWith(ConcurrentSuiteRunner.class)
@SuiteClasses({ BazaarManagerTests.class, BazaarTests.class, CategoryTests.class, ItemTests.class, BidderTests.class,
		BidTests.class })
public class AllTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for AllTests
	 */
	public AllTests() {
		super();
	}

	/**
	 * Main execution thread
	 *
	 * @param args The execution arguments
	 */
	public static void main(final String[] args) {
		final Result testResults = JUnitCore.runClasses(AllTests.class);
		System.out.println("Executed " + testResults.getRunCount() + " total tests in " + testResults.getRunTime()
				+ " milliseconds");
		System.out.println(testResults.getRunCount() - testResults.getFailureCount() + "/" + testResults.getRunCount()
				+ " tests executed successfully");
		System.out.println(testResults.getFailureCount() + "/" + testResults.getRunCount() + " tests failed execution");

	}
}
