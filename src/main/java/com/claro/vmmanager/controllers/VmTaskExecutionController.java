package com.claro.vmmanager.controllers;

import com.claro.vmmanager.dto.VmTaskExecutionResponseDTO;
import com.claro.vmmanager.services.VmTaskExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class VmTaskExecutionController {

    private final VmTaskExecutionService service;

    public VmTaskExecutionController(VmTaskExecutionService service) {
        this.service = service;
    }
    @Operation(summary = "List all VM task executions", description = "Returns a list of all tasks executed on virtual machines")

    @GetMapping("/v1")
    public ResponseEntity<List<VmTaskExecutionResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

