package com.fotio.notificationservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotio.notificationservice.dto.TaskNotificationDTO;
import com.fotio.notificationservice.entities.Notification;
import com.fotio.notificationservice.repositories.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class TaskNotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(TaskNotificationListener.class);

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "fotioqueue")
    public void handleMessage(String message) throws JsonProcessingException {
        TaskNotificationDTO taskNotificationDTO = new ObjectMapper().readValue(message,TaskNotificationDTO.class);
        Notification notification = new Notification();
        notification.setTaskDescription(taskNotificationDTO.getTaskId());
        notification.setTaskTitle(taskNotificationDTO.getTaskTitle());
        notification.setEmployeeId(taskNotificationDTO.getEmployeeId());
        notification.setTaskId(taskNotificationDTO.getTaskId());
        notificationRepository.save(notification);
//        System.out.println("Message received from Task Service");
//        System.out.println(taskNotificationDTO.toString());
        logger.info("Message received from Task Service {}", taskNotificationDTO.toString());

    }

}
