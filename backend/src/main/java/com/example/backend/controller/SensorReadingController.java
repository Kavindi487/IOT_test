package com.example.backend.controller;

import com.example.backend.model.SensorReading;
import com.example.backend.service.SensorReadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensordata")
@CrossOrigin(origins = "*") // open for testing; restrict origin in production
public class SensorReadingController {

    private final SensorReadingService service;

    public SensorReadingController(SensorReadingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> receive(@RequestBody SensorReading reading) {
        service.save(reading);

        // simple logging
        System.out.println("Received sensor reading at " + reading.getReceivedAt());
        System.out.println("RoomTemp: " + reading.getRoomTemp() + "C, Humidity: " + reading.getHumidity() + "%");
        System.out.println("WaterTemp: " + reading.getWaterTempC() + "C / " + reading.getWaterTempF() + "F");
        System.out.println("IR: " + reading.getIrValue() + " BPM: " + reading.getBpm() + " Avg: " + reading.getAvgBpm());
        System.out.println("Total saved rows: " + service.count());

        return ResponseEntity.ok("Data received");
    }

    @GetMapping
    public ResponseEntity<List<SensorReading>> allReadings() {
        return ResponseEntity.ok(service.findAll());
    }
}
