package com.client.book;

import com.book.Book;
import com.book.BookServiceGrpc;
import com.book.GetBookRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class UnaryClient {
    public static void main(String[] args) {

        //client connection setup
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();


        BookServiceGrpc.BookServiceBlockingStub bookServiceBlockingStub = BookServiceGrpc.newBlockingStub(channel);


        //calling service

        GetBookRequest build = GetBookRequest.newBuilder().setIsbn("12345").build();
        Book book = bookServiceBlockingStub.getBook(build);
        System.out.println(book);
    }
}