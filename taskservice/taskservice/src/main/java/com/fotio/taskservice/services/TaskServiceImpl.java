package com.fotio.taskservice.services;
import com.fotio.taskservice.dto.EmployeeDTO;
import com.fotio.taskservice.dto.TaskDTO;
import com.fotio.taskservice.dto.TaskDetailDTO;
import com.fotio.taskservice.dto.TaskNotificationDTO;
import com.fotio.taskservice.entities.Task;
import com.fotio.taskservice.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.fotio.taskservice.mapper.Mapper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    public static final String URL_EMPLOYEES = "http://localhost:7202/api/employees/";
    private final TaskRepository taskRepository;
    private final TaskDetailService taskDetailService;
    private final ModelMapper modelMapper;
    private final Mapper mapper;
    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final APIClient apiClient;
    // Rest Template
    @Override
    public TaskDTO saveTask(TaskDTO taskDTO) {
        ResponseEntity<EmployeeDTO> employeeDTOResponseEntity = restTemplate.getForEntity(URL_EMPLOYEES + taskDTO.getAssignee(), EmployeeDTO.class);
        EmployeeDTO employeeDTO = employeeDTOResponseEntity.getBody();
        if (employeeDTOResponseEntity.getStatusCode().is2xxSuccessful()) {
            taskDTO.setAssignee(employeeDTO.getId());
            Task task = modelMapper.map(taskDTO, Task.class);
            taskRepository.save(task);
            TaskDTO addedtaskDTO = modelMapper.map(task,TaskDTO.class);
            taskDTO.setId(task.getId());
            TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
            taskDetailDTO.setEmployeeId(employeeDTO.getId());
            taskDetailDTO.setEmployeeName(employeeDTO.getName());
            taskDetailDTO.setEmployeeSurname(employeeDTO.getSurname());
            taskDetailDTO.setTaskDescription(taskDTO.getTaskDescription());
            taskDetailDTO.setPriority(taskDTO.getPriorityType());
            taskDetailDTO.setStatus(taskDTO.getTaskStatus());
            taskDetailDTO.setTaskTitle(taskDTO.getTaskTitle());
            taskDetailDTO = taskDetailService.save(taskDetailDTO);
            return taskDTO;
        } else return new TaskDTO();
    }

    @Override
    public TaskDTO updateTask(String Id, TaskDTO taskDTO) {
        taskDTO.setId(Id);
        Task fromUser = modelMapper.map(taskDTO, Task.class);
        Task task = taskRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException());
        task.setTaskTitle(taskDTO.getTaskTitle());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setTaskStatus(fromUser.getTaskStatus());
        task.setPriorityType(fromUser.getPriorityType());
        task.setNotes(taskDTO.getNotes());
        task.setAssignee(taskDTO.getAssignee());
        taskRepository.save(task);
        return taskDTO;
    }

    @Override
    public TaskDTO getById(String Id) {
        Task task = taskRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        TaskDTO taskDTO = modelMapper.map(task,TaskDTO.class);
        return taskDTO;
    }

    @Override
    public TaskDTO deleteById(String Id) {
        TaskDTO taskDTO = getById(Id);
        taskRepository.deleteById(Id);
        return taskDTO;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> listTask = taskRepository.findAll();
        List<TaskDTO> lisTtaskDTO = mapper.mapList(listTask,TaskDTO.class);
        return lisTtaskDTO;
    }

    @Override
    public List<TaskDTO> getTaskPagination(int pagesize, int pageno) {
        Page<Task> tasks = taskRepository.findAll(PageRequest.of(pageno, pagesize));
        List<TaskDTO> dtolist = tasks.stream().map(task -> modelMapper.map(task, TaskDTO.class)).collect(Collectors.toList());
        return dtolist;
    }

    public List<TaskDTO> saveTaskBulk(List<TaskDTO> lisTtaskDTO) {
        logger.info("[{}] saveTaskBulk - dto= {}", lisTtaskDTO);

        // Nombre de threads souhaité (peut être ajusté selon les besoins)
        int threadCount = 10;

        // Diviser la liste en sous-listes
        List<List<TaskDTO>> partitions = partitionList(lisTtaskDTO, threadCount);

        // Créer un pool de threads
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<List<TaskDTO>>> futures = new ArrayList<>();

        for (List<TaskDTO> partition : partitions) {
            Callable<List<TaskDTO>> task = () -> {
                List<Task> LisTtask = partition.stream()
                        .map(e -> modelMapper.map(e, Task.class))
                        .collect(Collectors.toList());
//                List<Task> LisTtaskSave = taskRepository.saveAll(LisTtask);
                List<TaskDTO> lisTtaskDTOtoSave = mapper.mapList(LisTtask,TaskDTO.class);
                List<TaskDTO> LisTtaskSave = new ArrayList<>();
                for (TaskDTO taskDTO : lisTtaskDTOtoSave) {
                    LisTtaskSave.add(saveTask(taskDTO));
                }
                return LisTtaskSave.stream()
                        .map(e -> modelMapper.map(e, TaskDTO.class))
                        .collect(Collectors.toList());
            };
            futures.add(executor.submit(task));
        }

        // Attendre que tous les threads se terminent et combiner les résultats
        List<TaskDTO> result = new ArrayList<>();
        for (Future<List<TaskDTO>> future : futures) {
            try {
                result.addAll(future.get(10, TimeUnit.SECONDS)); // 10 secondes de timeout par thread
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error("Error occurred while processing tasks", e);
                // Gérer l'exception selon le cas (log, rethrow, etc.)
            }
        }

        executor.shutdown(); // Fermer le pool de threads
        return result;
    }
