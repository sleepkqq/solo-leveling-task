package com.sleepkqq.sololeveling.task.service;

import com.sleepkqq.sololeveling.avro.task.TaskTopic;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefineTaskTopicsService {

  public List<TaskTopic> defineTaskTopics() {
    return List.of(TaskTopic.PHYSICAL_ACTIVITY);
  }
}
