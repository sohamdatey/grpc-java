package org.example;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class RestClient {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest-test";

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<Long> time= () -> {
            Instant now = Instant.now();
            restTemplate.getForObject(url, String.class);
            Instant now2 = Instant.now();
            return Duration.between(now, now2).toMillis();
        };
        List<Future<Long>> futures  = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Future<Long> future = executorService.submit(time);
            futures.add(future);
        }

        OptionalDouble average = futures.stream().mapToLong((v -> {
            try {
                System.out.println("got something..");
                return v.get();
                
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })).average();
        executorService.shutdown();
        System.out.println(average.getAsDouble());
    }
}
