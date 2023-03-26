package AB.Backend.WeeklyMachines;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineWeeklyRepo extends CouchbaseRepository<MachineWeekly,Long> {


    List<MachineWeekly> findAllById(int id);

    List<MachineWeekly> findAllByIdAndTimeStampBetween(int id,long startTime, long endTime);

    List<MachineWeekly> findTop7ByIdAndOrderByTimeStampDesc(int id);



}
