package com.lifung.be;

import com.lifung.be.controller.ToDoController;
import com.lifung.be.entity.ToDoItem;
import com.lifung.be.request.ToDoItemRequest;
import com.lifung.be.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ToDoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testGetUserToDos() throws Exception {
        ToDoItem todo1 = new ToDoItem(1L, 1L, "Task 1", "Description 1", LocalDateTime.now());
        ToDoItem todo2 = new ToDoItem(2L, 1L, "Task 2", "Description 2", LocalDateTime.now());

        when(toDoService.getToDosByUserId(1L)).thenReturn(List.of(todo1, todo2));

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void testCreateToDo() throws Exception {
        ToDoItem newToDo = new ToDoItem(1L, 1L, "Buy groceries", "Milk, eggs, bread", LocalDateTime.now());

        ToDoItemRequest toDoItemRequest = new ToDoItemRequest(1L, "Buy groceries", "Milk, eggs, bread");
        when(toDoService.createToDoItem(toDoItemRequest)).thenReturn(newToDo);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"title\": \"Buy groceries\", \"description\": \"Milk, eggs, bread\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Buy groceries"));
    }

    @Test
    void testUpdateToDo() throws Exception {
        ToDoItemRequest request = new ToDoItemRequest(1L, "Updated Task", "Updated Description");
        ToDoItem updatedToDo = new ToDoItem(1L, 1L, "Updated Task", "Updated Description", LocalDateTime.now());

        when(toDoService.updateToDoItem(any(ToDoItemRequest.class))).thenReturn(updatedToDo);

        mockMvc.perform(put("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"title\": \"Updated Task\", \"description\": \"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void testCompleteToDo() throws Exception {
        ToDoItem completedToDo = new ToDoItem(1L, 1L, "Completed Task", "This task is completed", LocalDateTime.now());

        when(toDoService.completeToDoItem(1L)).thenReturn(completedToDo);

        mockMvc.perform(patch("/api/todos/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Completed Task"));
    }

    @Test
    void testDeleteToDo() throws Exception {
        when(toDoService.deleteToDoItem(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAllContacts_DefaultParameters() {

        ToDoItem todo1 = new ToDoItem(1L, 1L, "Task 1", "Description 1", LocalDateTime.now());
        ToDoItem todo2 = new ToDoItem(2L, 1L, "Task 2", "Description 2", LocalDateTime.now());

        Pageable pageable = PageRequest.of(0, 2);
        Page<ToDoItem> toDoPage = new PageImpl<>(List.of(todo1, todo2), pageable , 2);

        when(toDoService.getToDosPageableByUserId(1L, pageable)).thenReturn(toDoPage);

        ResponseEntity<Page<ToDoItem>> response = toDoController.getUserToDos(1L, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
    }

}
