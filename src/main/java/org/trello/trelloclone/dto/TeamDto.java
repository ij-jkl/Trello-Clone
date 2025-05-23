package org.trello.trelloclone.dto;

import java.util.List;

public class TeamDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> memberIds;
    private List<Long> boardIds;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Long> getMemberIds() {
        return memberIds;
    }
    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
    public List<Long> getBoardIds() {
        return boardIds;
    }
    public void setBoardIds(List<Long> boardIds) {
        this.boardIds = boardIds;
    }
}
