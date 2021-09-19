package nl.bentels.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GSLPolyComplexSolveCallerTest {

	private GSLPolyComplexSolveCaller objectUnderTest;
	
	@BeforeEach
	public void createTestObject() {
		objectUnderTest = new GSLPolyComplexSolveCaller();
	}
	
	@Test
	@DisplayName("Can solve for x^2")
	public void testPoly0() throws Throwable {
		assertArrayEquals(new double[] { 0.0D, 0.0D, 0D, -0D }, objectUnderTest.findRootsFor(0, 0, 1));
	}

	@Test
	@DisplayName("Can solve for -5x^2 + 13x + 26")
	public void testPoly1() throws Throwable {
		assertArrayEquals(new double[] { -1.3248809496813378D, 0.0D, 3.9248809496813375D, 0D }, objectUnderTest.findRootsFor(26, 13, -5));
	}

	@Test
	@DisplayName("Can solve for X^3 - 12X^2 + 45X - 16")
	public void testPoly2() throws Throwable {
		assertArrayEquals(new double[] { 0.3959914605075391D, 0.0D, 5.802004269746229D, 2.5964703280705796D, 5.802004269746229D, -2.5964703280705796D }, objectUnderTest.findRootsFor(-16, 45, -12, 1));
	}
}
