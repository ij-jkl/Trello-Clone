package org.trello.trelloclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trello.trelloclone.models.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBoardId(Long boardId);
    List<Task> findByAssignedToId(Long userId);
}
