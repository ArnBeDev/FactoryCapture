package AB.Backend.Models;

import AB.Backend.MachineLive.MachineState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineStatus {

    private int stateCode;
    private int workOn;

    public MachineStatus(int stateCode, int workOn){
        this.stateCode = stateCode;
        if(stateCode!=2){
            this.workOn = 0;
        }else{
            this.workOn=workOn;
        }

    }

    public MachineStatus(int stateCode){
        this.stateCode =stateCode;
        workOn =-1;
    }
}
