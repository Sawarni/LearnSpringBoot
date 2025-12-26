package com.eletask.todo.repository;

import com.eletask.todo.model.TaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskDetail, Integer> {

    @Query("SELECT t from TaskDetail t WHERE t.taskName LIKE  %:keyword% OR t.taskDescription LIKE %:keyword% ")
        //@EntityGraph(attributePaths = {"taskComments"})
    List<TaskDetail> searchTask(@Param("keyword") String keyword);
}
