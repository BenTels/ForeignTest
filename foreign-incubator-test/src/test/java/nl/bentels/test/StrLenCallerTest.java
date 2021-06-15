package nl.bentels.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class StrLenCallerTest {
	
	private StrLenCaller objectUnderTest;
	
	@BeforeEach
	public void setUp() {
		objectUnderTest = new StrLenCaller();
	}
	
	@ParameterizedTest(name="StrLen is called correctly")
	@ArgumentsSource(TestArguments.class)
	void test(Pair<String, Long> inputAndExpected) throws Throwable {
		assertEquals(inputAndExpected.getRight(), objectUnderTest.lengthOf(inputAndExpected.getLeft()));
	}

	private static class TestArguments implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(
					ImmutablePair.of("Hello", 5L)
					, ImmutablePair.of("Hello World", 11L)
					, ImmutablePair.of("Live Long And Prosper", 21L)
					).map(Arguments::of);
		}
		
	}
}
