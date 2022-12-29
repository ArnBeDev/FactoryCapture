package AB.Backend.DailyMachines;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path ="api/dailyMachines")
public class MachineDailyController {

    private final MachineDailyService machineDailyService;

    @Autowired
    public MachineDailyController(MachineDailyService machineDailyService){
        this.machineDailyService = machineDailyService;
    }


    @GetMapping(path ="{id}")
    public List<MachineDaily> getAllDailyMachines(){
        return null;
    }






}
