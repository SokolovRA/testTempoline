package com.example.testtempoline1.mappers;

import com.example.testtempoline1.dto.EventDTO;
import com.example.testtempoline1.models.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EventMapper {
    Event eventDTOToEvent(EventDTO eventDTO);
    EventDTO eventToEventDTO(Event event);
}
