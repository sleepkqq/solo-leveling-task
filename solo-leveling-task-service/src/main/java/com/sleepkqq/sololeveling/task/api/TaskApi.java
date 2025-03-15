package com.sleepkqq.sololeveling.task.api;

import com.sleepkqq.sololeveling.proto.task.GetTasksRequest;
import com.sleepkqq.sololeveling.proto.task.GetTasksResponse;
import com.sleepkqq.sololeveling.proto.task.Task;
import com.sleepkqq.sololeveling.proto.task.TaskRarity;
import com.sleepkqq.sololeveling.proto.task.TaskServiceGrpc.TaskServiceImplBase;
import com.sleepkqq.sololeveling.task.mapper.DtoMapper;
import com.sleepkqq.sololeveling.task.service.TaskService;
import io.grpc.stub.StreamObserver;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import one.util.streamex.StreamEx;

@GrpcService
@RequiredArgsConstructor
public class TaskApi extends TaskServiceImplBase {

  private final TaskService taskService;
  private final DtoMapper dtoMapper;

  @Override
  public void getTasks(
      GetTasksRequest request,
      StreamObserver<GetTasksResponse> responseObserver
  ) {
    var tasks = taskService.find(StreamEx.of(request.getTaskIdList())
        .map(UUID::fromString)
        .toSet()
    );

    var response = GetTasksResponse.newBuilder()
        .addAllTask(StreamEx.of(tasks).map(t -> Task.newBuilder()
            .setId(t.getId().toString())
            .setTitle(t.getTitle())
            .setDescription(t.getDescription())
            .setExperience(t.getExperience())
            .setRarity(TaskRarity.valueOf(t.getRarity().name()))
            .addAllTopic(dtoMapper.mapCollection(t.getTopics(), dtoMapper::map))
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
