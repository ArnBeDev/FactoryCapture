package AB.Backend.HourMachine;

import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.*;

import static AB.Backend.FactoryStructure.Machine.getTimeRangeMachineStatusTreeMap;

@Document
@Getter
@Setter
public class MachineHour {


    List<MachineTenMinutes> machineTenList;



    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private int machineId;
    private List<Integer> workedOn;

    private float power;
    private float powerLow;
    private float powerPeak;

    private int workingTime;//in ms
    private int idleTime;//
    private int errorTime;


    private int timePerPart;
    private double actualTime;

    private long startTime;

    @Transient
    private TreeMap<TimeRange, MachineStatus> timeLine;

    private long[] timeRange;
    private int[] machineStates;


    public MachineHour(List<MachineTenMinutes> tenMinutesMachines){
        this.machineTenList = new ArrayList<MachineTenMinutes>();
        processTenMinutes();


    }
    private void processTenMinutes(){
        Collections.sort(machineTenList);

        init();
        this.startTime = machineTenList.get(0).getStartTime();

        for(int i =0; i<machineTenList.size();i++){



            MachineTenMinutes machineTen = machineTenList.get(i);
            addTimeLine(machineTen.getTimeLine());

            addWorkedOn(machineTen.getWorkedOn());
            this.errorTime += machineTen.getErrorTime();
            this.idleTime += machineTen.getIdleTime();
            this.workingTime += machineTen.getWorkingTime();
            this.actualTime += machineTen.getActualTime();
            if(i==0){
                this.power = machineTen.getPower();
                this.powerLow = machineTen.getIdlePower();
                this.powerPeak = machineTen.getPowerPeak();
            }else{
                if(this.powerPeak < machineTen.getPowerPeak()){
                    this.powerPeak = machineTen.getPowerPeak();
                }
                if(this.powerLow >machineTen.getPowerLow()){
                    this.powerLow = machineTen.getPowerLow();
                }
            }


        }

        this.timePerPart =(int) (workedOn.size()/actualTime);


            setTimeLineAsArrays();
            dissolveDuplicates();

    }






    private void addWorkedOn(List<Integer> ids){
        for (Integer i: ids
             ) {
            if(!workedOn.contains(i)){
                workedOn.add(i);
            }
        }
    }

    private void addTimeLine(TreeMap<TimeRange,MachineStatus> tenMinuteTimeLine){
        for(Map.Entry<TimeRange,MachineStatus> tl : tenMinuteTimeLine.entrySet()){
            this.timeLine.put(tl.getKey(),tl.getValue());

        }
    }

    public TreeMap<TimeRange, MachineStatus> getTimeLine(){
        if(timeLine ==null)
        return getTimeRangeMachineStatusTreeMap(timeRange, machineStates);
        else return timeLine;

    }

    private void setTimeLineAsArrays(){
        this.timeRange = new long[timeLine.size()];
        this.machineStates= new int[timeRange.length];
        int index =0;
        for(Map.Entry<TimeRange,MachineStatus> entry : timeLine.entrySet()){
            timeRange[index] =entry.getKey().getStartTime();
            timeRange[index+1] =entry.getKey().getEndTime();
            machineStates[index] = entry.getValue().getStateCode();
            machineStates[index+1] = entry.getValue().getWorkOn();
            index++;
            index++;
        }

    }


    private void init (){
        errorTime=0;
        idleTime =0;
        workingTime=0;
        actualTime =0;
        timeLine = new TreeMap<>();
    }

    private void dissolveDuplicates() {
        //dissolve duplicates in Timeline
        int lastState = 0;
        for (int i = 0; i < timeRange.length; i++) {
            lastState = machineStates[i];
            if (i == 0) {
                continue;
            } else if (machineStates[i - 2] == lastState) {
                //found duplicate
                // short arrays
                //
                long[] rangeNew = new long[timeRange.length - 2];
                int[] statesNew = new int[timeRange.length - 2];
                for (int j = 0; j < timeRange.length; j++) {


                    if (j == i) {
                        rangeNew[j - 1] = timeRange[j + 1];
                        statesNew[j - 1] = machineStates[j + 1];
                         j++;
                    } else {
                        rangeNew[j] = timeRange[j];
                        statesNew[j] = machineStates[j];


                    }



                }


           timeRange = rangeNew;
                machineStates = statesNew;

            }
            i++;

        }

    }
}
