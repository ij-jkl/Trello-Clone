package org.trello.trelloclone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.trello.trelloclone.dtos.EntityNotFoundException;
import org.trello.trelloclone.dtos.InvalidEntityException;
import org.trello.trelloclone.dtos.common.ResponseBuilder;
import org.trello.trelloclone.dtos.common.ResponseObjectJsonDto;
import org.trello.trelloclone.models.User;
import org.trello.trelloclone.repository.UserRepository;
import org.trello.trelloclone.service.UserService;

import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseObjectJsonDto createUser(User user) {
        try {

            if (user.getName() == null || user.getName().trim().isEmpty()) {
                throw new InvalidEntityException("User name is required");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                throw new InvalidEntityException("User email is required");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new InvalidEntityException("User password is required");
            }

            User savedUser = userRepository.save(user);
            return ResponseBuilder.buildCreatedResponse(savedUser, "User created successfully");

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
                    .map(user -> ResponseBuilder.buildSuccessResponse(user, "User found successfully"))
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
            return ResponseBuilder.buildSuccessResponse(users, "Users list retrieved successfully");
        } catch (Exception e) {

            log.error("Error getting all users : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseObjectJsonDto updateUser(Long id, User updatedUser) {
        try {

            if (updatedUser.getName() == null || updatedUser.getName().trim().isEmpty()) {
                throw new InvalidEntityException("User name is required");
            }
            if (updatedUser.getEmail() == null || updatedUser.getEmail().trim().isEmpty()) {
                throw new InvalidEntityException("User email is required");
            }

            return userRepository.findById(id)
                    .map(user -> {
                        user.setName(updatedUser.getName());
                        user.setEmail(updatedUser.getEmail());
                        if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
                            user.setPassword(updatedUser.getPassword());
                        }
                        User savedUser = userRepository.save(user);
                        return ResponseBuilder.buildSuccessResponse(savedUser, "User updated successfully");
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
                    .map(user -> ResponseBuilder.buildSuccessResponse(user, "User found successfully"))
                    .orElse(ResponseBuilder.buildNotFoundResponse("User not found with email : " + email));

        } catch (InvalidEntityException e) {

            return ResponseBuilder.buildBadRequestResponse(e.getMessage());
        } catch (Exception e) {

            log.error("Error getting user by email : ", e);
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }
}