package org.winnie.runnable.run.thread;

import java.util.stream.IntStream;

public class RaceCondition {

    /**
     * A race condition happens when the order of events affects the correctness of the program.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        var firstThread = new Thread(() -> IntStream.range(0, 1000)
                .forEach(i -> System.out.println("First thread " + i)));
        var secondThread = new Thread(() -> IntStream.range(0, 1000)
                .forEach(i -> System.out.println("Second thread" + i)));
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
    }

}
