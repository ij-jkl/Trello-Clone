package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.common.ResponseBuilder;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dtos.models.BoardRequestDto;
import org.trello.trelloclone.models.Board;
import org.trello.trelloclone.models.Team;
import org.trello.trelloclone.repository.BoardRepository;
import org.trello.trelloclone.repository.TeamRepository;
import org.trello.trelloclone.service.BoardService;

@Service
@Slf4j
public abstract class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, TeamRepository teamRepository) {
        this.boardRepository = boardRepository;
        this.teamRepository = teamRepository;
    }

    private Board mapToBoard(BoardRequestDto dto) throws EntityNotFoundException {
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setDescription(dto.getDescription());
        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team", dto.getTeamId()));
            board.setTeam(team);
        }
        return board;
    }

    @Override
    public ResponseObjectJsonDto createBoard(BoardRequestDto boardRequestDto) {
        try {
            Board board = mapToBoard(boardRequestDto);
            validateBoard(board);
            Board savedBoard = boardRepository.save(board);
            return ResponseBuilder.buildCreatedResponse(savedBoard, "Board created successfully");
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        }
        catch (InvalidEntityException e) {
            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error creating board : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    private void validateBoard(Board board) throws InvalidEntityException {
        if (board.getTitle() == null || board.getTitle().isEmpty()) {
            throw new InvalidEntityException("Board title cannot be null or empty");
        }
        if (board.getDescription() == null || board.getDescription().isEmpty()) {
            throw new InvalidEntityException("Board description cannot be null or empty");
        }
    }
}


