package com.fotio.taskservice.dto;
import lombok.*;
import java.sql.Timestamp;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {


    private String id;
    private String taskTitle;
    private String taskDescription;
    private String notes;
    //EmployeeId
    private String assignee;
    private Timestamp taskStartDate;
    private String taskStatus;
    private String priorityType;
}
