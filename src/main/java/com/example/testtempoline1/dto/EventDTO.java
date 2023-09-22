package com.example.testtempoline1.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EventDTO {
    private String name;
    private LocalDate date;
    private String classifier;

}