// web client
    @Override
    public TaskDTO saveTaskWebClient(TaskDTO taskDTO) {
        EmployeeDTO employeeDTO = webClient.get()
                .uri(URL_EMPLOYEES + taskDTO.getAssignee())
                .retrieve()
                .bodyToMono(EmployeeDTO.class)
                .block();
            taskDTO.setAssignee(employeeDTO.getId());
            Task task = modelMapper.map(taskDTO, Task.class);
            taskRepository.save(task);
            TaskDTO addedtaskDTO = modelMapper.map(task,TaskDTO.class);
            taskDTO.setId(task.getId());
            TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
            taskDetailDTO.setEmployeeId(employeeDTO.getId());
            taskDetailDTO.setEmployeeName(employeeDTO.getName());
            taskDetailDTO.setEmployeeSurname(employeeDTO.getSurname());
            taskDetailDTO.setTaskDescription(taskDTO.getTaskDescription());
            taskDetailDTO.setPriority(taskDTO.getPriorityType());
            taskDetailDTO.setStatus(taskDTO.getTaskStatus());
            taskDetailDTO.setTaskTitle(taskDTO.getTaskTitle());
            taskDetailDTO = taskDetailService.save(taskDetailDTO);
            return taskDTO;
    }

    @Override
    public TaskDTO createTaskUseOpenFeign(TaskDTO taskDTO) {
        EmployeeDTO employeeDTO = apiClient.getById(taskDTO.getAssignee());
        taskDTO.setAssignee(employeeDTO.getId());
        Task task = modelMapper.map(taskDTO, Task.class);
        taskRepository.save(task);
        taskDTO.setId(task.getId());
        TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
        taskDetailDTO.setEmployeeId(employeeDTO.getId());
        taskDetailDTO.setEmployeeName(employeeDTO.getName());
        taskDetailDTO.setEmployeeSurname(employeeDTO.getSurname());
        taskDetailDTO.setTaskDescription(taskDTO.getTaskDescription());
        taskDetailDTO.setPriority(taskDTO.getPriorityType());
        taskDetailDTO.setStatus(taskDTO.getTaskStatus());
        taskDetailDTO.setTaskTitle(taskDTO.getTaskTitle());
        taskDetailDTO = taskDetailService.save(taskDetailDTO);
        return taskDTO;
    }

    // Méthode pour diviser une liste en sous-listes
    private <T> List<List<T>> partitionList(List<T> list, int partitions) {
        List<List<T>> partitioned = new ArrayList<>();
        int partitionSize = (list.size() + partitions - 1) / partitions; // taille de chaque sous-liste
        for (int i = 0; i < list.size(); i += partitionSize) {
            partitioned.add(list.subList(i, Math.min(i + partitionSize, list.size())));
        }
        return partitioned;
    }
}
