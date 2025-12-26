package com.eletask.todo.response;

import com.eletask.todo.model.TaskComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskCommentResponse {
    private int commentId;
    private String comment;
    private Date commentedOn;

    public TaskCommentResponse(TaskComment taskComment) {
        commentId = taskComment.getCommentId();
        comment = taskComment.getComment();
        commentedOn = taskComment.getCommentedOn();
    }

    public TaskComment toEntity() {
        var taskComment = new TaskComment();
        taskComment.setCommentedOn(commentedOn);
        taskComment.setCommentId(commentId);
        taskComment.setComment(comment);
        return taskComment;
    }
}
