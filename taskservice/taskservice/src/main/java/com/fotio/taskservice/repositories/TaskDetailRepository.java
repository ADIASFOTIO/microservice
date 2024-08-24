package com.fotio.taskservice.repositories;

import com.fotio.taskservice.entities.TaskDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskDetailRepository extends ElasticsearchRepository<TaskDetail,String> {
    public List<TaskDetail> findByTaskDescriptionContains(String description);
    public List<TaskDetail> findTaskDetailByEmployeeNameStartingWith(String text);
    public List<TaskDetail> findByTaskTitleContaining(String description);
}
