package nl.bentels.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.incubator.foreign.*;

public class GSLPolyEvalCaller {

	private static final MethodHandle gsl_poly_eval;

	static {
		System.loadLibrary("gsl");
		gsl_poly_eval = CLinker.getInstance().downcallHandle(
				SymbolLookup.loaderLookup()
					// .ofLibrary("gsl")
					.lookup("gsl_poly_eval").get(),
				MethodType.methodType(double.class, new Class[] { MemoryAddress.class, int.class, double.class }),
				FunctionDescriptor.of(CLinker.C_DOUBLE, CLinker.C_POINTER, CLinker.C_INT, CLinker.C_DOUBLE));
	}

	public double eval(double solveFor, double... coefficients) throws Throwable {
		try (ResourceScope scope = ResourceScope.newConfinedScope()) {
			MemorySegment coeffArr = 
					MemorySegment.allocateNative(coefficients.length * CLinker.C_DOUBLE.bitSize() / 8, scope);
			for (int ptr = 0; ptr < coefficients.length; ptr++) {
				MemoryAccess.setDoubleAtIndex(coeffArr, ptr, coefficients[ptr]);
			}
			return (double) gsl_poly_eval.invokeExact(coeffArr.address(), coefficients.length, solveFor);
		}
	}

}
