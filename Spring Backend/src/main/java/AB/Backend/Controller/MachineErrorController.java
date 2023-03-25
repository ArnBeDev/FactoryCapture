package AB.Backend.Controller;


import AB.Backend.MachineError.MachineError;
import AB.Backend.MachineError.MachineErrorRepo;
import AB.Backend.MachineError.MachineErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/api/error")
public class MachineErrorController {

    private final MachineErrorService machineErrorService;


    @Autowired
    public MachineErrorController(MachineErrorService machineErrorService){
        this.machineErrorService=machineErrorService;
    }



    @GetMapping(path="/{machineId}/all")
    public List<MachineError> getAllErrorForMachine(@PathVariable int machineId) {
        return machineErrorService.getAllErrorFromMachine(machineId);
    }

    @GetMapping(path="/{machineId}/between")
    public List<MachineError> getAllErrorForMachineBetweenTime(@PathVariable int machineId, @PathVariable long startTime, @PathVariable long endTime){
        return machineErrorService.getAllErrorFromMachineBetweenTime(machineId,startTime,endTime);
    }

    @GetMapping(path="/{machineId}/last10")
    public List<MachineError> getLast10ErrorForMachine(@PathVariable int machineId){
    return machineErrorService.getLast10ErrorsFromMachine(machineId);


    }





}
