package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.ResponseBuilder;
import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Team;
import org.trello.trelloclone.repository.TeamRepository;
import org.trello.trelloclone.service.TeamService;

import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public ResponseObjectJsonDto createTeam(Team team) {
        try {

            if (team.getName() == null || team.getName().trim().isEmpty()) {
                throw new InvalidEntityException("Team name is required");
            }
            if (team.getDescription() == null || team.getDescription().trim().isEmpty()) {
                throw new InvalidEntityException("Team description is required");
            }

            Team savedTeam = teamRepository.save(team);
            return ResponseBuilder.buildCreatedResponse(savedTeam, "Team created successfully");
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
                    .map(team -> ResponseBuilder.buildSuccessResponse(team, "Team found successfully"))
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

            return ResponseBuilder.buildSuccessResponse(teams, "Teams list retrieved successfully");
        } catch (Exception e) {

            log.error("Error getting all teams : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto updateTeam(Long id, Team updatedTeam) {
        try {

            if (updatedTeam.getName() == null || updatedTeam.getName().trim().isEmpty()) {
                throw new InvalidEntityException("Team name is required");
            }
            if (updatedTeam.getDescription() == null || updatedTeam.getDescription().trim().isEmpty()) {
                throw new InvalidEntityException("Team description is required");
            }

            return teamRepository.findById(id)
                    .map(team -> {
                        team.setName(updatedTeam.getName());
                        team.setDescription(updatedTeam.getDescription());
                        Team savedTeam = teamRepository.save(team);
                        return ResponseBuilder.buildSuccessResponse(savedTeam, "Team updated successfully");
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