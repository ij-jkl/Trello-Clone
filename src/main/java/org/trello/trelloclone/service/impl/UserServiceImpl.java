package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.common.ResponseBuilder;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.models.User;
import org.trello.trelloclone.models.Team;
import org.trello.trelloclone.dto.UserDto;
import org.trello.trelloclone.repository.UserRepository;
import org.trello.trelloclone.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        if (user.getTeams() != null) {
            dto.setTeamIds(user.getTeams().stream().map(Team::getId).collect(Collectors.toList()));
        }
        return dto;
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    @Override
    public ResponseObjectJsonDto createUser(UserDto userDto) {
        try {
            if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
                throw new InvalidEntityException("User name is required");
            }
            if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
                throw new InvalidEntityException("User email is required");
            }

            User user = toEntity(userDto);
            User savedUser = userRepository.save(user);
            return ResponseBuilder.buildCreatedResponse(toDto(savedUser), "User created successfully");

        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error creating user: ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .map(user -> ResponseBuilder.buildSuccessResponse(toDto(user), "User found successfully"))
                    .orElseThrow(() -> new EntityNotFoundException("User ", id));
        } catch (EntityNotFoundException e) {

            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error getting user : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDto> dtos = users.stream().map(this::toDto).collect(Collectors.toList());
            return ResponseBuilder.buildSuccessResponse(dtos, "Users list retrieved successfully");
        } catch (Exception e) {

            log.error("Error getting all users : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto updateUser(Long id, UserDto updatedUserDto) {
        try {
            if (updatedUserDto.getName() == null || updatedUserDto.getName().trim().isEmpty()) {
                throw new InvalidEntityException("User name is required");
            }
            if (updatedUserDto.getEmail() == null || updatedUserDto.getEmail().trim().isEmpty()) {
                throw new InvalidEntityException("User email is required");
            }
            return userRepository.findById(id)
                    .map(user -> {
                        user.setName(updatedUserDto.getName());
                        user.setEmail(updatedUserDto.getEmail());
                        User savedUser = userRepository.save(user);
                        return ResponseBuilder.buildSuccessResponse(toDto(savedUser), "User updated successfully");
                    })
                    .orElseThrow(() -> new EntityNotFoundException("User", id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error updating user : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto deleteUser(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new EntityNotFoundException("User", id);
            }

            userRepository.deleteById(id);
            return ResponseBuilder.buildSuccessResponse(null, "User deleted successfully");

        } catch (EntityNotFoundException e) {

            return ResponseBuilder.buildNotFoundResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error deleting user : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto getUserByEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new InvalidEntityException("Email is required");
            }

            return userRepository.findByEmail(email)
                    .map(user -> ResponseBuilder.buildSuccessResponse(toDto(user), "User found successfully"))
                    .orElse(ResponseBuilder.buildNotFoundResponse("User not found with email : " + email));

        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error getting user by email : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }
}