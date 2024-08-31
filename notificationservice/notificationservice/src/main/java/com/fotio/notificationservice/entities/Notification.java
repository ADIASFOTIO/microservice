package com.fotio.notificationservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@EqualsAndHashCode(of={"id"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "task_id")
    private String taskId;
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "task_title")
    private String taskTitle;
    @Column(name = "task_description")
    private String taskDescription;
}
