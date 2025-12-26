package com.eletask.todo.service;

import com.eletask.todo.model.TaskDetail;
import com.eletask.todo.response.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(int id);

    TaskResponse saveOrUpdateTask(TaskDetail task);

    List<TaskResponse> getMatchingTasks(String keyword);
}
