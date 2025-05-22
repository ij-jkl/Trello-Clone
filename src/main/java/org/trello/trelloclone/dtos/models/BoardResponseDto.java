package org.trello.trelloclone.dtos.models;

import lombok.Data;
import java.util.List;

@Data
public class BoardResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long teamId;
    private List<Long> taskIds;
}

