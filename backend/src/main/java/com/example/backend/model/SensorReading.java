package com.example.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Double roomTemp;

    @Column(nullable = true)
    private Double humidity;

    @Column(nullable = true)
    private Double waterTempC;

    @Column(nullable = true)
    private Double waterTempF;

    @Column(nullable = true)
    private Long irValue;

    @Column(nullable = true)
    private Double bpm;

    @Column(nullable = true)
    private Double avgBpm;

    @Column(nullable = false)
    private Instant receivedAt;

    public SensorReading() {}

    @PrePersist
    protected void onCreate() {
        if (receivedAt == null) {
            receivedAt = Instant.now();
        }
    }

    // Getters and setters
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

    @Override
    public String toString() {
        return "SensorReading{" +
                "id=" + id +
                ", roomTemp=" + roomTemp +
                ", humidity=" + humidity +
                ", waterTempC=" + waterTempC +
                ", waterTempF=" + waterTempF +
                ", irValue=" + irValue +
                ", bpm=" + bpm +
                ", avgBpm=" + avgBpm +
                ", receivedAt=" + receivedAt +
                '}';
    }
}