package com.fintrack.fin.configuration.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public/health")
public class AuthController {

    @GetMapping
    public ResponseEntity<Map<String, String>> health(){
     return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
