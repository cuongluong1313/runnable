package org.winnie.runnable.spring.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    @Async
    public CompletableFuture<String> performAsyncTask() {
        // Simulate a time-consuming task
        try {
            Thread.sleep(6000); // Sleep
            System.out.println("Async task completed!");
            return CompletableFuture.completedFuture("Async task completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Async task failed to complete!");
    }

}
