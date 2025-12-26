package com.eletask.todo.response;

import com.eletask.todo.model.TaskComment;
import com.eletask.todo.model.TaskDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {

    private int taskId;

    private String taskName;

    private String taskDescription;

    private Date dueDate;

    private List<TaskCommentResponse> taskComments;

    public TaskResponse(TaskDetail taskDetail) {
        taskId = taskDetail.getTaskId();
        taskName = taskDetail.getTaskName();
        taskDescription = taskDetail.getTaskDescription();
        dueDate = taskDetail.getDueDate();
        taskComments = taskDetail
                .getTaskComments()
                .stream()
                .filter(t -> t.getComment() != null)
                .map(TaskCommentResponse::new)
                .toList();

    }

    public TaskDetail toEntity() {
        var task = new TaskDetail();
        task.setTaskId(taskId);
        task.setTaskDescription(taskDescription);
        task.setTaskName(taskName);
        task.setDueDate(dueDate);
        if (taskComments != null && !taskComments.isEmpty()) {
            task.setTaskComments(taskComments.stream().map(t -> {
                var obj = new TaskComment();
                obj.setCommentId(t.getCommentId());
                obj.setComment(t.getComment());
                obj.setCommentedOn(t.getCommentedOn());
                obj.setTaskDetail(task);
                return obj;
            }).toList());
        }
        return task;
    }
}
