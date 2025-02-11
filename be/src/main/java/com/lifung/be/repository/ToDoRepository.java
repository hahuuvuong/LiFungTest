package com.lifung.be.repository;

import com.lifung.be.entity.ToDoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoItem, Long> {
    List<ToDoItem> findByUserId(Long userId);

    @Query("SELECT u FROM ToDoItem u WHERE u.userId = :userId ")
    Page<ToDoItem> findToDoItemByUserId(@Param("userId") Long userId, Pageable pageable);
}