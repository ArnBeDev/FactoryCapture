package AB.Backend.MachineError;


import AB.Backend.FactoryStructure.Machine;
import AB.Backend.MachineLive.MachineState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineErrorRepo extends CrudRepository<MachineError, String> {

    @Override
    Optional<MachineError> findById(String s);

    List<MachineError> findAllByMachineIdAndStartTimeBetween(int machineId, long startTime, long endTime);

    List<MachineError> findAllByMachineId(int machineId);

    List<MachineError> findTop10ByMachineIdOrderByStartTime(int machineId);
}
