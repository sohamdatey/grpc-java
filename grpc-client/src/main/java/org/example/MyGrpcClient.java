package org.example;

import com.example.grpc.GreetRequest;
import com.example.grpc.GreetResponse;
import com.example.grpc.GreetServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MyGrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel;
        GreetServiceGrpc.GreetServiceBlockingStub blockingStub;

        channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = GreetServiceGrpc.newBlockingStub(channel);


        var request = GreetRequest.newBuilder().setName("soham").build();

        // Make gRPC call
        GreetResponse response = blockingStub.greet(request);

        System.out.println(response.getGreeting());



    }
}
