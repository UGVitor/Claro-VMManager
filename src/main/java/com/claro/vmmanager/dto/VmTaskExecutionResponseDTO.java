package com.claro.vmmanager.dto;

import java.time.LocalDateTime;

public record VmTaskExecutionResponseDTO(
        String username,
        String vmName,
        String action,
        LocalDateTime executedAt
) {}
