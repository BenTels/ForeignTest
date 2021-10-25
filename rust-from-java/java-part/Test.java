import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.concurrent.ThreadLocalRandom;

import jdk.incubator.foreign.*;

class Test {
    
    private static final MethodHandle pow3;

    static {
        System.loadLibrary("rustlib");
        pow3 = CLinker.getInstance().downcallHandle(
            SymbolLookup.loaderLookup().lookup("pow3").get(),
            MethodType.methodType(long.class, new Class[]{long.class}), 
            FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_LONG));
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i < 100; i++) {
            long num = Math.abs( ThreadLocalRandom.current().nextInt(0, 100_000));
            System.out.println("%d: %d to the power 3 is %d".formatted(i, num, pow3.invoke(num)));
        }
    }

}