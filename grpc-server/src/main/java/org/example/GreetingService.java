package org.example;

import com.example.grpc.GreetRequest;
import com.example.grpc.GreetResponse;
import com.example.grpc.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetingService extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        String name = request.getName();
        String greeting = "Hello, " + name + "!";

        GreetResponse response = GreetResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        sleep(5000);
        responseObserver.onCompleted();
    }

    private static void sleep(long time)  {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}