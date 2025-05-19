package org.trello.trelloclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trello.trelloclone.models.Board;
import org.trello.trelloclone.repository.BoardRepository;
import org.trello.trelloclone.service.BoardService;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> getBoardsByTeamId(Long teamId) {
        return boardRepository.findByTeamId(teamId);
    }

    @Override
    public Board updateBoard(Long id, Board updatedBoard) {
        return boardRepository.findById(id).map(board -> {
            board.setTitle(updatedBoard.getTitle());
            board.setDescription(updatedBoard.getDescription());
            board.setTeam(updatedBoard.getTeam());
            return boardRepository.save(board);
        }).orElseThrow(() -> new RuntimeException("Board not found"));
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
