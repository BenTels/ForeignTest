package nl.bentels.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import jdk.incubator.foreign.*;

public class GSLPolyComplexSolveCaller {

	private static final MethodHandle gsl_poly_complex_solve;
	private static final MethodHandle gsl_poly_complex_workspace_alloc;
	private static final MethodHandle gsl_poly_complex_workspace_free;
	
	static {
		System.loadLibrary("gsl");
		gsl_poly_complex_solve = CLinker.getInstance().downcallHandle(SymbolLookup.loaderLookup().lookup("gsl_poly_complex_solve").get(), 
				MethodType.methodType(int.class, new Class[] {MemoryAddress.class, int.class, MemoryAddress.class, MemoryAddress.class}), 
				FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_INT, CLinker.C_POINTER, CLinker.C_POINTER));
		
		gsl_poly_complex_workspace_alloc = CLinker.getInstance().downcallHandle(SymbolLookup.loaderLookup().lookup("gsl_poly_complex_workspace_alloc").get(), 
				MethodType.methodType(MemoryAddress.class, int.class), 
				FunctionDescriptor.of(CLinker.C_POINTER, CLinker.C_INT));
		
		
		gsl_poly_complex_workspace_free = CLinker.getInstance().downcallHandle(SymbolLookup.loaderLookup().lookup("gsl_poly_complex_workspace_free").get(), 
				MethodType.methodType (void.class, MemoryAddress.class), 
				FunctionDescriptor.ofVoid(CLinker.C_POINTER));
	}
	
	public double[] findRootsFor(double... coefficients) throws Throwable {
		int resultLength = 2 * (coefficients.length -1);
		double results[] = new double[resultLength];
		
		try (ResourceScope scope = ResourceScope.newConfinedScope()) {
			
			MemorySegment coeffs = MemorySegment.allocateNative(coefficients.length * CLinker.C_DOUBLE.byteSize(), scope);
			MemorySegment resultArr = MemorySegment.allocateNative(resultLength * CLinker.C_DOUBLE.byteSize(), scope);
			for (int ptr = 0; ptr < coefficients.length; ptr++) {
				MemoryAccess.setDoubleAtIndex(coeffs, ptr, coefficients[ptr]);
			}
			
			MemoryAddress workspace = (MemoryAddress) gsl_poly_complex_workspace_alloc.invokeExact(coefficients.length);
			int m = (int)gsl_poly_complex_solve.invokeExact(coeffs.address(), coefficients.length, workspace, resultArr.address());
			gsl_poly_complex_workspace_free.invokeExact(workspace);
			
			for (int ptr = 0; ptr < resultLength; ptr++) {
				results[ptr] = MemoryAccess.getDoubleAtIndex(resultArr, ptr);
			}
		}
		return results;
	}
	
}
