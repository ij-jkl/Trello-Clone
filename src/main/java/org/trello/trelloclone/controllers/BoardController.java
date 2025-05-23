package org.trello.trelloclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dtos.models.board.BoardRequestDto;
import org.trello.trelloclone.service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create_board")
    public ResponseEntity<ResponseObjectJsonDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        ResponseObjectJsonDto response = boardService.createBoard(boardRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_board_by_{boardId}")
    public ResponseEntity<ResponseObjectJsonDto> getBoardById(@PathVariable Long boardId) {
        ResponseObjectJsonDto response = boardService.getBoardById(boardId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_all_boards")
    public ResponseEntity<ResponseObjectJsonDto> getAllBoards() {
        ResponseObjectJsonDto response = boardService.getAllBoards();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_boards_by_{teamId}")
    public ResponseEntity<ResponseObjectJsonDto> getBoardsByTeamId(@PathVariable Long teamId) {
        ResponseObjectJsonDto response = boardService.getBoardsByTeamId(teamId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/update_board_by/{boardId}")
    public ResponseEntity<ResponseObjectJsonDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        ResponseObjectJsonDto response = boardService.updateBoard(boardId, boardRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/delete_board_by/{boardId}")
    public ResponseEntity<ResponseObjectJsonDto> deleteBoard(@PathVariable Long boardId) {
        ResponseObjectJsonDto response = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}