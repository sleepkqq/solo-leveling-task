package com.sleepkqq.sololeveling.task.grpc;

import com.sleepkqq.sololeveling.task.grpc.TaskServiceGrpc.TaskServiceImplBase;
import com.sleepkqq.sololeveling.task.model.TaskTopic;
import com.sleepkqq.sololeveling.task.service.TaskService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import one.util.streamex.StreamEx;

@GrpcService
@RequiredArgsConstructor
public class TaskGrpcService extends TaskServiceImplBase {

  private final TaskService taskService;

  @Override
  public void getTasks(
      GetTasksRequest request,
      StreamObserver<GetTasksResponse> responseObserver
  ) {
    var tasks = taskService.find(request.getTaskIdList());

    var response = GetTasksResponse.newBuilder()
        .addAllTask(StreamEx.of(tasks).map(t -> Task.newBuilder()
            .setId(t.getId())
            .setTitle(t.getTitle())
            .setDescription(t.getDescription())
            .setExperience(t.getExperience())
            .setRarity(TaskRarity.valueOf(t.getRarity().name()))
            .addAllTopic(TaskTopic.toGrpc(t.getTopics()))
            .setAgility(t.getAgility().getValue())
            .setStrength(t.getStrength().getValue())
            .setIntelligence(t.getIntelligence().getValue())
            .build()
        ))
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
