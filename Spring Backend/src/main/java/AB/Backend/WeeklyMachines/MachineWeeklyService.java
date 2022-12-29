package AB.Backend.WeeklyMachines;

import AB.Backend.DailyMachines.MachineDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineWeeklyService {

    private final MachineWeeklyRepo machineWeeklyRepo;

    @Autowired
    public MachineWeeklyService(MachineWeeklyRepo machineWeeklyRepo){
        this.machineWeeklyRepo =machineWeeklyRepo;
    }
}
