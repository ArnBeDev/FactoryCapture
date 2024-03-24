package AB.Backend.MachineError;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
public class MachineError {

    @Id
    private String id;

    private int errorType;

    private int machineId;

    private long startTime;

    private long endTime;

    @Transient
    private int line;


    private int partId; // 0 if no part was present during the error

    private int getLine(){
        if(machineId<15){
            return 1;
        }else{
            return 2;
        }
    }
}
