package com.sleepkqq.sololeveling.task.repository;

import com.sleepkqq.sololeveling.task.model.Task;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskRepository extends ElasticsearchRepository<Task, String> {

  List<Task> findByTitle(String title);

  List<Task> findByExperienceBetween(int minExperience, int maxExperience);

  List<Task> findByIdIn(Collection<UUID> ids);
}
