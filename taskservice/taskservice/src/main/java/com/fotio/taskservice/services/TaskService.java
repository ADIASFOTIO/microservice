package com.fotio.taskservice.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fotio.taskservice.dto.TaskDTO;
import java.util.List;

public interface TaskService {

    public TaskDTO saveTask(TaskDTO taskDTO)throws JsonProcessingException;
    public TaskDTO updateTask( String Id,TaskDTO taskDTO);
    public TaskDTO getById(String Id);
    public TaskDTO deleteById(String Id);
    public List<TaskDTO> getAllTasks();
    public List<TaskDTO> getTaskPagination(int pagesize,int pageno);
    List<TaskDTO> saveTaskBulk(List<TaskDTO> lisTtaskDTO)throws JsonProcessingException;

    TaskDTO saveTaskWebClient(TaskDTO taskDTO)throws JsonProcessingException;

    TaskDTO createTaskUseOpenFeign(TaskDTO taskDTO)throws JsonProcessingException;
}
