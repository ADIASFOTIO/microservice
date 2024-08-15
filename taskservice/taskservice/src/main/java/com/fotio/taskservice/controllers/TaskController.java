package com.fotio.taskservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fotio.taskservice.dto.TaskDTO;
import com.fotio.taskservice.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")

    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") String id) {
        return new ResponseEntity<TaskDTO>(taskService.getById(id), HttpStatus.OK);
    }

    public ResponseEntity<TaskDTO> TaskServicefallbackMethodForDTO(Throwable throwable) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskDescription("This Error Comes from TAsk Service for dto");
        System.out.println("This Error Comes from TAsk Service for dto");
        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) throws JsonProcessingException {
        return new ResponseEntity<TaskDTO>(taskService.saveTask(taskDTO), HttpStatus.CREATED);
        //save to elastic search
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getall() {
        return new ResponseEntity<List<TaskDTO>>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/pagination/{pageNo}/{pageSize}")
    public ResponseEntity<List<TaskDTO>> taskpagination(@PathVariable int pageNo, @PathVariable int pageSize) {
        return ResponseEntity.ok(taskService.getTaskPagination(pageSize, pageNo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        TaskDTO checkDTO = taskService.getById(id);
        if (checkDTO != null) return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
        else return new ResponseEntity<TaskDTO>(new TaskDTO(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable("id") String id) {
        return new ResponseEntity<TaskDTO>(taskService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
