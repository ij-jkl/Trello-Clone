package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Task;


public interface TaskService {
    ResponseObjectJsonDto createTask(Task task);
    ResponseObjectJsonDto getTaskById(Long id);
    ResponseObjectJsonDto getAllTasks();
    ResponseObjectJsonDto getTasksByBoardId(Long boardId);
    ResponseObjectJsonDto getTasksByAssignedUserId(Long userId);
    ResponseObjectJsonDto updateTask(Long id, Task updatedTask);
    ResponseObjectJsonDto deleteTask(Long id);
}
