package com.example.socket.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    @NotBlank(message = "Sender name can not be empty!")
    private String sender;

    @NotBlank(message = "Message can not be empty!")
    private String message;

}
