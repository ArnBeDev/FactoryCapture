package AB.Backend.Broker;

import AB.Backend.DailyMachines.MachineDaily;
import AB.Backend.DailyMachines.MachineDailyService;
import AB.Backend.HourMachine.MachineHour;
import AB.Backend.HourMachine.MachineHourService;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.MachineStateService;
import AB.Backend.ProducedParts.Part;
import AB.Backend.ProducedParts.PartService;

import AB.Backend.TenMinutesMachine.MachineTenMinService;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * RestController, API for receiving machine data
 */
@RestController
@RequestMapping("/sendmachine/")
public class MachineBrokerController
{

    private final MachineStateService machineStateService;
    private final PartService partService;

    private boolean test;
    private final MachineTenMinService tenMinService; //only for debugging
    private final MachineHourService machineHourService;//only for debugging
    private final MachineDailyService machineDailyService;//only for debugging

    @Autowired
    public MachineBrokerController(MachineStateService machineStateService, PartService partService,
                                   MachineTenMinService mtService,MachineHourService mhService, MachineDailyService mdService){

    this.machineStateService=machineStateService;
    this.partService = partService;
    this.tenMinService = mtService;
    this.machineHourService = mhService;
    this.machineDailyService = mdService;

    test = true;
    }

    //http://localhost:8080/sendmachine/1?s=2&p=85.43&w=13&t=240001.444
   // @PutMapping(path ="{id}")
    public void receiveData(@PathVariable int id,
                            @RequestParam byte s,
                            @RequestParam float p,
                            @RequestParam int w,
                            @RequestParam long t
                            ){
        System.out.println("PUT RECEIVED");
      //  machineStateService.addMachineState(id,2d,3f,(byte)4,5);
        machineStateService.addMachineState(id,t,p,s,w);
    }

    @PutMapping
    public void receiveStates(@RequestBody List<MachineState> machineStateList){
        machineStateService.addMachineStates(machineStateList);
        partService.passNewMachineStates(machineStateList);
    }
}
