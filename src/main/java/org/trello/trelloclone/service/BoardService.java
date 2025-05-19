package org.trello.trelloclone.service;

import org.trello.trelloclone.models.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    Board createBoard(Board board);
    Optional<Board> getBoardById(Long id);
    List<Board> getAllBoards();
    List<Board> getBoardsByTeamId(Long teamId);
    Board updateBoard(Long id, Board updatedBoard);
    void deleteBoard(Long id);
}
