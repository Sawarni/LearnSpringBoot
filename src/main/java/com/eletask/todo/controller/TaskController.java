package com.eletask.todo.controller;

import com.eletask.todo.response.TaskResponse;
import com.eletask.todo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTaskDetails() {
        var tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/search/{keyword}")
    public ResponseEntity<List<TaskResponse>> getSearchedDetails(@PathVariable String keyword) {
        var tasks = taskService.getMatchingTasks(keyword);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTaskDetailsById(@PathVariable int id) {
        var task = taskService.getTaskById(id);
        if (task == null) {
            log.error("No task was found for id : {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> saveTask(@RequestBody TaskResponse task) {
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var savedTask = taskService.saveOrUpdateTask(task.toEntity());
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskResponse task, @PathVariable int id) {
        if (id <= 0 || taskService.getTaskById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (task == null || task.getTaskId() != id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var savedTask = taskService.saveOrUpdateTask(task.toEntity());
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }
}
