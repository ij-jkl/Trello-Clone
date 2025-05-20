package org.trello.trelloclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.Board;
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
    public ResponseEntity<ResponseObjectJsonDto> createBoard(@RequestBody Board board) {
        ResponseObjectJsonDto response = boardService.createBoard(board);
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
    public ResponseEntity<ResponseObjectJsonDto> updateBoard(@PathVariable Long boardId, @RequestBody Board board) {
        ResponseObjectJsonDto response = boardService.updateBoard(boardId, board);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/delete_board_by/{boardId}")
    public ResponseEntity<ResponseObjectJsonDto> deleteBoard(@PathVariable Long boardId) {
        ResponseObjectJsonDto response = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}