package com.eletask.todo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
public class TaskDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int taskId;

    private String taskName;

    private String taskDescription;

    private Date dueDate;

    @OneToMany(mappedBy = "taskDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskComment> taskComments = new ArrayList<>();

    @Override
    public String toString() {
        return "TaskDetail{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }


}
