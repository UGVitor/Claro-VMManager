package com.claro.vmmanager.controllers;

import com.claro.vmmanager.dto.VirtualMachineRequestDTO;
import com.claro.vmmanager.dto.VirtualMachineResponseDTO;
import com.claro.vmmanager.services.VirtualMachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vm")
public class VirtualMachineController {
    private final VirtualMachineService vmService;

    public VirtualMachineController(VirtualMachineService vmService){
        this.vmService = vmService;
    }

    @PostMapping
    public ResponseEntity<VirtualMachineResponseDTO> save(@Valid @RequestBody VirtualMachineRequestDTO dto) {
        VirtualMachineResponseDTO response = vmService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ) {
        vmService.deleteVirtualMachine(id);
        return ResponseEntity.noContent().build();
    }
}
