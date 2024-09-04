package com.fotio.taskservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fotio.taskservice.dto.TaskDTO;
import com.fotio.taskservice.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "TASK_SERVICE",fallbackMethod = "TaskServicefallbackMethodForDTO")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) throws JsonProcessingException {
        return new ResponseEntity<TaskDTO>(taskService.saveTask(taskDTO), HttpStatus.CREATED);
        //save to elastic search
    }

    // use web client
    @PostMapping("/open-feign")
    public ResponseEntity<TaskDTO> createTaskUseOpenFeign(@RequestBody TaskDTO taskDTO) throws JsonProcessingException {
        return new ResponseEntity<TaskDTO>(taskService.createTaskUseOpenFeign(taskDTO), HttpStatus.CREATED);
        //save to elastic search
    }

    // open feign
    @PostMapping("/web-client")
    public ResponseEntity<TaskDTO> createTaskUseWebClient(@RequestBody TaskDTO taskDTO) throws JsonProcessingException {
        return new ResponseEntity<TaskDTO>(taskService.saveTaskWebClient(taskDTO), HttpStatus.CREATED);
        //save to elastic search
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<TaskDTO>> createTaskBulk(@RequestBody List<TaskDTO> LisTtaskDTO) throws JsonProcessingException {
        return new ResponseEntity<List<TaskDTO>>(taskService.saveTaskBulk(LisTtaskDTO), HttpStatus.CREATED);
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
