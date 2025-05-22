package org.trello.trelloclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Team;
import org.trello.trelloclone.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/create_team")
    public ResponseEntity<ResponseObjectJsonDto> createTeam(@RequestBody Team team) {
        ResponseObjectJsonDto response = teamService.createTeam(team);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_team_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> getTeamById(@PathVariable Long id) {
        ResponseObjectJsonDto response = teamService.getTeamById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("get_all_teams")
    public ResponseEntity<ResponseObjectJsonDto> getAllTeams() {
        ResponseObjectJsonDto response = teamService.getAllTeams();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/update_team_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        ResponseObjectJsonDto response = teamService.updateTeam(id, team);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/delete_team_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> deleteTeam(@PathVariable Long id) {
        ResponseObjectJsonDto response = teamService.deleteTeam(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}