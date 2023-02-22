package AB.Backend.DailyMachines;

import AB.Backend.MachineLive.MachineState;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MachineDailyRepo extends CouchbaseRepository<MachineDaily, Integer> {


    List<MachineDaily> findAllByStartTimeBetween(long startTime, long endTime);

}
