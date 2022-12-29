package AB.Backend.MachineLive;


import AB.Backend.FactoryStructure.Machine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiveMachine {

    private float power;
    private int id;
    private byte stateCode;
    private int workingOn;

    private double time;


    public void update(int id, float power, byte stateCode,int workingOn,double time){
        this.id =id;
        this.power = power;
        this.stateCode = stateCode;
        this.workingOn=workingOn;
        this.time =time;

    }

    public LiveMachine( int id,float power, byte stateCode, int workingOn, double time) {
        this.power = power;
        this.id = id;
        this.stateCode = stateCode;
        this.workingOn = workingOn;
        this.time = time;
    }
}
