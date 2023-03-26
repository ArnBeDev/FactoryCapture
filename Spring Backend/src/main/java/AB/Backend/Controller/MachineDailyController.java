package AB.Backend.Controller;


import AB.Backend.DailyMachines.MachineDaily;
import AB.Backend.DailyMachines.MachineDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path ="api/daily")
public class MachineDailyController {

    private final MachineDailyService machineDailyService;

    @Autowired
    public MachineDailyController(MachineDailyService machineDailyService){
        this.machineDailyService = machineDailyService;
    }


    @GetMapping(path="/{id}/last")
    public List<MachineDaily> getLast7(@PathVariable int id){
        return machineDailyService.getLast7(id);
    }


    public List<MachineDaily> getBetween(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        long startTime = Long.parseLong(body.get("start"));
        long endTime = Long.parseLong(body.get("end"));

        return machineDailyService.getBetween(id,startTime,endTime);
    }









}
