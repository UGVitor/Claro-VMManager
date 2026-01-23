package com.claro.vmmanager.repositories;

import com.claro.vmmanager.models.VmTaskExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VmTaskExecutionRepository extends JpaRepository<VmTaskExecution, Long> {
}
