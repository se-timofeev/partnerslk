package ru.planetnails.partnerslk.model.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDtoOut {

    private Integer id;
    private Long orderNum;
    private LocalDateTime orderDate;
    private Double sum;
    private String status;
    private LocalDateTime statusUpdateTime;
    private boolean isRead;
}
