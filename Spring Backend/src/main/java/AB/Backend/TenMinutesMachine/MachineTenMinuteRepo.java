package AB.Backend.TenMinutesMachine;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineTenMinuteRepo extends CouchbaseRepository<MachineTenMinutes,Long> {

@Query("SELECT * FROM REPO m WHERE m.startTime BETWEEN $starTime AND $endTIme")
List<MachineTenMinutes> findBetweenTime(double starTime, double endTime);


}
