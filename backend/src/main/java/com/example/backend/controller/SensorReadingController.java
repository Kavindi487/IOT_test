package com.example.backend.controller;


import com.example.backend.model.SensorReading;
import com.example.backend.service.SensorReadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://172.30.21.47:3000", "*"})
public class SensorReadingController {


    private final SensorReadingService service;


    public SensorReadingController(SensorReadingService service) {
        this.service = service;
    }


    // Keep existing POST endpoint
    @PostMapping("/sensordata")
    public ResponseEntity<String> receive(@RequestBody SensorReading reading) {
// Ensure receivedAt is set if not provided
        if (reading.getReceivedAt() == null) {
            reading.setReceivedAt(java.time.Instant.now());
        }


        SensorReading saved = service.save(reading);


// debug log
        System.out.println("Received sensor reading: " + saved);
        System.out.println("Total saved rows: " + service.count());


        return ResponseEntity.ok("Data received");
    }


    // Return all readings
    @GetMapping("/sensordata")
    public ResponseEntity<List<SensorReading>> allReadings() {
        return ResponseEntity.ok(service.findAll());
    }


    // New endpoint used by frontend to get the latest reading
    @GetMapping("/readings/latest")
    public ResponseEntity<SensorReading> latest() {
        SensorReading latest = service.findLatest();
        if (latest == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(latest);
    }
}