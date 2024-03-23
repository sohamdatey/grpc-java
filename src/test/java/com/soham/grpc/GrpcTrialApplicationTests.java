package com.soham.grpc;

import com.example.grpc.GreetRequest;
import com.example.grpc.GreetResponse;
import com.example.grpc.GreetServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.*;

import static org.junit.Assert.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GrpcTrialApplicationTests {


    @Test
    void contextLoads() throws InterruptedException {

    }


}
