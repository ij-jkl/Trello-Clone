package org.trello.trelloclone.dtos.models.board;

import lombok.Data;

@Data
public class BoardRequestDto {
    private String title;
    private String description;
    private Long teamId;
}

