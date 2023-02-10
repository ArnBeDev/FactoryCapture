package AB.Backend.TenMinutesMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineTenMinService {

    private final MachineTenMinuteRepo machineTenMinuteRepo;

    @Autowired
    public MachineTenMinService(MachineTenMinuteRepo machineTenMinuteRepo){
        this.machineTenMinuteRepo = machineTenMinuteRepo;
    }


    public void testSaveTenMinutes(MachineTenMinutes mt){
        machineTenMinuteRepo.save(mt);
    }





}
