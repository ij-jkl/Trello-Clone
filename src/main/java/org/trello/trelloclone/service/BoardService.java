package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Board;


public interface BoardService {
    ResponseObjectJsonDto createBoard(Board board);
    ResponseObjectJsonDto getBoardById(Long id);
    ResponseObjectJsonDto getAllBoards();
    ResponseObjectJsonDto getBoardsByTeamId(Long teamId);
    ResponseObjectJsonDto updateBoard(Long id, Board updatedBoard);
    ResponseObjectJsonDto deleteBoard(Long id);
}
