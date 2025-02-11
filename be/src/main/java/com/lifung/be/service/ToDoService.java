package com.lifung.be.service;

import com.lifung.be.entity.ToDoItem;
import com.lifung.be.repository.ToDoRepository;
import com.lifung.be.request.ToDoItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDoItem createToDoItem(ToDoItemRequest request) {
        ToDoItem newItem = ToDoItem.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        return toDoRepository.save(newItem);
    }

    public ToDoItem updateToDoItem(ToDoItemRequest request) {
        ToDoItem item = toDoRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Todo item not exist with id: " + request.getId()));
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        return toDoRepository.save(item);
    }

    public ToDoItem completeToDoItem(Long id) {
        ToDoItem item = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo item not exist with id: " + id));
        item.setCompleted(true);
        return toDoRepository.save(item);
    }

    public boolean deleteToDoItem(Long id) {
        ToDoItem item = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo item not exist with id: " + id));
        toDoRepository.delete(item);
        return true;
    }

    public Page<ToDoItem> getToDosPageableByUserId(Long userId, Pageable pageable) {
        return toDoRepository.findToDoItemByUserId(userId, pageable);
    }

    public List<ToDoItem> getToDosByUserId(Long userId) {
        return toDoRepository.findByUserId(userId);
    }
}