package org.trello.trelloclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trello.trelloclone.models.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTeamId(Long teamId);
}
