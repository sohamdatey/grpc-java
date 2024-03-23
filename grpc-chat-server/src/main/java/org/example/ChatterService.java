package org.example;

import com.grpc.example.chatter.Chat;
import com.grpc.example.chatter.ChatterServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ChatterService extends ChatterServiceGrpc.ChatterServiceImplBase {

    private final List<StreamObserver<Chat.Message>> list = new CopyOnWriteArrayList<>();
    @Override
    public StreamObserver<Chat.Message> streamMessages(StreamObserver<Chat.Message> responseObserver) {
        list.add(responseObserver);

        return new StreamObserver<Chat.Message>() {
            @Override
            public void onNext(Chat.Message message) {
                System.out.println("message received:"+ message.getMessage()+", from: "+ message.getFrom());
                list.forEach(obsrver->{
                    obsrver.onNext(message);
                });
            }

            @Override
            public void onError(Throwable throwable) {
                list.remove(responseObserver);
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
            }
        };
    }

}