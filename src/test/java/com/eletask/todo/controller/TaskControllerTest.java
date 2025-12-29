package com.eletask.todo.controller;

import com.eletask.todo.response.TaskResponse;
import com.eletask.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController controller;

    @BeforeEach
    void setUp() {
        // Mockito annotations are initialized by the extension, nothing to do here
    }

    @Test
    @DisplayName("getTaskDetails returns list from service")
    void getTaskDetails_returnsList() {
        var t1 = new TaskResponse();
        t1.setTaskId(1);
        t1.setTaskName("Task 1");

        var t2 = new TaskResponse();
        t2.setTaskId(2);
        t2.setTaskName("Task 2");

        when(taskService.getAllTasks()).thenReturn(List.of(t1, t2));

        ResponseEntity<List<TaskResponse>> resp = controller.getTaskDetails();

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(2, resp.getBody().size());
        assertEquals(1, resp.getBody().get(0).getTaskId());
    }

    @Test
    @DisplayName("getSearchedDetails returns matches")
    void getSearchedDetails_returnsMatches() {
        var t = new TaskResponse();
        t.setTaskId(5);
        when(taskService.getMatchingTasks("kw")).thenReturn(List.of(t));

        ResponseEntity<List<TaskResponse>> resp = controller.getSearchedDetails("kw");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
    }

    @Test
    @DisplayName("getTaskDetailsById - found")
    void getTaskDetailsById_found() {
        var t = new TaskResponse();
        t.setTaskId(10);
        when(taskService.getTaskById(10)).thenReturn(t);

        ResponseEntity<TaskResponse> resp = controller.getTaskDetailsById(10);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(10, resp.getBody().getTaskId());
    }

    @Test
    @DisplayName("getTaskDetailsById - not found returns 404")
    void getTaskDetailsById_notFound() {
        when(taskService.getTaskById(99)).thenReturn(null);

        ResponseEntity<TaskResponse> resp = controller.getTaskDetailsById(99);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    @DisplayName("saveTask - null body returns 400")
    void saveTask_nullBody_returnsBadRequest() {
        ResponseEntity<TaskResponse> resp = controller.saveTask(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DisplayName("saveTask - valid returns 201")
    void saveTask_valid_returnsCreated() {
        var req = new TaskResponse();
        req.setTaskName("New");
        var saved = new TaskResponse();
        saved.setTaskId(100);
        when(taskService.saveOrUpdateTask(any())).thenReturn(saved);

        ResponseEntity<TaskResponse> resp = controller.saveTask(req);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(100, resp.getBody().getTaskId());
    }

    @Test
    @DisplayName("updateTask - invalid id returns 400")
    void updateTask_invalidId_returnsBadRequest() {
        var req = new TaskResponse();
        req.setTaskId(0);
        ResponseEntity<TaskResponse> resp = controller.updateTask(req, 0);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DisplayName("updateTask - not found returns 400")
    void updateTask_notFound_returnsBadRequest() {
        var req = new TaskResponse();
        req.setTaskId(5);
        when(taskService.getTaskById(5)).thenReturn(null);

        ResponseEntity<TaskResponse> resp = controller.updateTask(req, 5);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DisplayName("updateTask - mismatched id returns 400")
    void updateTask_mismatchedId_returnsBadRequest() {
        var req = new TaskResponse();
        req.setTaskId(6);
        when(taskService.getTaskById(5)).thenReturn(new TaskResponse());

        ResponseEntity<TaskResponse> resp = controller.updateTask(req, 5);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DisplayName("updateTask - valid returns 200")
    void updateTask_valid_returnsOk() {
        var req = new TaskResponse();
        req.setTaskId(7);
        when(taskService.getTaskById(7)).thenReturn(req);
        var updated = new TaskResponse();
        updated.setTaskId(7);
        updated.setTaskName("After");
        when(taskService.saveOrUpdateTask(any())).thenReturn(updated);

        ResponseEntity<TaskResponse> resp = controller.updateTask(req, 7);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("After", resp.getBody().getTaskName());
    }
}
