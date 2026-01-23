package com.claro.vmmanager.controllers;

import com.claro.vmmanager.dto.VirtualMachineRequestDTO;
import com.claro.vmmanager.dto.VirtualMachineResponseDTO;
import com.claro.vmmanager.dto.VirtualMachineUpdateDTO;
import com.claro.vmmanager.dto.VirtualMachineUpdateStatusDTO;
import com.claro.vmmanager.models.VirtualMachine;
import com.claro.vmmanager.services.VirtualMachineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@RestController
@RequestMapping("/vm")
@Tag(name = "Virtual Machine API", description = "Endpoints for managing virtual machines")
public class VirtualMachineController {
    private final VirtualMachineService vmService;

    public VirtualMachineController(VirtualMachineService vmService){
        this.vmService = vmService;
    }

    @Operation(summary = "Create a new virtual machine", description = "Creates a virtual machine with CPU, RAM, memory, disk and default status")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Virtual machine created successfully", content = @Content(schema = @Schema(implementation = VirtualMachineResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping("/v1")
    public ResponseEntity<VirtualMachineResponseDTO> save(@Valid @RequestBody VirtualMachineRequestDTO dto) {
        VirtualMachineResponseDTO response = vmService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @Operation(summary = "Delete a virtual machine", description = "Deletes a virtual machine by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Virtual machine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Virtual machine not found", content = @Content)})
    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ) {
        vmService.deleteVirtualMachine(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Update virtual machine data", description = "Updates CPU, RAM, memory, disk or name of a virtual machine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Virtual machine updated successfully", content = @Content(schema = @Schema(implementation = VirtualMachineResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Virtual machine not found", content = @Content )})
    @PatchMapping("/v1/{id}")
    public ResponseEntity<VirtualMachineResponseDTO> update(@PathVariable Long id, @RequestBody VirtualMachineUpdateDTO dto) {
        return ResponseEntity.ok(vmService.updateVirtualMachine(id, dto));
    }
    @Operation(
            summary = "Update virtual machine status",
            description = "Updates the status of a virtual machine (RUNNING, STOPPED, SUSPENDED)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Virtual machine status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Virtual machine not found", content = @Content)})
    @PatchMapping("/v1/status/{id}")
    public ResponseEntity<Void> updateStatusVM(@PathVariable Long id, @RequestBody VirtualMachineUpdateStatusDTO dto) {
        vmService.updateStatusVM(id, dto.status());
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get all virtual machines", description = "Returns a list of all registered virtual machines")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Virtual machines retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No virtual machines found")})
    @GetMapping("/v1")
    public ResponseEntity<List<VirtualMachineResponseDTO>> findAllVMs() {

        List<VirtualMachineResponseDTO> response = vmService.getAllVMs()
                .stream()
                .map(vm -> new VirtualMachineResponseDTO(
                        vm.getId(),
                        vm.getName(),
                        vm.getCpu(),
                        vm.getRam(),
                        vm.getMemory(),
                        vm.getDisk(),
                        vm.getStatus(),
                        vm.getDataCriacao()
                ))
                .toList();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Get virtual machine by ID", description = "Returns the details of a specific virtual machine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Virtual machine found", content = @Content(schema = @Schema(implementation = VirtualMachineResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Virtual machine not found", content = @Content)})
    @GetMapping("/v1/{id}")
    public ResponseEntity<VirtualMachineResponseDTO> findVMById(@PathVariable Long id) {
        VirtualMachine vm = vmService.findVMById(id);

        VirtualMachineResponseDTO response = new VirtualMachineResponseDTO(
                vm.getId(),
                vm.getName(),
                vm.getCpu(),
                vm.getRam(),
                vm.getMemory(),
                vm.getDisk(),
                vm.getStatus(),
                vm.getDataCriacao()
        );

        return ResponseEntity.ok(response);
    }
    }

