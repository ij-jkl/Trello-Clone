package org.trello.trelloclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trello.trelloclone.models.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
