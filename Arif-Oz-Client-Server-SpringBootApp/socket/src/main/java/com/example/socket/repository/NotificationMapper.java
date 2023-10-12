package com.example.socket.repository;

import com.example.socket.model.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insert(Notification notification);
    List<Notification> selectAll();
    Notification selectById(String id);
    void deleteById(String id);
    void deleteOldNotifications(String date);

}