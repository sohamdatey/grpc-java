package org.example;

import com.grpc.example.streamer.Streamer;
import com.grpc.example.streamer.StreamerServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.stream.IntStream;

public class StreamerService extends StreamerServiceGrpc.StreamerServiceImplBase {


    @Override
    public void streamMessages(Streamer.StreamRequest request, StreamObserver<Streamer.StreamResponse> responseObserver) {
        int numberOfMessages = request.getNumberOfMessages();
        System.out.println("Server hit");
        IntStream.range(0,numberOfMessages).forEach(i->{
            responseObserver.onNext(Streamer.StreamResponse.newBuilder().setMessage("Message:"+i).build());
            sleep(2000);
        });
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