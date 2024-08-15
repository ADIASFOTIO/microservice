package com.fotio.taskservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"id"})
public class TaskDetailDTO {
    private String id;
    private String employeeId;
    private String employeeName;
    private String employeeSurname;
    private String taskTitle;
    private String taskDescription;
    private String status;
    private String priority;
}
