package AB.Backend.MachineLive;

import AB.Backend.FactoryStructure.Machine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

import java.util.Comparator;


@Document
@Setter
@Getter
public class MachineState implements Comparable<MachineState> {

@Id
@GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private Integer machineId;
    private long timestamp;
    private float power;

    private byte stateCode;
    private int workingOn;




    public MachineState(int machineId,byte s,float p,int w,long t){

        this.machineId =machineId;
        this.timestamp = t;
        this.power = p;
        this.stateCode=s;
        this.workingOn = w;
    }
    public MachineState(int machineId,long timestamp,float power,byte stateCode,int workingOn){

        this.machineId =machineId;
        this.timestamp = timestamp;
        this.power = power;
        this.stateCode=stateCode;
        this.workingOn = workingOn;
    }

    public MachineState() {

    }





    @Override
    public String toString() {
        return "MachineState{" +
                ", machineId=" + machineId +
                ", timestamp=" + timestamp +
                ", pConsum=" +  power +
                ", stateCode=" + stateCode +
                ", workingOn=" + workingOn +
                '}';
    }



    @Override
    public int compareTo(MachineState ms) {
        return this.timestamp < ms.getTimestamp() ? -1 : (this.timestamp == ms.getTimestamp() ? 0 : 1);
    }
}