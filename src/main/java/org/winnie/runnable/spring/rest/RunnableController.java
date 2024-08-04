package org.winnie.runnable.spring.rest;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winnie.runnable.spring.service.AsyncService;
import org.winnie.runnable.spring.utils.RunnableTypeEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api")
public class RunnableController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/runnable")
    public ResponseEntity<String> getRunnable(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) List<RunnableTypeEnum> runnableTypeEnums) throws InterruptedException {
        // Will be cut off by RequestTimeoutFilter
        Thread.sleep(6000);
        return ResponseEntity.ok(String.format("Hello %s %s %s", name != null ? name : "World", startDate, type != null ? type.toString() : "Hen" ));
    }

    // Not working
    @Timeout(5000)
    @GetMapping("/microprofile-fault-tolerance/runnable")
    public ResponseEntity<String> getRunnable2(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) List<RunnableTypeEnum> runnableTypeEnums) throws InterruptedException {
        Thread.sleep(6000);
        return ResponseEntity.ok(String.format("Hello %s %s %s", name != null ? name : "World", startDate, type != null ? type.toString() : "Hen" ));
    }

    // Not working, it doesn't allow to sleep inside.
    @TimeLimiter(name = "myTimeLimiter")
    @GetMapping("/resilience4j/runnable")
    public CompletableFuture<ResponseEntity<?>> getRunnable3(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) List<RunnableTypeEnum> runnableTypeEnums) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(String.format("Hello %s %s %s", name != null ? name : "World", startDate, type != null ? type.toString() : "Hen" ));
        });
    }

    @GetMapping("/mvc-async/runnable")
    public Callable<String> getRunnable4(@RequestParam(required = false) String name, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) List<String> type, @RequestParam(required = false) List<RunnableTypeEnum> runnableTypeEnums) throws InterruptedException {
        Thread.sleep(6000);
        return () -> String.format("Hello %s %s %s", name != null ? name : "World", startDate, type != null ? type.toString() : "Hen" );
    }

    @GetMapping("/async-task/trigger")
    public String triggerAsyncTask() {
        asyncService.performAsyncTask();
        return "Async task triggered!";
    }

    @GetMapping("/async-task/return")
    public CompletableFuture<String> returnAsyncTask() {
        return asyncService.performAsyncTask();
    }

}
