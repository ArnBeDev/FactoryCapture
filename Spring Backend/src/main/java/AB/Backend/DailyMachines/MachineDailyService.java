package AB.Backend.DailyMachines;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MachineDailyService {

    private final MachineDailyRepo machineDailyRepo;
    @Autowired
    public MachineDailyService(MachineDailyRepo machineDailyRepo) {
        this.machineDailyRepo = machineDailyRepo;
    }

    public List<MachineDaily> getLast7(@PathVariable int id) {

        return machineDailyRepo.findTop7ByIdOOrderByStartTime(id);
    }

    public List<MachineDaily> getBetween(int id, long startTime, long endTime) {
        return machineDailyRepo.findByIdAndsAndStartTimeBetween(id, startTime, endTime);
    }
}
