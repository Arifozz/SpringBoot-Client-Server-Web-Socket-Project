package com.example.socket.service.impl;

import com.example.socket.model.dto.NotificationDto;
import com.example.socket.model.entity.Notification;
import com.example.socket.repository.NotificationMapper;
import com.example.socket.service.NotificationService;
import com.example.socket.socket.SocketTopics;
import com.example.socket.util.TimeUtil;
import com.example.socket.util.converter.NotificationConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LogManager.getLogger(NotificationServiceImpl.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationConverter notificationConverter;

    @Override
    public void sendNotification(NotificationDto dto) {
        Notification notification = notificationConverter.dtoToNotification(dto);
        notification.setId(UUID.randomUUID().toString());
        String formattedDate = TimeUtil.nowToString();
        notification.setDate(formattedDate);
        messagingTemplate.convertAndSend(SocketTopics.NOTIFICATION.getTopic(), notification);
        LOGGER.info("Message sent to: {}", SocketTopics.NOTIFICATION.getTopic());
        notificationMapper.insert(notification);
        LOGGER.info("Notification Saved");
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void deleteScheduledData() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime pastDate = now.minusDays(30);
        String dateTimeToString = TimeUtil.localDateTimeToString(pastDate);
        notificationMapper.deleteOldNotifications(dateTimeToString);
        LOGGER.info("Deleted Notifications Before: {}", dateTimeToString);
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void sendHeartBeatMessage() {
        messagingTemplate.convertAndSend(SocketTopics.HEART_BEAT.getTopic(), "heart-beat");
    }

}
