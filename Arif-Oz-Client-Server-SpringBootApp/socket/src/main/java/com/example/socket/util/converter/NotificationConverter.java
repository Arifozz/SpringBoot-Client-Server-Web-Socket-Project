package com.example.socket.util.converter;

import com.example.socket.model.dto.NotificationDto;
import com.example.socket.model.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationConverter {

    Notification dtoToNotification(NotificationDto dto);

}
