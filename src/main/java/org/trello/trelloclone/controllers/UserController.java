package org.trello.trelloclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trello.trelloclone.dtos.ResponseObjectJsonDto;
import org.trello.trelloclone.models.User;
import org.trello.trelloclone.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<ResponseObjectJsonDto> createUser(@RequestBody User user) {
        ResponseObjectJsonDto response = userService.createUser(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_user_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> getUserById(@PathVariable Long id) {
        ResponseObjectJsonDto response = userService.getUserById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_user_by_{email}")
    public ResponseEntity<ResponseObjectJsonDto> getUserByEmail(@PathVariable String email) {
        ResponseObjectJsonDto response = userService.getUserByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<ResponseObjectJsonDto> getAllUsers() {
        ResponseObjectJsonDto response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/update_user_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        ResponseObjectJsonDto response = userService.updateUser(id, user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/delete_user_by_{id}")
    public ResponseEntity<ResponseObjectJsonDto> deleteUser(@PathVariable Long id) {
        ResponseObjectJsonDto response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
