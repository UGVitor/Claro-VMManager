package com.claro.vmmanager.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vm_task_execution")
public class VmTaskExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String vmName;

    private String action;

    private LocalDateTime executedAt;

    public VmTaskExecution() {}

    public VmTaskExecution(String username, String vmName, String action) {
        this.username = username;
        this.vmName = vmName;
        this.action = action;
        this.executedAt = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

