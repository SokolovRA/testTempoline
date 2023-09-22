package com.example.testtempoline1.services;

import com.example.testtempoline1.dto.EventDTO;
import com.example.testtempoline1.exceptions.IncorrectArgumentException;
import com.example.testtempoline1.exceptions.ItemNotFoundException;
import com.example.testtempoline1.mappers.EventMapper;
import com.example.testtempoline1.models.Event;
import com.example.testtempoline1.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventDTO addEvent(EventDTO eventDTO) {
        validateEventDTO(eventDTO);

        Event event = eventMapper.eventDTOToEvent(eventDTO);
        Event savedEvent = eventRepository.save(event);

        return eventMapper.eventToEventDTO(savedEvent);
    }

    public Page<EventDTO> getEvents(String classifier, Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        if (page < 0 || size <= 0) {
            throw new IncorrectArgumentException("Invalid page or size");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Event> eventsPage;
        if (classifier != null) {
            eventsPage = findByClassifier(classifier, pageable);
        } else {
            eventsPage = findAll(pageable);
        }
        if (eventsPage.isEmpty()) {
            throw new ItemNotFoundException(
                    classifier != null ? "No events found for classifier: " + classifier : "No events found"
            );
        }

        return eventsPage.map(eventMapper::eventToEventDTO);
    }

    private Page<Event> findByClassifier(String classifier, Pageable pageable) {
        return eventRepository.findByClassifier(classifier, pageable);
    }

    private Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    private void validateEventDTO(EventDTO eventDTO) {
        if (eventDTO == null) {
            throw new IncorrectArgumentException("EventDTO cannot be null");
        }

        if (eventDTO.getName() == null || eventDTO.getName().trim().isEmpty()) {
            throw new IncorrectArgumentException("Event name cannot be null or empty");
        }

        if (eventDTO.getDate() == null) {
            throw new IncorrectArgumentException("Event date cannot be null");
        }

        if (eventDTO.getClassifier() == null || eventDTO.getClassifier().trim().isEmpty()) {
            throw new IncorrectArgumentException("Event classifier cannot be null or empty");
        }
    }
}
