package AB.Backend.FactoryStructure;

import AB.Backend.FactoryStructure.Unit;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import lombok.Getter;
import lombok.Setter;

import java.util.TreeMap;

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

    public static TreeMap<TimeRange, MachineStatus> getTimeRangeMachineStatusTreeMap(long[] timeRange, int[] machineStatus) {
        TreeMap<TimeRange,MachineStatus> tLine = new TreeMap<>();
        TimeRange tr;
        MachineStatus ms;

        for(int i = 0; i< timeRange.length; i++){
            if(i%2==1){
                tr = new TimeRange(timeRange[i-1], timeRange[i]);
                ms = new MachineStatus(machineStatus[i-1], machineStatus[i]);
                tLine.put(tr,ms);
            }
        }
        return tLine;
    }
}
