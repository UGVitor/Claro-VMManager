package com.claro.vmmanager.repositories;

import com.claro.vmmanager.models.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {

}
