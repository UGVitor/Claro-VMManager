package com.claro.vmmanager.services;

import com.claro.vmmanager.dto.VirtualMachineRequestDTO;
import com.claro.vmmanager.dto.VirtualMachineResponseDTO;
import com.claro.vmmanager.dto.VirtualMachineUpdateDTO;
import com.claro.vmmanager.models.VirtualMachine;
import com.claro.vmmanager.models.enums.Status;
import com.claro.vmmanager.repositories.VirtualMachineRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VirtualMachineService {

    @Autowired
    private VirtualMachineRepository vmRepository;

    @Transactional
   public VirtualMachineResponseDTO save(@Valid VirtualMachineRequestDTO dto) {

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

        @Transactional
        public void deleteVirtualMachine(Long id) {
       Optional<VirtualMachine> vm = vmRepository.findById(id);
       if (vm.isPresent()) {
           vmRepository.delete(vm.get());
       } else {
           throw new EntityNotFoundException("Machine with id " + id + " was not found");
       }
    }
    @Transactional
    public VirtualMachineResponseDTO updateVirtualMachine(Long id, VirtualMachineUpdateDTO updateDTO) {

        VirtualMachine vm = vmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Machine with id " + id + " was not found"));

        if (updateDTO.name() != null) vm.setName(updateDTO.name());
        if (updateDTO.cpu() != null) vm.setCpu(updateDTO.cpu());
        if (updateDTO.ram() != null) vm.setRam(updateDTO.ram());
        if (updateDTO.memory() != null) vm.setMemory(updateDTO.memory());
        if (updateDTO.disk() != null) vm.setDisk(updateDTO.disk());

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

    @Transactional
    public void updateStatusVM(Long id, Status status) {

        VirtualMachine vm = vmRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Machine with id " + id + " was not found")
                );

        vm.setStatus(status);
    }

    @Transactional
    public List<VirtualMachine> getAllVMs(){
        return vmRepository.findAll();
    }

    @Transactional
    public VirtualMachine findVMById(Long id){
        return vmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Machine with id " + id + " was not found"));
    }


}

