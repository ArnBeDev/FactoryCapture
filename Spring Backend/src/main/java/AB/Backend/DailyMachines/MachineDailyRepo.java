package AB.Backend.DailyMachines;

import AB.Backend.MachineLive.MachineState;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MachineDailyRepo extends CouchbaseRepository<MachineDaily, Integer> {



    List<MachineDaily> findTop7ByIdOOrderByStartTime(int id);

    List<MachineDaily> findByIdAndsAndStartTimeBetween(int id, long startTime,long endTime);

}
