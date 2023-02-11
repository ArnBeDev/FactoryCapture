package AB.Backend.MachineLive;

import AB.Backend.MachineLive.LiveMachine;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.LiveMachineRepo;
import AB.Backend.MachineLive.MachineStateRepository;
import AB.Backend.Models.MachineStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineStateService {

    private int idCounter;
    private final MachineStateRepository machineStateRepository;
    private final LiveMachineRepo liveMachineRepo;

    @Autowired
    public MachineStateService(MachineStateRepository machineStateRepository, LiveMachineRepo liveMachineRepo){
        this.machineStateRepository = machineStateRepository;

        this.liveMachineRepo = liveMachineRepo;
        //TODO find highest Id in repo =idCounter+1 ;s
        idCounter =1;
    }
    public void addMachineState(int id,long t,float p,byte state, int wOn){


        liveMachineRepo.updateMachine(id,p,t,state,wOn);

        MachineState ms = new MachineState(getId(),id,t,p,state,wOn);


        machineStateRepository.save(ms);
    }

    public void addMachineStates(List<MachineState> machineStateList){
        for(MachineState ms :machineStateList){
            liveMachineRepo.updateMachine(ms.getMachineId(),ms.getPower(),ms.getTimestamp(),ms.getStateCode(),ms.getWorkingOn());
            ms.setId(getId());
            machineStateRepository.save(ms);
        }
      //  System.out.println("MachineStatesList saved");
    }


    private int getId(){
        if(idCounter >1000000){
            idCounter =1;
        }
        return idCounter++;
    }

    public List<MachineState> getAllMachineStates  (){
        return machineStateRepository.findAll();
    }

    public List<LiveMachine> getLiveData(){
        return this.liveMachineRepo.getLiveMachineData();
    }
    public List<LiveMachine> getLiveDataById(int id){
        return liveMachineRepo.getLiveMachineById(id);
    }

    public List<MachineState> getMachineStatesByTimeRange(long startTime, long endTime){
        return machineStateRepository.findBetweenTime(startTime,endTime);
    }

}
