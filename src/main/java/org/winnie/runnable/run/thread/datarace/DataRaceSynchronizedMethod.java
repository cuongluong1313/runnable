package org.winnie.runnable.run.thread.datarace;

import java.util.stream.IntStream;

public class DataRaceSynchronizedMethod {

    private static int COUNTER = 0;

    public static void main(String... args) throws Exception {
        final Runnable increaseCounterFunc = () -> IntStream
                .range(0, 100)
                .forEach(DataRaceSynchronizedMethod::increaseCounter);

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println(COUNTER);
    }

    private static synchronized void increaseCounter(int i) {
        ++COUNTER;
    }

}
