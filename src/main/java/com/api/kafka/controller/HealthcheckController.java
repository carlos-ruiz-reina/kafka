package com.api.kafka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthcheckController {

    @GetMapping
    public ResponseEntity<Object> healthCheck() {
	Map<String, String> response = new HashMap<>();
	response.put("status", "OK");
	return ResponseEntity.ok(response);
    }

}