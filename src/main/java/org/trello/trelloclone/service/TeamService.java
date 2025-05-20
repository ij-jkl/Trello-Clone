package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Team;


public interface TeamService {
    ResponseObjectJsonDto createTeam(Team team);
    ResponseObjectJsonDto getTeamById(Long id);
    ResponseObjectJsonDto getAllTeams();
    ResponseObjectJsonDto updateTeam(Long id, Team updatedTeam);
    ResponseObjectJsonDto deleteTeam(Long id);
}