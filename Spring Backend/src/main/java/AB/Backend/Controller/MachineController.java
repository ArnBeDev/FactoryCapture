package AB.Backend.Controller;


import AB.Backend.FactoryStructure.Machine;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.MachineStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/machinestate")
public class MachineController {


    private final MachineStateService ms;



    @Autowired
    public MachineController(MachineStateService ms){

        this.ms=ms;
    }



@GetMapping
    public Machine getMachine(@RequestParam(required = false) String id){


        if(id ==null){
            return new Machine(31);
        }

        System.out.println("Request for: " +id);

        int i = Integer.parseInt(id);

        return (new Machine(3));
    }
    @GetMapping("/ask/{id}")
    public List<MachineState> getAnswer(@PathVariable String id){
        return ms.getAllMachineStates();
    }



}
