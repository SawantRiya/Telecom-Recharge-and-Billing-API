package com.telecom.controller;

import com.telecom.repository.ConnectionTestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionTestController {

    private final ConnectionTestRepository connectionTestRepository;

    public ConnectionTestController(
            ConnectionTestRepository connectionTestRepository) {

        this.connectionTestRepository = connectionTestRepository;
    }

    @GetMapping("/test-connection")
    public String testConnection() {

        return connectionTestRepository.testConnection();
    }
}