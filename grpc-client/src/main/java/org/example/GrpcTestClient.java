package org.example;

import com.example.grpc.*;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GrpcTestClient {
    public static void main(String[] args) {

        ManagedChannel channel=ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .maxInboundMessageSize(6100000)
                .build();;
        TestGrpcServiceGrpc.TestGrpcServiceBlockingStub stub = TestGrpcServiceGrpc.newBlockingStub(channel);


        var request = Test.newBuilder().setTest("test").build();

        // Make gRPC call
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<Long> time= () -> {
            Instant now = Instant.now();
            stub.testMe(request);
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
                System.out.println("got something in grpc..");
                return v.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })).average();
        executorService.shutdown();
        System.out.println(average.getAsDouble());


    }

}

