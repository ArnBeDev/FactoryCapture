package AB.Backend.Controller;


import AB.Backend.HourMachine.MachineHour;
import AB.Backend.HourMachine.MachineHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/hourly")
public class MachineHourlyController {


    private final MachineHourService machineHourService;

    @Autowired
    public MachineHourlyController(MachineHourService machineHourService) {
        this.machineHourService = machineHourService;
    }

    @GetMapping(path = "/between")
    public List<MachineHour> getBetween(@RequestBody Map<String, String> body) {
        int id = Integer.parseInt(body.get("id"));
        long startTime = Long.parseLong(body.get("start"));
        long endTime = Long.parseLong(body.get("end"));
        return machineHourService.getBetween(id, startTime, endTime);
    }
}
