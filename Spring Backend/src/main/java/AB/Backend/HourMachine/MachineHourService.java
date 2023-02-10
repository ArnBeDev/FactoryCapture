package AB.Backend.HourMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineHourService {


    private final MachineHourRepo machineHourRepo;

@Autowired
    public MachineHourService(MachineHourRepo machineHourRepo){
        this.machineHourRepo = machineHourRepo;
    }


    public void testSaveHourMachine(MachineHour mHour){
    machineHourRepo.save(mHour);
    }
}
