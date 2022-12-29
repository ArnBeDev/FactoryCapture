package AB.Backend.HourMachine;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineHourRepo extends CouchbaseRepository<MachineHour,Integer> {


}
