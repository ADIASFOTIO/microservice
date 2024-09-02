package com.fotio.notificationservice.services;

import com.fotio.notificationservice.dto.NotificationDTO;
import com.fotio.notificationservice.entities.Notification;
import com.fotio.notificationservice.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {
        if(notificationDTO==null)
            throw new IllegalArgumentException();
        Notification notification=modelMapper.map(notificationDTO,Notification.class);
        notification=notificationRepository.save(notification);
        notificationDTO.setId(notification.getId());
        return notificationDTO;
    }

    @Override
    public NotificationDTO getById(long Id) {
        Notification notification=notificationRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        NotificationDTO notificationDTO=modelMapper.map(notification,NotificationDTO.class);
        return notificationDTO;
    }

    @Override
    public List<NotificationDTO> getAll() {

        List<Notification> notifications=notificationRepository.findAll();
        List<NotificationDTO> dtoList=notifications.stream().
                map(notification -> modelMapper.map(notification,NotificationDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public NotificationDTO update(long Id, NotificationDTO notificationDTO) {
        Notification notification=notificationRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        notification.setEmployeeId(notificationDTO.getEmployeeId());
        notification.setTaskId(notificationDTO.getTaskId());
        notification.setTaskTitle(notificationDTO.getTaskTitle());
        notification.setTaskDescription(notificationDTO.getTaskDescription());
        notificationRepository.save(notification);
        return notificationDTO;
    }

    @Override
    public NotificationDTO delete(long Id) {
        Notification notification=notificationRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        NotificationDTO notificationDTO=modelMapper.map(notification,NotificationDTO.class);
        notificationRepository.delete(notification);
        return notificationDTO;
    }
}
