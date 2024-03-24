package AB.Backend.MachineLive;

import lombok.Setter;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Setter
public class LiveMachineRepo {

    List<LiveMachine> machines;

    public List<LiveMachine> getLiveMachineData() {
        return machines;
    }

    public void updateMachine(int id, float power, long timeStamp, byte stateCode, int workingOn) {

        machines.stream()
                .filter(machine -> machine.getId() == id)
                .findFirst()
                .ifPresentOrElse(machine -> machine.update(id, power, stateCode, workingOn, timeStamp), () -> addNewMachine(new LiveMachine(id, power, stateCode, workingOn, timeStamp)));
    }

    public List<LiveMachine> getLiveMachineById(int id) {
        Optional<LiveMachine> requestedMachine = machines.stream().filter(liveMachine -> liveMachine.getId() == id).findFirst();
        return requestedMachine.map(List::of).orElseGet(() -> machines);
    }

    public LiveMachineRepo() {
        this.machines = new ArrayList<LiveMachine>();
    }

    private void addNewMachine(LiveMachine liveMachine) {
        machines.add(liveMachine);
    }
}
