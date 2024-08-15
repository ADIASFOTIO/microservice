package com.fotio.taskservice.repositories;

import com.fotio.taskservice.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task,String> {
}
