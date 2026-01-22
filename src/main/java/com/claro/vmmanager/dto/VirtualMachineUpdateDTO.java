package com.claro.vmmanager.dto;

import java.math.BigDecimal;

public record VirtualMachineUpdateDTO(
        String name,
        Integer cpu,
        BigDecimal ram,
        BigDecimal memory,
        BigDecimal disk) {
}
