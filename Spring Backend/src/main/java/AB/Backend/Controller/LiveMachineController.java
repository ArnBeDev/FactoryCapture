package AB.Backend.Controller;

import AB.Backend.MachineLive.LiveMachine;
import AB.Backend.MachineLive.MachineStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/live")
public class LiveMachineController {

    private final MachineStateService machineStateService;

    @Autowired
    public LiveMachineController(MachineStateService machineStateService) {
        this.machineStateService = machineStateService;
    }

    @GetMapping(path = "/")
    public List<LiveMachine> getLiveData(@RequestParam(required = false) String id) {
        if (id == null) {
            return machineStateService.getLiveData();
        } else {
            try {
                int i = Integer.parseInt(id);
                return machineStateService.getLiveDataById(i);
            } catch (Exception e) {
                return machineStateService.getLiveData();
            }
        }
    }
}



