package org.trello.trelloclone.service;

import org.trello.trelloclone.models.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team createTeam(Team team);
    Optional<Team> getTeamById(Long id);
    List<Team> getAllTeams();
    Team updateTeam(Long id, Team updatedTeam);
    void deleteTeam(Long id);
}
