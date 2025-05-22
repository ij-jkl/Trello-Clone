package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dto.TeamDto;

public interface TeamService {
    ResponseObjectJsonDto createTeam(TeamDto teamDto);
    ResponseObjectJsonDto getTeamById(Long id);
    ResponseObjectJsonDto getAllTeams();
    ResponseObjectJsonDto updateTeam(Long id, TeamDto updatedTeamDto);
    ResponseObjectJsonDto deleteTeam(Long id);
}
