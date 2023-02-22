package AB.Backend.DailyMachines;

import AB.Backend.HourMachine.MachineHour;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@Document
@Getter
@Setter
public class MachineDaily {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private float powerAVG;
    private float powerIdle;
    private float powerPeak;

    private List<Integer> workedOn;

    private int idleTime;
    private int workingTime;
    private int errorTime;


    private double actualTime;
    private int timePerPart;

    private long startTime;
    private TreeMap<TimeRange, MachineStatus> timeLine;

    public MachineDaily(List<MachineHour> machineHourList){
        //processHourMachines(machineHourList);


    }


        private void processHourMachines(List<MachineHour> machineHourList){


            for(int i =0;i<machineHourList.size();i++){

                MachineHour machineHour = machineHourList.get(i);
                addTimeLine(machineHour.getTimeLine());
                addWorkedOn(machineHour.getWorkedOn());

                this.idleTime += machineHour.getIdleTime();
                this.workingTime +=machineHour.getWorkingTime();
                this.errorTime +=machineHour.getErrorTime();
                this.actualTime +=machineHour.getActualTime();
                this.powerAVG +=machineHour.getPower();



                if(i==0){
                    this.powerPeak = machineHour.getPowerPeak();
                    this.powerIdle = machineHour.getPowerIdle();
                }else{

                }




            }

            this.timePerPart = (int)(workedOn.size()/ actualTime);



        }

        private void addWorkedOn(List<Integer> ids){
        for(Integer i :ids){
            if(!workedOn.contains(i)){
                workedOn.add(i);
            }
        }

        }

        private void addTimeLine(TreeMap<TimeRange, MachineStatus> hl){
            for(Map.Entry<TimeRange, MachineStatus> tl : hl.entrySet() ){
                this.timeLine.put(tl.getKey(),tl.getValue());
            }

        }
        private void processTimeLine(){
        // find overlapping
        }

}
