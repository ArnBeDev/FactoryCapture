package AB.Backend.WeeklyMachines;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineWeeklyRepo extends CouchbaseRepository<MachineWeekly,Long> {
}
