package org.trello.trelloclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Task;
import org.trello.trelloclone.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create_task")
    public ResponseEntity<ResponseObjectJsonDto> createTask(@RequestBody Task task) {
        ResponseObjectJsonDto response = taskService.createTask(task);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_task_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> getTaskById(@PathVariable Long id) {
        ResponseObjectJsonDto response = taskService.getTaskById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_all_tasks")
    public ResponseEntity<ResponseObjectJsonDto> getAllTasks() {
        ResponseObjectJsonDto response = taskService.getAllTasks();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_task_by_{boardId}")
    public ResponseEntity<ResponseObjectJsonDto> getTasksByBoardId(@PathVariable Long boardId) {
        ResponseObjectJsonDto response = taskService.getTasksByBoardId(boardId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_task_by_{userId}")
    public ResponseEntity<ResponseObjectJsonDto> getTasksByAssignedUserId(@PathVariable Long userId) {
        ResponseObjectJsonDto response = taskService.getTasksByAssignedUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/update_task_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> updateTask(@PathVariable Long id, @RequestBody Task task) {
        ResponseObjectJsonDto response = taskService.updateTask(id, task);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/delete_task_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> deleteTask(@PathVariable Long id) {
        ResponseObjectJsonDto response = taskService.deleteTask(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}

