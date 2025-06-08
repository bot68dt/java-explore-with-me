package ru.practicum.explore.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}