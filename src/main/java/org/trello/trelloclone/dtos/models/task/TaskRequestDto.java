package org.trello.trelloclone.dtos.models.task;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private String status;
    private Long boardId;
    private Long assignedToUserId;
    private LocalDate dueDate;
}

