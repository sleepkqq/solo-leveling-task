package com.sleepkqq.sololeveling.task.kafka;

import com.sleepkqq.sololeveling.task.model.Agility;
import com.sleepkqq.sololeveling.task.model.Intelligence;
import com.sleepkqq.sololeveling.task.model.Strength;
import com.sleepkqq.sololeveling.task.model.Task;
import com.sleepkqq.sololeveling.task.model.TaskRarity;
import com.sleepkqq.sololeveling.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskKafkaConsumer {

  private final TaskService taskService;

  @KafkaListener(topics = "save-tasks", groupId = "task-group")
  public void listen(SaveTasksEvent saveTasksEvent) {
    saveTasksEvent.getTasks()
        .forEach(t -> taskService.saveTask(new Task(
            t.getTitle(),
            t.getDescription(),
            t.getExperience(),
            TaskRarity.valueOf(t.getRarity().name()),
            Agility.valueOf(t.getAgility()),
            Strength.valueOf(t.getStrength()),
            Intelligence.valueOf(t.getIntelligence())
        )));
  }
}
