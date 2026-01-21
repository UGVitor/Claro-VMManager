package com.claro.vmmanager.dto;

import java.math.BigDecimal;

public record VirtualMachineRequestDTO(String name,
                                       Integer cpu,
                                       BigDecimal ram,
                                       BigDecimal memory,
                                       BigDecimal disk) {
}
