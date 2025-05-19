package org.trello.trelloclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trello.trelloclone.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
