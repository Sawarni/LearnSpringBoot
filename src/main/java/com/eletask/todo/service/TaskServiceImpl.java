package com.eletask.todo.service;

import com.eletask.todo.model.TaskDetail;
import com.eletask.todo.repository.TaskRepository;
import com.eletask.todo.response.TaskResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    // Create a logger instance for the current class

    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponse> getAllTasks() {
        log.info("getAllTasks is called");
        var tasks = taskRepository.findAll();
        return tasks.stream().map(TaskResponse::new).toList();
    }

    @Override
    public TaskResponse getTaskById(int id) {
        log.info("getTaskById is called for id : {}", id);
        var task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return null;
        }
        return new TaskResponse(task);
    }

    @Override
    public TaskResponse saveOrUpdateTask(TaskDetail task) {
        log.info("saveOrUpdateTask is called for task : {}", task);
        var savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getMatchingTasks(String keyword) {
        log.info("getMatchingTasks is called");
        List<TaskDetail> taskDetails = taskRepository.searchTask(keyword);
        return taskDetails.stream().map(TaskResponse::new).toList();
    }

}
