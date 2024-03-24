package AB.Backend.MachineLive;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
@Data
@NoArgsConstructor
public class MachineState implements Comparable<MachineState> {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private Integer machineId;
    private long timestamp;
    private float power;

    private byte stateCode;
    private int workingOn;


    public MachineState(int machineId, byte stateCode, float power, int workingOn, long timestamp) {

        this.machineId = machineId;
        this.timestamp = timestamp;
        this.power = power;
        this.stateCode = stateCode;
        this.workingOn = workingOn;
    }

    public MachineState(int machineId, long timestamp, float power, byte stateCode, int workingOn) {

        this.machineId = machineId;
        this.timestamp = timestamp;
        this.power = power;
        this.stateCode = stateCode;
        this.workingOn = workingOn;
    }


//    @Override
//    public String toString() {
//        return "MachineState{" +
//                ", machineId=" + machineId +
//                ", timestamp=" + timestamp +
//                ", pConsum=" + power +
//                ", stateCode=" + stateCode +
//                ", workingOn=" + workingOn +
//                '}';
//    }

    @Override
    public int compareTo(MachineState ms) {
        return this.timestamp < ms.getTimestamp() ? -1 : (this.timestamp == ms.getTimestamp() ? 0 : 1);
    }
}