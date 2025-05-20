package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.ResponseBuilder;
import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Board;
import org.trello.trelloclone.repository.BoardRepository;
import org.trello.trelloclone.service.BoardService;

import java.util.List;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public ResponseObjectJsonDto createBoard(Board board) {
        try {
            validateBoard(board);
            Board savedBoard = boardRepository.save(board);

            return ResponseBuilder.buildCreatedResponse(savedBoard, "Board created successfully");
        } catch (InvalidEntityException e) {

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
                    .orElseThrow(() -> new EntityNotFoundException("Board ", id));

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
    public ResponseObjectJsonDto updateBoard(Long id, Board updatedBoard) {
        try {

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
            if (!boardRepository.existsById(id)) {
                throw new EntityNotFoundException("Board", id);
            }
            boardRepository.deleteById(id);

            return ResponseBuilder.buildSuccessResponse(null, "Board deleted successfully");
        } catch (EntityNotFoundException e) {

            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error deleting board : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    private void validateBoard(Board board) {
        if (board.getTitle() == null || board.getTitle().trim().isEmpty()) {
            throw new InvalidEntityException("Board title is required");
        }
        if (board.getTeam() == null) {
            throw new InvalidEntityException("Board must be associated with a team");
        }
    }

    private void updateBoardFields(Board existingBoard, Board updatedBoard) {
        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setDescription(updatedBoard.getDescription());
        existingBoard.setTeam(updatedBoard.getTeam());
    }
}