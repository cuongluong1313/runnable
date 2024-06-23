package org.winnie.runnable.spring.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/api")
public class RunnableRestController {

    @GetMapping("/something")
    public ResponseEntity<String> getSomeThing(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate startDate
    ) {
        return ResponseEntity.ok(String.format("Hello %s %s", name != null ? name : "World", startDate));
    }

}
