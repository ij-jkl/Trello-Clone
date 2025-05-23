package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.common.ResponseBuilder;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dtos.models.board.BoardRequestDto;
import org.trello.trelloclone.models.Board;
import org.trello.trelloclone.models.Team;
import org.trello.trelloclone.repository.BoardRepository;
import org.trello.trelloclone.repository.TeamRepository;
import org.trello.trelloclone.service.BoardService;

import java.util.List;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
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

    @Override
    public ResponseObjectJsonDto getBoardById(Long id) {
        try {
            return boardRepository.findById(id)
                    .map(board -> ResponseBuilder.buildSuccessResponse(board, "Board found successfully"))
                    .orElseThrow(() -> new EntityNotFoundException("Board", id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error getting board : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getAllBoards() {
        try {
            List<Board> boards = boardRepository.findAll();
            return ResponseBuilder.buildSuccessResponse(boards, "Boards list retrieved successfully");
        } catch (Exception e) {
            log.error("Error getting all boards : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getBoardsByTeamId(Long teamId) {
        try {
            if (teamId == null) {
                throw new InvalidEntityException("Team ID is required");
            }
            List<Board> boards = boardRepository.findByTeamId(teamId);
            return ResponseBuilder.buildSuccessResponse(boards, "Team boards retrieved successfully");
        } catch (InvalidEntityException e) {
            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error getting boards by team ID : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        try {
            Board updatedBoard = mapToBoard(boardRequestDto);
            validateBoard(updatedBoard);
            return boardRepository.findById(id)
                    .map(board -> {
                        updateBoardFields(board, updatedBoard);
                        Board savedBoard = boardRepository.save(board);
                        return ResponseBuilder.buildSuccessResponse(savedBoard, "Board updated successfully");
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Board", id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (InvalidEntityException e) {
            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error updating board: ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto deleteBoard(Long id) {
        try {
            return boardRepository.findById(id)
                    .map(board -> {
                        boardRepository.delete(board);
                        return ResponseBuilder.buildSuccessResponse(null, "Board deleted successfully");
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Board", id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting board: ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    private void updateBoardFields(Board board, Board updatedBoard) {
        board.setTitle(updatedBoard.getTitle());
        board.setDescription(updatedBoard.getDescription());
        board.setTeam(updatedBoard.getTeam());
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

