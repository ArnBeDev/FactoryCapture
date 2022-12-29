package AB.Backend.WeeklyMachines;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Getter
@Setter
public class MachineWeekly {


    @Id
    private int id;
    private int machineId;







}
