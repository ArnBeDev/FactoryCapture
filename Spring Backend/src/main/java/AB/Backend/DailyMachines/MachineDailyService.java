package AB.Backend.DailyMachines;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineDailyService {

    private final MachineDailyRepo machineDailyRepo;
    @Autowired
    public MachineDailyService(MachineDailyRepo machineDailyRepo){
        this.machineDailyRepo = machineDailyRepo;
    }


}
