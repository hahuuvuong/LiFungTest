
package com.lifung.be.controller;

import com.lifung.be.entity.ToDoItem;
import com.lifung.be.request.ToDoItemRequest;
import com.lifung.be.service.ToDoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public ResponseEntity<ToDoItem> createToDo(@RequestBody ToDoItemRequest request) {
        ToDoItem newToDo = toDoService.createToDoItem(request);
        return ResponseEntity.ok(newToDo);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ToDoItem>> getUserToDos(@PathVariable Long userId) {
        List<ToDoItem> userToDos = toDoService.getToDosByUserId(userId);
        return ResponseEntity.ok(userToDos);
    }


    @PutMapping
    public ResponseEntity<ToDoItem> updateToDo(@RequestBody ToDoItemRequest request) {
        ToDoItem newToDo = toDoService.updateToDoItem(request);
        return ResponseEntity.ok(newToDo);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ToDoItem> completeToDo(@PathVariable Long id) {
        ToDoItem newToDo = toDoService.completeToDoItem(id);
        return ResponseEntity.ok(newToDo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteToDo(@PathVariable Long id) {
        return ResponseEntity.ok(toDoService.deleteToDoItem(id));
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<Page<ToDoItem>> getUserToDos(@PathVariable Long userId, Pageable pageable) {
        Page<ToDoItem> userToDos = toDoService.getToDosPageableByUserId(userId, pageable);
        return ResponseEntity.ok(userToDos);
    }
}