package com.example.backend.service;

import com.example.backend.model.SensorReading;
import com.example.backend.repository.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SensorReadingService {

    @Autowired
    private SensorReadingRepository repository;

    public SensorReading save(SensorReading reading) {
        if (reading.getReceivedAt() == null) {
            reading.setReceivedAt(Instant.now());
        }
        return repository.save(reading);
    }

    public List<SensorReading> findAll() {
        return repository.findAll();
    }

    public long count() {
        return repository.count();
    }

    public SensorReading findLatest() {
        return repository.findAll()
                .stream()
                .sorted((a, b) -> b.getReceivedAt().compareTo(a.getReceivedAt()))
                .findFirst()
                .orElse(null);
    }
}