package AB.Backend.MachineLive;

import AB.Backend.MachineLive.MachineState;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineStateRepository extends CrudRepository<MachineState, String> {

    @Override
    Optional<MachineState> findById(String s);

    List<MachineState> findAllByTimestampBetween(long startTime, long endTime);

    List<MachineState> findByMachineId(Integer id);

    List<MachineState> findAllByIdAndTimestampBetween(int id, long startTime, long endTime);
}
