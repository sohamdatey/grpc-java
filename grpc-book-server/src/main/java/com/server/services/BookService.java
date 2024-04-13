package com.server.services;

import com.book.Book;
import com.book.BookServiceGrpc;
import com.book.GetBookByAuthor;
import com.book.GetBookRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class BookService extends BookServiceGrpc.BookServiceImplBase {

    private static final List<Book> books = List.of(
            book("12345", "Effective Java", 5f, "Joshua bloch"),
            book("12346", "Head First Java", 4.5f, "Kathy Sierra"),
            book("12347", "Head First Design Patterns", 4.6f, "Kathy Sierra"));

    //unary
    @Override
    public void getBook(GetBookRequest request, StreamObserver<Book> responseObserver) {
        String isbn = request.getIsbn();
        List<Book> list = books.stream().filter(b -> b.getIsbn().equalsIgnoreCase(isbn)).toList();

        if (list.size() != 1) {
            responseObserver.onError(new RuntimeException("Found none or found many, but not found one!"));
        }
        responseObserver.onNext(list.get(0));
        responseObserver.onCompleted();

    }

    //server streaming
    @Override
    public void getBooksByAuthor(GetBookByAuthor request, StreamObserver<Book> responseObserver) {

        String author = request.getAuthor();
        List<Book> list = books.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).toList();

        if (list.isEmpty()) {
            responseObserver.onError(new RuntimeException("No books found!"));
            return;
        }

        list.forEach(e->{
             sleep(2000);
             responseObserver.onNext(e);
        });
        responseObserver.onCompleted();

    }

    //client streaming
    @Override
    public StreamObserver<GetBookRequest> getMostRatedBook(StreamObserver<Book> responseObserver) {

        final List<GetBookRequest> bookRequests = new ArrayList<>();

        return new StreamObserver<GetBookRequest>() {
            @Override
            public void onNext(GetBookRequest getBookRequest) {
                validate(getBookRequest);
                bookRequests.add(getBookRequest);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                List<Book> foundBooks = BookService.getFoundBooks(bookRequests);

                Book bookWithMaxRating = foundBooks.stream()
                        .max((b1, b2) -> Float.compare(b1.getRating(), b2.getRating()))
                        .orElse(null);

                responseObserver.onNext(bookWithMaxRating);
                responseObserver.onCompleted();
            }


            private void validate(GetBookRequest getBookRequest) {
                List<Book> foundBooks = BookService.getFoundBooks(List.of(getBookRequest));
                if (foundBooks.isEmpty()) {
                    responseObserver.onError(new RuntimeException("unable to find highest rated book"));
                }
                if (bookRequests.size() > 100) {
                    responseObserver.onError(new RuntimeException("Too Many!"));
                }
            }
        };


    }


    //both streaming
    @Override
    public StreamObserver<GetBookRequest> getBooks(StreamObserver<Book> responseObserver) {


        return new StreamObserver<GetBookRequest>() {
            @Override
            public void onNext(GetBookRequest getBookRequest) {
                List<Book> foundBooks = BookService.getFoundBooks(List.of(getBookRequest));
                responseObserver.onNext(foundBooks.get(0));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

    }


    private static Book book(String isbn, String name, float rating, String author) {
        return Book.newBuilder().setAuthor(author).setIsbn(isbn).setRating(rating).setTitle(name).build();
    }


    private static List<Book> getFoundBooks(List<GetBookRequest> request) {
        List<String> isbnsToFind = request.stream().map(GetBookRequest::getIsbn).toList();

        return isbnsToFind.stream()
                .flatMap(isbn -> books.stream().filter(book -> book.getIsbn().equals(isbn)))
                .toList();
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }}