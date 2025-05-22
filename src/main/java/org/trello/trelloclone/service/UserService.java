package org.trello.trelloclone.service;

import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.dto.UserDto;

public interface UserService {
    ResponseObjectJsonDto createUser(UserDto userDto);
    ResponseObjectJsonDto getUserById(Long id);
    ResponseObjectJsonDto getUserByEmail(String email);
    ResponseObjectJsonDto getAllUsers();
    ResponseObjectJsonDto updateUser(Long id, UserDto updatedUserDto);
    ResponseObjectJsonDto deleteUser(Long id);
}
