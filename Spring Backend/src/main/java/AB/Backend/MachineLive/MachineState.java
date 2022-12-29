package AB.Backend.MachineLive;

import AB.Backend.FactoryStructure.Machine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

import java.util.Comparator;


@Document
@Setter
@Getter
public class MachineState implements Comparable<MachineState> {

@Id
    private Integer id;
    private int machineId;
    private long timestamp;
    private float power;


    //1 = idle, 2= working, 0=maintance  , 3/4 leichte/starke einschränkungen, ab 5 defekt

    private byte stateCode;
    private int workingOn;




    public MachineState(int machineId,long timestamp,float power,byte stateCode,int workingOn){

        this.machineId =machineId;
        this.timestamp = timestamp;
        this.power = power;
        this.stateCode=stateCode;
        this.workingOn = workingOn;
    }
    public MachineState(int id,int machineId,long timestamp,float power,byte stateCode,int workingOn){
        this.id = id;
        this.machineId =machineId;
        this.timestamp = timestamp;
        this.power = power;
        this.stateCode=stateCode;
        this.workingOn = workingOn;
    }

    public MachineState() {
        this.id = null;
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