package com.claro.vmmanager.services;

import com.claro.vmmanager.dto.VmTaskExecutionResponseDTO;
import com.claro.vmmanager.models.VmTaskExecution;
import com.claro.vmmanager.repositories.VmTaskExecutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VmTaskExecutionService {

    private final VmTaskExecutionRepository repository;

    public VmTaskExecutionService(VmTaskExecutionRepository repository) {
        this.repository = repository;
    }

    public void log(String username, String vmName, String action) {
        repository.save(
                new VmTaskExecution(username, vmName, action)
        );
    }

    public List<VmTaskExecutionResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(task -> new VmTaskExecutionResponseDTO(
                        task.getUsername(),
                        task.getVmName(),
                        task.getAction(),
                        task.getExecutedAt()
                ))
                .toList();
    }
}
