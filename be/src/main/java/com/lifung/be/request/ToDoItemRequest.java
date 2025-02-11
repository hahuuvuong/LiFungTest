
package com.lifung.be.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoItemRequest {
    private Long id;
    private Long userId;
    private String title;
    private String description;

    public ToDoItemRequest(Long userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public ToDoItemRequest(Long id, Long userId, String title, String description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }
}
