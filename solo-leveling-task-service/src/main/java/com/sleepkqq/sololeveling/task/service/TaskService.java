package com.sleepkqq.sololeveling.task.service;

import com.sleepkqq.sololeveling.task.model.Task;
import com.sleepkqq.sololeveling.task.repository.TaskRepository;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public Task saveTask(Task task) {
    return taskRepository.save(task);
  }

  public List<Task> findByTitle(String title) {
    return taskRepository.findByTitle(title);
  }

  public List<Task> findByExperienceBetween(int minExperience, int maxExperience) {
    return taskRepository.findByExperienceBetween(minExperience, maxExperience);
  }

  public List<Task> find(Collection<UUID> ids) {
    return taskRepository.findByIdIn(ids);
  }

  public void deleteTask(String id) {
    taskRepository.deleteById(id);
  }
}
