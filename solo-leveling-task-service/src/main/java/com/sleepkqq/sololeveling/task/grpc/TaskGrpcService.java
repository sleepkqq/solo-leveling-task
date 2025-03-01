package com.sleepkqq.sololeveling.task.grpc;

import com.sleepkqq.sololeveling.task.grpc.ExampleServiceGrpc.ExampleServiceImplBase;
import com.sleepkqq.sololeveling.task.service.TaskService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class TaskGrpcService extends ExampleServiceImplBase {

  private final TaskService taskService;

  @Override
  public void getExample(ExampleRequest request, StreamObserver<ExampleResponse> responseObserver) {
    var response = ExampleResponse.newBuilder()
        .setResponseId(request.getRequestId())
        .setMessage("Hello from gRPC service")
        .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
