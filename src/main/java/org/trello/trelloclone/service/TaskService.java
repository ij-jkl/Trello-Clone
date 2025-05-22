package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dtos.models.TaskRequestDto;

public interface TaskService {
    ResponseObjectJsonDto createTask(TaskRequestDto taskRequestDto);
    ResponseObjectJsonDto getTaskById(Long id);
    ResponseObjectJsonDto getAllTasks();
    ResponseObjectJsonDto getTasksByBoardId(Long boardId);
    ResponseObjectJsonDto getTasksByAssignedUserId(Long userId);
    ResponseObjectJsonDto updateTask(Long id, TaskRequestDto taskRequestDto);
    ResponseObjectJsonDto deleteTask(Long id);
}
