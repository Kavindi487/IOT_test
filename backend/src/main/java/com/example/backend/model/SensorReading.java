package com.example.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {

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

    // getters & setters (omitted here for brevity—add auto-generated ones)
    // ... generate getter/setters or use your IDE to create them
    // example:
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getRoomTemp() { return roomTemp; }
    public void setRoomTemp(Double roomTemp) { this.roomTemp = roomTemp; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public Double getWaterTempC() { return waterTempC; }
    public void setWaterTempC(Double waterTempC) { this.waterTempC = waterTempC; }

    public Double getWaterTempF() { return waterTempF; }
    public void setWaterTempF(Double waterTempF) { this.waterTempF = waterTempF; }

    public Long getIrValue() { return irValue; }
    public void setIrValue(Long irValue) { this.irValue = irValue; }

    public Double getBpm() { return bpm; }
    public void setBpm(Double bpm) { this.bpm = bpm; }

    public Double getAvgBpm() { return avgBpm; }
    public void setAvgBpm(Double avgBpm) { this.avgBpm = avgBpm; }

    public Instant getReceivedAt() { return receivedAt; }
    public void setReceivedAt(Instant receivedAt) { this.receivedAt = receivedAt; }
}

