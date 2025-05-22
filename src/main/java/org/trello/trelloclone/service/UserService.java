package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.models.User;


public interface UserService {
    ResponseObjectJsonDto createUser(User user);
    ResponseObjectJsonDto getUserById(Long id);
    ResponseObjectJsonDto getUserByEmail(String email);
    ResponseObjectJsonDto getAllUsers();
    ResponseObjectJsonDto updateUser(Long id, User updatedUser);
    ResponseObjectJsonDto deleteUser(Long id);
}
