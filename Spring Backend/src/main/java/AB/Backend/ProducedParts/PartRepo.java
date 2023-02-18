package AB.Backend.ProducedParts;

import AB.Backend.MachineLive.MachineState;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepo extends CouchbaseRepository<Part,Integer> {
    @Override
    Optional<Part> findById(Integer integer);


    List<Part> findAllByProductionEndBetween(long startTime,long endTime);

}
