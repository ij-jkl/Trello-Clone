package org.trello.trelloclone.models;

import jakarta.persistence.*;
import org.trello.trelloclone.models.enums.TrackStatus;
import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TrackStatus status;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedTo;

    private LocalDate dueDate;
}
