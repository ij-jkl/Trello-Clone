package org.trello.trelloclone.service;

import org.trello.trelloclone.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    Optional<Task> getTaskById(Long id);
    List<Task> getAllTasks();
    List<Task> getTasksByBoardId(Long boardId);
    List<Task> getTasksByAssignedUserId(Long userId);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
}
