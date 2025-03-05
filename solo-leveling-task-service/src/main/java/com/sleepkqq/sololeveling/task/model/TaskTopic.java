package com.sleepkqq.sololeveling.task.model;

import java.util.Collection;
import java.util.Set;
import one.util.streamex.StreamEx;

public enum TaskTopic {
  PHYSICAL_ACTIVITY,
  MENTAL_HEALTH,
  EDUCATION,
  CREATIVITY,
  SOCIAL_SKILLS,
  HEALTHY_EATING,
  PRODUCTIVITY,
  EXPERIMENTS,
  ECOLOGY,
  TEAMWORK;

  public static Set<TaskTopic> fromKafka(
      Collection<com.sleepkqq.sololeveling.task.kafka.TaskTopic> topics
  ) {
    return StreamEx.of(topics).map(t -> TaskTopic.valueOf(t.name())).toSet();
  }

  public static Set<com.sleepkqq.sololeveling.task.grpc.TaskTopic> toGrpc(
      Set<TaskTopic> topics
  ) {
    return StreamEx.of(topics)
        .map(t -> com.sleepkqq.sololeveling.task.grpc.TaskTopic.valueOf(t.name()))
        .toSet();
  }
}
