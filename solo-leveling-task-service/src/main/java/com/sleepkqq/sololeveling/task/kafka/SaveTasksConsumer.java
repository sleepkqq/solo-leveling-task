package com.sleepkqq.sololeveling.task.kafka;

import com.sleepkqq.sololeveling.task.model.Agility;
import com.sleepkqq.sololeveling.task.model.Intelligence;
import com.sleepkqq.sololeveling.task.model.Strength;
import com.sleepkqq.sololeveling.task.model.Task;
import com.sleepkqq.sololeveling.task.model.TaskRarity;
import com.sleepkqq.sololeveling.task.model.TaskTopic;
import com.sleepkqq.sololeveling.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveTasksConsumer {

  private final TaskService taskService;

  @KafkaListener(topics = "save-tasks", groupId = "task-group")
  public void listen(SaveTasksEvent event) {
    log.info(">> Start saving tasks | transactionId={}", event.getTransactionId());
    var taskIds = StreamEx.of(event.getTasks())
        .map(t -> taskService.saveTask(new Task(
            t.getTitle(),
            t.getDescription(),
            t.getExperience(),
            TaskRarity.valueOf(t.getRarity().name()),
            TaskTopic.fromKafka(t.getTopics()),
            Agility.valueOf(t.getAgility()),
            Strength.valueOf(t.getStrength()),
            Intelligence.valueOf(t.getIntelligence())
        )))
        .map(Task::getId)
        .toList();

    log.info("<< Tasks successfully saved | transactionId={}", event.getTransactionId());
  }
}
