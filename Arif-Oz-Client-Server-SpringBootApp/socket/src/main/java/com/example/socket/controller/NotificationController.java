package com.example.socket.controller;

import com.example.socket.model.dto.NotificationDto;
import com.example.socket.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> sendNotification(@Valid @RequestBody NotificationDto dto){
        notificationService.sendNotification(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
