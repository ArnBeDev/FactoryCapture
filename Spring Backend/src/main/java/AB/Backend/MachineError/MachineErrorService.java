package AB.Backend.MachineError;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineErrorService {

    private final MachineErrorRepo machineErrorRepo;

    @Autowired
    public MachineErrorService(MachineErrorRepo machineErrorRepo){
        this.machineErrorRepo = machineErrorRepo;
    }

    public List<MachineError> getAllErrorFromMachine(int machineId){
        return machineErrorRepo.findAllByMachineId(machineId);
    }

    public List<MachineError> getAllErrorFromMachineBetweenTime(int machineId,long startTime,long endTime){
        return machineErrorRepo.findAllByMachineIdAndStartTimeBetween(machineId,startTime,endTime);
    }

    public List<MachineError> getLast10ErrorsFromMachine(int machineId){
            return machineErrorRepo.findTop10ByMachineIdOrderByStartTime(machineId);
    }
}
