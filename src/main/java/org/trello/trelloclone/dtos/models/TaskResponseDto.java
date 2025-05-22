package org.trello.trelloclone.dtos.models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long boardId;
    private Long assignedToUserId;
    private LocalDate dueDate;
}

