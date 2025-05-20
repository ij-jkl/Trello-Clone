package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.ResponseBuilder;
import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Task;
import org.trello.trelloclone.repository.TaskRepository;
import org.trello.trelloclone.service.TaskService;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponseObjectJsonDto createTask(Task task) {
        try {

            validateTask(task);
            Task savedTask = taskRepository.save(task);

            return ResponseBuilder.buildCreatedResponse(savedTask, "Task created successfully");
        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error creating task : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getTaskById(Long id) {
        try {

            return taskRepository.findById(id)
                    .map(task -> ResponseBuilder.buildSuccessResponse(task, "Task found successfully"))
                    .orElseThrow(() -> new EntityNotFoundException("Task", id));

        } catch (EntityNotFoundException e) {

            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error getting task : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getAllTasks() {
        try {
            List<Task> tasks = taskRepository.findAll();

            return ResponseBuilder.buildSuccessResponse(tasks, "Tasks list retrieved successfully");
        } catch (Exception e) {

            log.error("Error getting all tasks : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getTasksByBoardId(Long boardId) {
        try {
            if (boardId == null) {
                throw new InvalidEntityException("Board ID is required");
            }
            List<Task> tasks = taskRepository.findByBoardId(boardId);

            return ResponseBuilder.buildSuccessResponse(tasks, "Board tasks retrieved successfully");
        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error getting tasks by board ID : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getTasksByAssignedUserId(Long userId) {
        try {
            if (userId == null) {
                throw new InvalidEntityException("User ID is required");
            }
            List<Task> tasks = taskRepository.findByAssignedToId(userId);

            return ResponseBuilder.buildSuccessResponse(tasks, "User assigned tasks retrieved successfully");
        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error getting tasks by user ID : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto updateTask(Long id, Task updatedTask) {
        try {
            validateTask(updatedTask);

            return taskRepository.findById(id)
                    .map(task -> {
                        updateTaskFields(task, updatedTask);
                        Task savedTask = taskRepository.save(task);
                        return ResponseBuilder.buildSuccessResponse(savedTask, "Task updated successfully");
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Task", id));

        } catch (EntityNotFoundException e) {

            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error updating task : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto deleteTask(Long id) {
        try {
            if (!taskRepository.existsById(id)) {
                throw new EntityNotFoundException("Task", id);
            }
            taskRepository.deleteById(id);

            return ResponseBuilder.buildSuccessResponse(null, "Task deleted successfully");
        } catch (EntityNotFoundException e) {

            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error deleting task : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new InvalidEntityException("Task title is required");
        }
        if (task.getStatus() == null) {
            throw new InvalidEntityException("Task status is required");
        }
        if (task.getBoard() == null) {
            throw new InvalidEntityException("Task must be associated with a board");
        }
    }

    private void updateTaskFields(Task existingTask, Task updatedTask) {
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setBoard(updatedTask.getBoard());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());
    }
}