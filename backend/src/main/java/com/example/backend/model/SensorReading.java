package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "sensor_readings")
public class SensorReading {

    // getters & setters (omitted here for brevity—add auto-generated ones)
    // ... generate getter/setters or use your IDE to create them
    // example:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // sensor fields
    private Double roomTemp;
    private Double humidity;
    private Double waterTempC;
    private Double waterTempF;
    private Long irValue;
    private Double bpm;
    private Double avgBpm;

    // when received by server
    private Instant receivedAt;

    public SensorReading() {}

}

