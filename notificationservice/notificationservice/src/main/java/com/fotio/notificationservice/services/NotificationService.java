package com.fotio.notificationservice.services;

import com.fotio.notificationservice.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    public NotificationDTO save( NotificationDTO notificationDTO);
    public NotificationDTO getById(long Id);
    public List<NotificationDTO> getAll();
    public NotificationDTO update(long Id,NotificationDTO notificationDTO);
    public NotificationDTO delete(long Id);
}
