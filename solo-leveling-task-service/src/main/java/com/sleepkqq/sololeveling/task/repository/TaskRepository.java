package com.sleepkqq.sololeveling.task.repository;

import com.sleepkqq.sololeveling.task.model.Task;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskRepository extends ElasticsearchRepository<Task, String> {

  List<Task> findByTitle(String title);

  List<Task> findByExperienceBetween(int minExperience, int maxExperience);
}
