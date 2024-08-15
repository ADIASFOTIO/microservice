package com.fotio.taskservice.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String job;
    private int age;
}
