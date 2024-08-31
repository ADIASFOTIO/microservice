package com.fotio.notificationservice.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class TaskNotificationDTO implements Serializable {
    private String taskId;
    private String employeeId;
    private String taskTitle;
    private String taskDescription;
}
