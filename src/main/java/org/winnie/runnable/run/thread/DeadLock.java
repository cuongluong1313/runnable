package org.winnie.runnable.run.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class DeadLock {

    private static final Lock wKey = new ReentrantLock();
    private static final Lock eKey = new ReentrantLock();

    private static int openCaseCount = 0;

    /**
     * XThread lock wkey first then try to lock ekey, YThread lock ekey first then try to lock wkey
     * But XThread is waiting for YThread to release ekey, YThread is waiting for XThread wkey
     * Solution: Modify the order of locks from one to the same as another.
     * @param args
     */
    public static void main(String... args) {
        //
        Thread xThread = new Thread(() -> {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        wKey.lock();
                        eKey.lock();
                        openCase();
                        eKey.unlock();
                        wKey.unlock();
                    });
        });
       //
        Thread yThread = new Thread(() -> {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        eKey.lock();
                        wKey.lock();
                        openCase();
                        wKey.unlock();
                        eKey.unlock();
                    });
        });
        xThread.start();
        yThread.start();
    }

    private static void openCase() {
        ++openCaseCount;
        System.out.println("Opened case! Count: " + openCaseCount);
    }

}
