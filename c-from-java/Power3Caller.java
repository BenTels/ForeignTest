import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.concurrent.ThreadLocalRandom;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SymbolLookup;

class Power3Caller {
    private static final MethodHandle power3;

    static {
        System.loadLibrary("power3");
        power3 = CLinker.getInstance().downcallHandle(
            SymbolLookup.loaderLookup().lookup("power3").get(),
            MethodType.methodType(long.class, long.class),
            FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_LONG));
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i < 100; i++) {
            long num = Math.abs(ThreadLocalRandom.current().nextLong());
            long result = (Long)power3.invoke(num);
            System.out.println("%d: %d to the power 3 is %d".formatted(i, num, result));
        }
    }
}