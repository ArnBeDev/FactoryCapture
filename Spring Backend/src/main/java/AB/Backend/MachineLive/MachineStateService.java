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

    private final MachineStateRepository machineStateRepository;
    private final LiveMachineRepo liveMachineRepo;

    @Autowired
    public MachineStateService(MachineStateRepository machineStateRepository, LiveMachineRepo liveMachineRepo) {
        this.machineStateRepository = machineStateRepository;
        this.liveMachineRepo = liveMachineRepo;
    }

    public void addMachineState(int id, long t, float p, byte state, int wOn) {

        liveMachineRepo.updateMachine(id, p, t, state, wOn);

        MachineState ms = new MachineState(id, t, p, state, wOn);
        machineStateRepository.save(ms);
    }

    public void addMachineStates(List<MachineState> machineStateList) {

        machineStateList
                .stream()
                .forEach(machineState -> {
                    liveMachineRepo.updateMachine(machineState.getMachineId(), machineState.getPower(), machineState.getTimestamp(), machineState.getStateCode(), machineState.getWorkingOn());
                    machineStateRepository.save(machineState);
                });
    }

    public List<MachineState> getAllMachineStates() {
        return machineStateRepository.findAllByTimestampBetween(0, 99999999999999l);
    }

    public List<LiveMachine> getLiveData() {
        return this.liveMachineRepo.getLiveMachineData();
    }

    public List<LiveMachine> getLiveDataById(int id) {
        return liveMachineRepo.getLiveMachineById(id);
    }

    public List<MachineState> getMachineStatesByTimeRange(long startTime, long endTime) {
        return machineStateRepository.findAllByTimestampBetween(startTime, endTime);
    }

    public List<MachineState> getStatesBetween(int id, long startTime, long endTime) {
        return machineStateRepository.findAllByIdAndTimestampBetween(id, startTime, endTime);
    }
}
