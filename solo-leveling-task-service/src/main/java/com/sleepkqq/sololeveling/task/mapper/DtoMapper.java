package com.sleepkqq.sololeveling.task.mapper;

import com.sleepkqq.sololeveling.avro.task.TaskTopic;
import java.util.Collection;
import java.util.function.Function;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

  public com.sleepkqq.sololeveling.proto.task.TaskTopic map(TaskTopic avro) {
    return com.sleepkqq.sololeveling.proto.task.TaskTopic.valueOf(avro.name());
  }

  public TaskTopic map(com.sleepkqq.sololeveling.proto.task.TaskTopic proto) {
    return TaskTopic.valueOf(proto.name());
  }

  public <T, R> Collection<R> mapCollection(Collection<T> collection, Function<T, R> mapper) {
    return StreamEx.of(collection).map(mapper).toList();
  }
}
