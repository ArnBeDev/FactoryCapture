package AB.Backend.DailyMachines;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MachineDailyRepo extends CouchbaseRepository<MachineDaily, Integer> {
}
