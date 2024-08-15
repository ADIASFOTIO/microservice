package com.fotio.taskservice.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
@EqualsAndHashCode(of = {"id"})
public class Task extends CoreEntity {
    @Id
    @Getter
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;
    @Getter
    @Setter
    @Column(name = "title")
    private String taskTitle;
    @Getter
    @Setter
    @Column(name = "description", length = 600)
    private String taskDescription;
    @Getter
    @Setter
    @Column(name = "notes")
    private String notes;
    @Getter
    @Setter
    @Column(name = "assignee", length = 50)
    private String assignee;
    @Getter
    @Setter
    @Column(name = "startdate")
    private Timestamp taskStartDate;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private PriorityType priorityType;
}
