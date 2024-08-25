package com.fotio.taskservice.services;
import com.fotio.taskservice.dto.TaskDetailDTO;
import java.util.List;
public interface TaskDetailService {

    public TaskDetailDTO save(TaskDetailDTO taskDetailDTO);
    public TaskDetailDTO getTaskDetailbyId(String Id);
    public List<TaskDetailDTO> getAllTaskDetails();
    public TaskDetailDTO updateTaskDetail(TaskDetailDTO taskDetailDTO);
    public TaskDetailDTO deleteTaskDetail(String Id);
    public List<TaskDetailDTO> getWithContainDescription(String description);
    public List<TaskDetailDTO> getWithStartsName(String text);

    List<TaskDetailDTO> saveList(List<TaskDetailDTO> listTaskDetailDTO);
}
