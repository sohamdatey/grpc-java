package org.example;

import com.grpc.example.chatter.Chat;
import com.grpc.example.chatter.ChatterServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class MyChatClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String username = scanner.nextLine();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext()
                .build();

        var stub = ChatterServiceGrpc.newStub(channel);


        StreamObserver<Chat.Message> serverObserver =
                stub.streamMessages(new StreamObserver<Chat.Message>() {
            @Override
            public void onNext(Chat.Message message) {
                System.out.println("----------------------------------");
                System.out.println(message.getFrom() +": "+ message.getMessage());
                System.out.println("----------------------------------");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });

        while(true){
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Enter something: ");
            String userInput = scanner.nextLine();

            serverObserver.onNext(Chat.Message.newBuilder().setMessage(userInput).setFrom(username).build());
        }


    }
}
