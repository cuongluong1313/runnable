package org.winnie.runnable.run.thread.datarace;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DataRaceAtomicVariable {

    private static AtomicInteger COUNTER = new AtomicInteger(0);

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        final Runnable increaseCounterFunc = () -> IntStream
                .range(0, 100)
                .forEach(DataRaceAtomicVariable::increaseCounter);

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println(COUNTER);
    }

    private static void increaseCounter(int i) {
        COUNTER.incrementAndGet();
    }

}
