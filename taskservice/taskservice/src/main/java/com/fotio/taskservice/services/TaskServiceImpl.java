package com.fotio.taskservice.services;
import com.fotio.taskservice.dto.TaskDTO;
import com.fotio.taskservice.entities.Task;
import com.fotio.taskservice.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = taskRepository.save(modelMapper.map(taskDTO,Task.class));
        return modelMapper.map(task,TaskDTO.class);
    }

    @Override
    public TaskDTO updateTask(String Id, TaskDTO taskDTO) {
        return null;
    }

    @Override
    public TaskDTO getById(String Id) {
        Task task = taskRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        TaskDTO taskDTO = modelMapper.map(task,TaskDTO.class);
        return taskDTO;
    }

    @Override
    public TaskDTO deleteById(String Id) {
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return List.of();
    }

    @Override
    public List<TaskDTO> getTaskPagination(int pagesize, int pageno) {
        return List.of();
    }
}
