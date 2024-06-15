package org.winnie.runnable.run;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebFlux {

    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4, 5)
                .flatMap(integer -> Mono.just(++integer))
                .handle((integer, synchronousSink) -> {
                    if (integer != 4) {
                        System.out.println(integer);
                        synchronousSink.next(true);
                    } else {
                        synchronousSink.error(new RuntimeException("create order failed!"));
                    }
                })
                .then(Mono.fromCallable(() -> 100))
                .onErrorResume(throwable -> Mono.just(200))
                .subscribe();
    }

}
