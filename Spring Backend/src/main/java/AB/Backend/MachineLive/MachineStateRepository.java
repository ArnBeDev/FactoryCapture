package AB.Backend.MachineLive;

import AB.Backend.MachineLive.MachineState;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineStateRepository extends CouchbaseRepository<MachineState,Integer> {

    @Override
    Optional<MachineState> findById(Integer integer);


    Optional<MachineState> findAllByTimestamp(double timestamp);
    @Query("SELECT * FROM machineState m WHERE m.t BETWEEN $startTime AND $endTime ")
    List<MachineState> findBetweenTime(double startTime, double endTime);



}
