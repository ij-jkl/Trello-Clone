package org.trello.trelloclone.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<Long> teamIds;

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Long> getTeamIds() {
        return teamIds;
    }
    public void setTeamIds(List<Long> teamIds) {
        this.teamIds = teamIds;
    }
}
