package AB.Backend.HourMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineHourService {

    private final MachineHourRepo machineHourRepo;

    @Autowired
    public MachineHourService(MachineHourRepo machineHourRepo) {
        this.machineHourRepo = machineHourRepo;
    }

    public List<MachineHour> getBetween(int id, long start, long end) {

        return machineHourRepo.findAllByIdAndsAndStartTimeBetween(id, start, end);
    }
}
