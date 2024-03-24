package AB.Backend.Controller;

import AB.Backend.WeeklyMachines.MachineWeekly;
import AB.Backend.WeeklyMachines.MachineWeeklyService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/weekly/")
public class MachineWeeklyController {


    private final MachineWeeklyService weeklyService;

    @Autowired
    public MachineWeeklyController(MachineWeeklyService weeklyService) {
        this.weeklyService = weeklyService;
    }

    @GetMapping(path = "/{id})")
    public List<MachineWeekly> getAllStates(@PathVariable int id) {
        return weeklyService.getAllById(id);
    }

    @GetMapping(path = "/between")
    public List<MachineWeekly> getAllBetween(@RequestBody Map<String, String> body) {
        int id = Integer.parseInt(body.get("id"));
        long startTime = Long.parseLong(body.get("starttime"));
        long endTime = Long.parseLong(body.get("endtime"));


        return weeklyService.getAllByIdBetweem(id, startTime, endTime);
    }

    @GetMapping(path = "/{id}/last")
    public List<MachineWeekly> getLast7(@PathVariable int id) {
        return weeklyService.getLast7(id);
    }
}
