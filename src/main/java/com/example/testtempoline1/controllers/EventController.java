package com.example.testtempoline1.controllers;

import com.example.testtempoline1.dto.EventDTO;
import com.example.testtempoline1.models.Event;
import com.example.testtempoline1.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "Добавить событие",
            description = "Добавляет новое событие в систему.",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            }
    )
    @PostMapping("/add")
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO eventDTO) {
        EventDTO savedEvent = eventService.addEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @Operation(
            summary = "Получить события",
            description = "Получает список событий с возможностью фильтрации и пагинацией.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))}),
            }
    )
    @GetMapping("/list")
    public ResponseEntity<List<EventDTO>> getEvents(
            @RequestParam(required = false) String classifier,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Page<EventDTO> eventsPage = eventService.getEvents(classifier, page, size);
        List<EventDTO> events = eventsPage.getContent();
         return ResponseEntity.ok(events);
    }
}




