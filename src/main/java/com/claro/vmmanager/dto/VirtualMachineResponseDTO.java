package com.claro.vmmanager.dto;

import com.claro.vmmanager.models.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VirtualMachineResponseDTO (Long id,
                                         String name,
                                         Integer cpu,
                                         BigDecimal ram,
                                         BigDecimal memory,
                                         BigDecimal disk,
                                         Status status,
                                         LocalDateTime dataCriacao) {}
