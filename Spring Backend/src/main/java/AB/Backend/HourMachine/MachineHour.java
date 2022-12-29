package AB.Backend.HourMachine;

import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.*;

@Document
@Getter
@Setter
public class MachineHour {


    List<MachineTenMinutes> machineTenList;



    @Id
    private int id;
    private int machineId;
    private List<Integer> workedOn;

    private float power;
    private float powerIdle;
    private float powerPeak;

    private int workingTime;//in ms
    private int idleTime;//
    private int errorTime;


    private int timePerPart;
    private double actualTime;

    private TreeMap<TimeRange, MachineStatus> timeLine;

    public MachineHour(List<MachineTenMinutes> tenMinutesMachines){
        this.machineTenList = new ArrayList<MachineTenMinutes>();
        processTenMinutes();


    }
    private void processTenMinutes(){
        Collections.sort(machineTenList);

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
                this.powerIdle = machineTen.getIdlePower();
                this.powerPeak = machineTen.getPowerPeak();
            }else{
                if(this.powerPeak < machineTen.getPowerPeak()){
                    this.powerPeak = machineTen.getPowerPeak();
                }
                if(this.powerIdle >machineTen.getIdlePower()){
                    this.powerIdle = machineTen.getIdlePower();
                }
            }


        }

        this.timePerPart =(int) (workedOn.size()/actualTime);




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

    private void processTimeLine(){
        //find


    }


}
