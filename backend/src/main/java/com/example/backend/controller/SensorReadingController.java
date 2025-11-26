package com.example.backend.controller;

import com.example.backend.model.SensorReading;
import com.example.backend.service.SensorReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")
public class SensorReadingController {

    @Autowired
    private SensorReadingService service;

    @PostMapping("/sensordata")
    public ResponseEntity<?> receive(@RequestBody SensorReading reading) {
        try {
            System.out.println("========================================");
            System.out.println("📥 Received sensor data:");
            System.out.println("Room Temp: " + reading.getRoomTemp());
            System.out.println("Humidity: " + reading.getHumidity());
            System.out.println("Water Temp C: " + reading.getWaterTempC());
            System.out.println("Water Temp F: " + reading.getWaterTempF());
            System.out.println("IR Value: " + reading.getIrValue());
            System.out.println("BPM: " + reading.getBpm());
            System.out.println("Avg BPM: " + reading.getAvgBpm());

            if (reading.getReceivedAt() == null) {
                reading.setReceivedAt(java.time.Instant.now());
            }

            SensorReading saved = service.save(reading);

            System.out.println("✅ Successfully saved with ID: " + saved.getId());
            System.out.println("Total records in DB: " + service.count());
            System.out.println("========================================");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Data received successfully");
            response.put("id", saved.getId());
            response.put("timestamp", saved.getReceivedAt());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ ERROR saving sensor data:");
            System.err.println("Error message: " + e.getMessage());
            System.err.println("Error class: " + e.getClass().getName());
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            errorResponse.put("errorType", e.getClass().getSimpleName());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/sensordata")
    public ResponseEntity<List<SensorReading>> allReadings() {
        try {
            List<SensorReading> readings = service.findAll();
            System.out.println("📤 Returning " + readings.size() + " readings");
            return ResponseEntity.ok(readings);
        } catch (Exception e) {
            System.err.println("❌ ERROR fetching all readings: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/readings/latest")
    public ResponseEntity<SensorReading> latest() {
        try {
            SensorReading latest = service.findLatest();
            if (latest == null) {
                System.out.println("⚠️ No readings found in database");
                return ResponseEntity.noContent().build();
            }
            System.out.println("📤 Returning latest reading ID: " + latest.getId());
            return ResponseEntity.ok(latest);
        } catch (Exception e) {
            System.err.println("❌ ERROR fetching latest reading: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", java.time.Instant.now());
        try {
            health.put("totalReadings", service.count());
        } catch (Exception e) {
            health.put("error", e.getMessage());
        }
        return ResponseEntity.ok(health);
    }
}