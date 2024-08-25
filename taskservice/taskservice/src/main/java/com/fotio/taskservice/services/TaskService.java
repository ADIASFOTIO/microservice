package com.fotio.taskservice.services;
import com.fotio.taskservice.dto.TaskDTO;
import java.util.List;

public interface TaskService {

    public TaskDTO saveTask(TaskDTO taskDTO);
    public TaskDTO updateTask( String Id,TaskDTO taskDTO);
    public TaskDTO getById(String Id);
    public TaskDTO deleteById(String Id);
    public List<TaskDTO> getAllTasks();
    public List<TaskDTO> getTaskPagination(int pagesize,int pageno);
    List<TaskDTO> saveTaskBulk(List<TaskDTO> lisTtaskDTO);

    TaskDTO saveTaskWebClient(TaskDTO taskDTO);

    TaskDTO createTaskUseOpenFeign(TaskDTO taskDTO);
}
