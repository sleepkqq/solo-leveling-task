package com.sleepkqq.sololeveling.task.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateTasksProducer {

  private static final String TOPIC = "generate-tasks";

  private final KafkaTemplate<String, GenerateTasksEvent> kafkaTemplate;

  public void generateTasks(GenerateTasksEvent event) {
    kafkaTemplate.send(TOPIC, event);
    log.info("<< Generate tasks event sent | transactionId={}", event.getTransactionId());
  }
}
