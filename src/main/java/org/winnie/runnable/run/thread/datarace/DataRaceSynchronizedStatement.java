package org.winnie.runnable.run.thread.datarace;

import java.util.stream.IntStream;

public class DataRaceSynchronizedStatement {

    private static int COUNTER = 0;

    public static void main(String... args) throws Exception {
        final Runnable increaseCounterFunc = () -> IntStream
                .range(0, 100)
                .forEach(DataRaceSynchronizedStatement::increaseCounter);

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println(COUNTER);
    }

    private static void increaseCounter(int i) {
        synchronized(DataRaceSynchronizedStatement.class) {
            ++COUNTER;
        }
    }

}
