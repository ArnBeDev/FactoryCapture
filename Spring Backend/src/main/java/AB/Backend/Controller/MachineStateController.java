package AB.Backend.Controller;


import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.MachineStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * RestController, API for aggregated machine data
 */
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/machinestate")
public class MachineStateController {


    private final MachineStateService ms;



    @Autowired
    public MachineStateController(MachineStateService ms){

        this.ms=ms;
    }


    @GetMapping(path = "/between")
    public List<MachineState> getMachineStatesBetween(@RequestBody Map<String,String> body){
            int gid = Integer.parseInt(body.get("id"));
            long start = Long.parseLong(body.get("starttime"));
            long end = Long.parseLong((body.get("endtime")));
            return ms.getStatesBetween(gid,start,end);

    }



}
