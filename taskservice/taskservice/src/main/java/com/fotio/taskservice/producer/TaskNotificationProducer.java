package com.fotio.taskservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fotio.taskservice.dto.TaskNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class TaskNotificationProducer {
    @Value("${ms.rabbit.routing.name}")
    private String routingName;
    @Value("${ms.rabbit.exchange.name}")
    private String exchangeName;

    private final AmqpTemplate amqpTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TaskNotificationProducer.class);

    public void sendToQueue(TaskNotificationDTO taskNotificationDTO) throws JsonProcessingException {
        String taskString = new ObjectMapper().writeValueAsString(taskNotificationDTO);
//        System.out.println("Notification Sent: "+ taskNotificationDTO.toString());
        logger.info("Notification Sent: {}", taskNotificationDTO.toString());
        amqpTemplate.convertAndSend(exchangeName,routingName,taskString);

    }
}
