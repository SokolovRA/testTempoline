package com.example.testtempoline1.repository;

import com.example.testtempoline1.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByClassifier(String classifier, Pageable pageable);
    Page<Event> findAll(Pageable pageable);
}
