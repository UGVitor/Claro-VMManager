package com.claro.vmmanager.models;

import com.claro.vmmanager.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbVMs")
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "CPU is required")
    @Positive(message = "CPU must be greater than zero")
    @Column(nullable = false)
    private Integer cpu;

    @NotNull(message = "RAM is required")
    @Positive
    @Column(nullable = false)
    private BigDecimal ram;

    @NotNull(message = "Memory is required")
    @Positive
    @Column(nullable = false)
    private BigDecimal memory;

    @NotNull(message = "Disk is required")
    @Positive
    @Column(nullable = false)
    private BigDecimal disk;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.CREATED;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public void setRam(BigDecimal ram) {
        this.ram = ram;
    }

    public BigDecimal getMemory() {
        return memory;
    }

    public void setMemory(BigDecimal memory) {
        this.memory = memory;
    }

    public BigDecimal getDisk() {
        return disk;
    }

    public void setDisk(BigDecimal disk) {
        this.disk = disk;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
