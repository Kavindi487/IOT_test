package com.example.backend.repository;

import com.example.backend.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    // Add custom queries if needed
}
