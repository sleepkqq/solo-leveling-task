package com.sleepkqq.sololeveling.task.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskKafkaProducer {

  private static final String TOPIC = "tasks";

  private final KafkaTemplate<String, GenerateTasksEvent> kafkaTemplate;

  public void generateTasks(GenerateTasksEvent event) {
    kafkaTemplate.send(TOPIC, event);
  }
}
