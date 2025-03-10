package com.sleepkqq.sololeveling.task.controller;

import com.sleepkqq.sololeveling.task.kafka.GenerateTask;
import com.sleepkqq.sololeveling.task.kafka.GenerateTasksEvent;
import com.sleepkqq.sololeveling.task.kafka.GenerateTasksProducer;
import com.sleepkqq.sololeveling.task.service.CalculateTaskRarityService;
import com.sleepkqq.sololeveling.task.service.DefineTaskTopicsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

  private final GenerateTasksProducer generateTasksProducer;
  private final CalculateTaskRarityService calculateTaskRarityService;
  private final DefineTaskTopicsService defineTaskTopicsService;

  @GetMapping("/send")
  public ResponseEntity<Void> test() {
    var userId = 123123123L;
    var userLevel = 85;
    var generateTasksCount = 2;
    var generateTasks = StreamEx.generate(() -> new GenerateTask(
            calculateTaskRarityService.calculateTaskRarity(userLevel),
            defineTaskTopicsService.defineTaskTopics()
        ))
        .limit(generateTasksCount)
        .toList();

    generateTasksProducer.generateTasks(new GenerateTasksEvent(
        UUID.randomUUID().toString(),
        userId,
        generateTasks
    ));

    return ResponseEntity.ok().build();
  }
}
