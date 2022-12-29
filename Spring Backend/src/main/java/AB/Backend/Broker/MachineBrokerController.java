package AB.Backend.Broker;

import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.MachineStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sendmachine/")
public class MachineBrokerController
{

    private final MachineStateService machineStateService;

    @Autowired
    public MachineBrokerController(MachineStateService machineStateService){

    this.machineStateService=machineStateService;
    }

    // empfängt zustand einer maschine
    //reihenfolge ist imt put request egal
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
        System.out.println("daten wurden empfangen");

        System.out.println(machineStateList);
        //machineStateService.addMachineStates(machineStateList);

    }




}
