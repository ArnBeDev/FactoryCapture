package AB.Backend.FactoryStructure;

import AB.Backend.FactoryStructure.Unit;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Machine {
    private int machineID;
    private float pConsumTotal;

    private float  pConsumAvg;

    private float pConsumPeak;
    private float pConsumIdle;
    private float pConsumCurrent;

    private int parallelWith;
    private int before;
    private int after;

    private int enterTime;
    private int exitTime;

    private Unit partOf;

    private double lastWorking;

    public static final int WORK_CODE =2;
    public static final int IDLE_CODE =1;
    public Machine(){

    }
    public Machine(int id){
        this.machineID = id;


    }

}
