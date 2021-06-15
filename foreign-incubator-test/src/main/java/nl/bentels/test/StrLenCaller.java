package nl.bentels.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.LibraryLookup.Symbol;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

public class StrLenCaller {
	
	private static final MethodHandle strLen;
	
	static {
		Symbol symbol = LibraryLookup.ofDefault().lookup("strlen").get();
		MethodType type = MethodType.methodType(long.class, MemoryAddress.class);
		FunctionDescriptor descriptor = FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_POINTER);
		strLen = CLinker.getInstance().downcallHandle(symbol, type, descriptor);
	}

	public long lengthOf(String str) throws Throwable {
		try (MemorySegment cstr = CLinker.toCString(str)) {
			long invokeExact = (long)strLen.invokeExact(cstr.address());
			System.out.println(invokeExact);
			return (long)invokeExact;
		}
	}
} 
