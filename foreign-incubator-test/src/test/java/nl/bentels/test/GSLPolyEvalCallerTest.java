package nl.bentels.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class GSLPolyEvalCallerTest {

	private GSLPolyEvalCaller objectUnderTest;
	
	@BeforeEach
	public void createTestObject() {
		objectUnderTest = new GSLPolyEvalCaller();
	}
	
	@ParameterizedTest(name="Evaluation of x^2 is correct")
	@ArgumentsSource(PolynomialArgumentProvider.class)
	public void testPoly0(double d) throws Throwable {
		double expected = Math.pow(d, 2);
		assertEquals(expected, objectUnderTest.eval(d, 0, 0, 1));
	}

	@ParameterizedTest(name="Evaluation of -5x^2 + 13x + 26 is correct")
	@ArgumentsSource(PolynomialArgumentProvider.class)
	public void testPoly1(double d) throws Throwable {
		double expected = -5 * Math.pow(d, 2) + 13 * d + 26;
		assertEquals(expected, objectUnderTest.eval(d, 26, 13, -5));
	}
	
	@ParameterizedTest(name="Evaluation of X^3 - 12X^2 + 45X - 16 is correct")
	@ArgumentsSource(PolynomialArgumentProvider.class)
	public void testPoly2(double d) throws Throwable {
		double expected = Math.pow(d, 3) - 12 * Math.pow(d, 2) + 45 * d - 16;
		assertEquals(expected, objectUnderTest.eval(d, -16, 45, -12, 1));
	}
	
	private static class PolynomialArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return IntStream.range(-15, 15)
			.mapToDouble(i -> i)
			.mapToObj(d -> Double.valueOf(d))
			.map(Arguments::of);
		}
		
	}
	
}
