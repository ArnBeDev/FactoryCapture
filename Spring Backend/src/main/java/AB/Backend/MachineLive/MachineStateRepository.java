package AB.Backend.MachineLive;

import AB.Backend.MachineLive.MachineState;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineStateRepository extends CrudRepository<MachineState,Integer> {

    @Override
    Optional<MachineState> findById(Integer integer);



    List<MachineState> findAllByTimestampBetween(long startTime,long endTime);


}
