package main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "creation_time")
    LocalDateTime creationTime;

    @Column(name = "is_done")
    boolean isDone;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

}
