package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.common.ResponseBuilder;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Team;
import org.trello.trelloclone.models.User;
import org.trello.trelloclone.models.Board;
import org.trello.trelloclone.dto.TeamDto;
import org.trello.trelloclone.repository.TeamRepository;
import org.trello.trelloclone.repository.UserRepository;
import org.trello.trelloclone.repository.BoardRepository;
import org.trello.trelloclone.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    private TeamDto toDto(Team team) {
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDescription(team.getDescription());
        dto.setMemberIds(
            team.getMembers() != null
                ? team.getMembers().stream().map(User::getId).collect(Collectors.toList())
                : null
        );
        dto.setBoardIds(
            team.getBoards() != null
                ? team.getBoards().stream().map(Board::getId).collect(Collectors.toList())
                : null
        );
        return dto;
    }

    private Team toEntity(TeamDto dto) {
        Team team = new Team();
        team.setId(dto.getId());
        team.setName(dto.getName());
        team.setDescription(dto.getDescription());
        if (dto.getMemberIds() != null) {
            List<User> members = dto.getMemberIds().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
            team.setMembers(members);
        }
        if (dto.getBoardIds() != null) {
            List<Board> boards = dto.getBoardIds().stream()
                .map(boardRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
            team.setBoards(boards);
        }
        return team;
    }

    @Override
    public ResponseObjectJsonDto createTeam(TeamDto teamDto) {
        try {
            if (teamDto.getName() == null || teamDto.getName().trim().isEmpty()) {
                throw new InvalidEntityException("Team name is required");
            }
            if (teamDto.getDescription() == null || teamDto.getDescription().trim().isEmpty()) {
                throw new InvalidEntityException("Team description is required");
            }
            Team team = toEntity(teamDto);
            Team savedTeam = teamRepository.save(team);
            return ResponseBuilder.buildCreatedResponse(toDto(savedTeam), "Team created successfully");
        } catch (InvalidEntityException e) {
            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error creating team : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getTeamById(Long id) {
        try {
            return teamRepository.findById(id)
                    .map(team -> ResponseBuilder.buildSuccessResponse(toDto(team), "Team found successfully"))
                    .orElseThrow(() -> new EntityNotFoundException("Team ", id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error getting team : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getAllTeams() {
        try {
            List<Team> teams = teamRepository.findAll();
            List<TeamDto> dtos = teams.stream().map(this::toDto).collect(Collectors.toList());
            return ResponseBuilder.buildSuccessResponse(dtos, "Teams list retrieved successfully");
        } catch (Exception e) {
            log.error("Error getting all teams : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto updateTeam(Long id, TeamDto updatedTeamDto) {
        try {
            if (updatedTeamDto.getName() == null || updatedTeamDto.getName().trim().isEmpty()) {
                throw new InvalidEntityException("Team name is required");
            }
            if (updatedTeamDto.getDescription() == null || updatedTeamDto.getDescription().trim().isEmpty()) {
                throw new InvalidEntityException("Team description is required");
            }
            return teamRepository.findById(id)
                    .map(team -> {
                        team.setName(updatedTeamDto.getName());
                        team.setDescription(updatedTeamDto.getDescription());
                        if (updatedTeamDto.getMemberIds() != null) {
                            List<User> members = updatedTeamDto.getMemberIds().stream()
                                .map(userRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList());
                            team.setMembers(members);
                        }
                        if (updatedTeamDto.getBoardIds() != null) {
                            List<Board> boards = updatedTeamDto.getBoardIds().stream()
                                .map(boardRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList());
                            team.setBoards(boards);
                        }
                        Team savedTeam = teamRepository.save(team);
                        return ResponseBuilder.buildSuccessResponse(toDto(savedTeam), "Team updated successfully");
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Team", id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (InvalidEntityException e) {
            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error updating team : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto deleteTeam(Long id) {
        try {
            if (!teamRepository.existsById(id)) {
                throw new EntityNotFoundException("Team ", id);
            }
            teamRepository.deleteById(id);
            return ResponseBuilder.buildSuccessResponse(null, "Team deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting team : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }
}
