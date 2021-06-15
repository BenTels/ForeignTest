package nl.bentels.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

public class GSLPolyEvalCaller {

	private static final MethodHandle gsl_poly_eval;

	static {
		gsl_poly_eval = CLinker.getInstance().downcallHandle(
				LibraryLookup.ofLibrary("gsl").lookup("gsl_poly_eval").get(),
				MethodType.methodType(double.class, new Class[] { MemoryAddress.class, int.class, double.class }),
				FunctionDescriptor.of(CLinker.C_DOUBLE, CLinker.C_POINTER, CLinker.C_INT, CLinker.C_DOUBLE));
	}

	public double eval(double solveFor, double... coefficients) throws Throwable {
		try (MemorySegment coeffArr = MemorySegment.allocateNative(coefficients.length * CLinker.C_DOUBLE.bitSize() / 8)) {
			for (int ptr = 0; ptr < coefficients.length; ptr++) {
				MemoryAccess.setDoubleAtIndex(coeffArr, ptr, coefficients[ptr]);
			}
			return (double) gsl_poly_eval.invokeExact(coeffArr.address(), coefficients.length, solveFor);
		}
	}

}
