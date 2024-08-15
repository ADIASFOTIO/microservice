package com.fotio.taskservice.entities;

import lombok.Getter;

@Getter
public enum PriorityType {
    URGENT("Urgent"),
    LOW("Low"),
    HIGH("High");
private String type;
    PriorityType(String type) {
        this.type=type;
    }
}
