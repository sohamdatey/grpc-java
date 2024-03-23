package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        final Server server= ServerBuilder.forPort(50051)
				 .addService(new GreetingService())
				 .build();;

		SpringApplication.run(Main.class, args);
		server.start();
		server.awaitTermination();
    }
}