package AB.Backend.HourMachine;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineHourRepo extends CouchbaseRepository<MachineHour, Integer> {

    List<MachineHour> findAllByIdAndsAndStartTimeBetween(int id, long startTime, long endTime);

    List<MachineHour> findAllByStartTimeBetween(long starTime, long endTime);
}
