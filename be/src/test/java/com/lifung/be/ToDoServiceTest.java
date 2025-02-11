package com.lifung.be;

import com.lifung.be.entity.ToDoItem;
import com.lifung.be.repository.ToDoRepository;
import com.lifung.be.request.ToDoItemRequest;
import com.lifung.be.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateToDoItem() {
        ToDoItem newItem = ToDoItem .builder()
                .userId(1L)
                .title("Buy groceries")
                .description("Milk, eggs, bread")
                .createdAt(LocalDateTime.now())
                .build();

        when(toDoRepository.save(any(ToDoItem.class))).thenReturn(newItem);
        ToDoItemRequest toDoItemRequest = new ToDoItemRequest(1L, "Buy groceries", "Milk, eggs, bread");
        ToDoItem result = toDoService.createToDoItem(toDoItemRequest);

        assertNotNull(result);
        assertEquals("Buy groceries", result.getTitle());
        verify(toDoRepository, times(1)).save(any(ToDoItem.class));
    }


    @Test
    void testGetToDosByUserId() {
        ToDoItem todo1 = new ToDoItem(1L, 1L, "Task 1", "Description 1", LocalDateTime.now());
        ToDoItem todo2 = new ToDoItem(2L, 1L, "Task 2", "Description 2", LocalDateTime.now());

        when(toDoRepository.findByUserId(1L)).thenReturn(List.of(todo1, todo2));

        List<ToDoItem> result = toDoService.getToDosByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        verify(toDoRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testUpdateToDoItem() {
        ToDoItem existingToDo = new ToDoItem(1L, 1L, "Old Title", "Old Description", LocalDateTime.now());
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(existingToDo));
        when(toDoRepository.save(any(ToDoItem.class))).thenReturn(existingToDo);

        ToDoItemRequest toDoItemRequest = new ToDoItemRequest(1L, 1L, "New Title", "New Description");

        ToDoItem updatedToDo = toDoService.updateToDoItem(toDoItemRequest);

        assertEquals("New Title", updatedToDo.getTitle());
        assertEquals("New Description", updatedToDo.getDescription());
        verify(toDoRepository, times(1)).save(existingToDo);
    }

    @Test
    void testDeleteToDoItem() {
        ToDoItem existingToDo = new ToDoItem(1L, 1L, "Delete Me", "Remove this task", LocalDateTime.now());

        when(toDoRepository.findById(1L)).thenReturn(Optional.of(existingToDo));
        doNothing().when(toDoRepository).delete(existingToDo);

        toDoRepository.delete(existingToDo);
        verify(toDoRepository, times(1)).delete(existingToDo);
    }
}
