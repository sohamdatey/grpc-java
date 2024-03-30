package org.example;

import com.example.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@GrpcService
public class TestService extends TestGrpcServiceGrpc.TestGrpcServiceImplBase {
    @Override
    public void testMe(Test request, StreamObserver<Tests> responseObserver) {
        responseObserver.onNext(Tests.newBuilder().addAllTests(
                IntStream.range(0, 1000000).mapToObj(v->"test").collect(Collectors.toList())).build());
        responseObserver.onCompleted();
    }
}