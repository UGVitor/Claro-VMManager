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

import java.util.List;

@RestController
@RequestMapping("/vm")
public class VirtualMachineController {
    private final VirtualMachineService vmService;

    public VirtualMachineController(VirtualMachineService vmService){
        this.vmService = vmService;
    }

    @PostMapping("/v1")
    public ResponseEntity<VirtualMachineResponseDTO> save(@Valid @RequestBody VirtualMachineRequestDTO dto) {
        VirtualMachineResponseDTO response = vmService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ) {
        vmService.deleteVirtualMachine(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/v1/{id}")
    public ResponseEntity<VirtualMachineResponseDTO> update(@PathVariable Long id, @RequestBody VirtualMachineUpdateDTO dto) {
        return ResponseEntity.ok(vmService.updateVirtualMachine(id, dto));
    }

    @PatchMapping("/v1/status/{id}")
    public ResponseEntity<Void> updateStatusVM(@PathVariable Long id, @RequestBody VirtualMachineUpdateStatusDTO dto) {
        vmService.updateStatusVM(id, dto.status());
        return ResponseEntity.noContent().build();
    }

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

