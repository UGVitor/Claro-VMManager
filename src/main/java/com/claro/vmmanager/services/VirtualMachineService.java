package com.claro.vmmanager.services;

import com.claro.vmmanager.dto.VirtualMachineRequestDTO;
import com.claro.vmmanager.dto.VirtualMachineResponseDTO;
import com.claro.vmmanager.models.VirtualMachine;
import com.claro.vmmanager.repositories.VirtualMachineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VirtualMachineService {

    @Autowired
    private VirtualMachineRepository vmRepository;

   public VirtualMachineResponseDTO save(VirtualMachineRequestDTO dto) {

            VirtualMachine vm = new VirtualMachine();
            vm.setName(dto.name());
            vm.setCpu(dto.cpu());
            vm.setRam(dto.ram());
            vm.setMemory(dto.memory());
            vm.setDisk(dto.disk());

            VirtualMachine savedVm = vmRepository.save(vm);

            return new VirtualMachineResponseDTO(
                    savedVm.getId(),
                    savedVm.getName(),
                    savedVm.getCpu(),
                    savedVm.getRam(),
                    savedVm.getMemory(),
                    savedVm.getDisk(),
                    savedVm.getStatus(),
                    savedVm.getDataCriacao()
            );
        }

        public void deleteVirtualMachine(Long id) {
       Optional<VirtualMachine> vm = vmRepository.findById(id);
       if (vm.isPresent()) {
           vmRepository.delete(vm.get());
       } else {
           throw new EntityNotFoundException("Machine with id " + id + " does not exist");
       }
        }
    }

