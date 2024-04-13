package com.client.book;

import com.book.Book;
import com.book.BookServiceGrpc;
import com.book.GetBookRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class BothStreaming {
    public static void main(String[] args) {

        //client connection setup
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();


        ExecutorService executorService = Executors.newCachedThreadPool();
        BookServiceGrpc.BookServiceStub bookServiceStub = BookServiceGrpc.newStub(channel).withExecutor(executorService);

        StreamObserver<GetBookRequest> bookRequestObserver = bookServiceStub.getBooks(new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                System.out.println(book);
            }

            @Override
            public void onError(Throwable throwable) {
                executorService.shutdown();
            }

            @Override
            public void onCompleted() {
                System.out.println("ending gracefully.");
                executorService.shutdown();
            }
        });

        Stream.of("12345", "12347").forEach(isbn -> {
            bookRequestObserver.onNext(GetBookRequest.newBuilder().setIsbn(isbn).build());
        });
        bookRequestObserver.onCompleted();


        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
    }


    private static void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}