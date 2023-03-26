package AB.Backend.WeeklyMachines;

import AB.Backend.Controller.MachineWeeklyController;
import AB.Backend.DailyMachines.MachineDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class MachineWeeklyService {

    private final MachineWeeklyRepo machineWeeklyRepo;

    @Autowired
    public MachineWeeklyService(MachineWeeklyRepo machineWeeklyRepo){
        this.machineWeeklyRepo =machineWeeklyRepo;
    }


    public List<MachineWeekly> getAllById(int id){
        return machineWeeklyRepo.findAllById(id);
    }


    public List<MachineWeekly> getAllByIdBetweem(int id,long start, long end){

            return machineWeeklyRepo.findAllByIdAndTimeStampBetween(id,start,end);

    }

    public List<MachineWeekly> getLast7(int id){
    return  machineWeeklyRepo.findTop7ByIdAndOrderByTimeStampDesc(id);

    }
}



