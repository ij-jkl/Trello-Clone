package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dtos.models.board.BoardRequestDto;

public interface BoardService {
    ResponseObjectJsonDto createBoard(BoardRequestDto boardRequestDto);
    ResponseObjectJsonDto getBoardById(Long id);
    ResponseObjectJsonDto getAllBoards();
    ResponseObjectJsonDto getBoardsByTeamId(Long teamId);
    ResponseObjectJsonDto updateBoard(Long id, BoardRequestDto boardRequestDto);
    ResponseObjectJsonDto deleteBoard(Long id);
}
