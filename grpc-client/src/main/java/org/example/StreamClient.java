package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import com.grpc.example.streamer.Streamer;
import com.grpc.example.streamer.StreamerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class StreamClient {
    public static void main(String[] args) {
        ManagedChannel channel;
        StreamerServiceGrpc.StreamerServiceBlockingStub stub;

        channel = ManagedChannelBuilder.forAddress("http://ldvpcs-MyLoa-SNKgpQGRwPU5-2114675533.us-east-1.elb.amazonaws.com"    , 9091)
                .usePlaintext()
                .build();

        stub = StreamerServiceGrpc.newBlockingStub(channel);
        Streamer.StreamRequest streamRequest = Streamer.StreamRequest.newBuilder().setNumberOfMessages(5).build();


        stub.streamMessages(streamRequest).forEachRemaining(res->{
            System.out.println(res.getMessage());
        });


    }
}