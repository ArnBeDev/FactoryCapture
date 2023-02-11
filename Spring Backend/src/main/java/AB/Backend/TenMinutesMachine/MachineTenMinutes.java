package AB.Backend.TenMinutesMachine;

import AB.Backend.FactoryStructure.Machine;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

@Getter
@Setter
@Document
public class MachineTenMinutes implements Comparable<MachineTenMinutes>{

    private int id;
    private int machineId;
    private double startTime;
    private float power;
    private int workingTime;
    private float workingPower;
    private int idleTime;
    private float idlePower;
    private int errorTime;

    private float powerPeak;
    private float powerLow;

    private double actualTime;
    private List<Byte> stateCodes;
    private List<Integer> workedOn;

    private TreeMap<TimeRange, MachineStatus> timeLine;

    private void addState(byte state) {
        if (stateCodes.contains(state)) {
            return;
        } else {
            stateCodes.add(state);
        }
    }

    private void addWorkedOn(int i){
            if(workedOn.contains(i)){
                return;
            }else{
                workedOn.add(i);
            }


        }


        private void addPower(float p){
           power+=p;
        }



        public MachineTenMinutes(int id,double startTime){

        }


        public MachineTenMinutes(){

        }


        public void addMachineStates(List<MachineState> ms){
            Collections.sort(ms);
            init();

            int currentState=0;
            int currentWorkOn=-1;
            long startTime=0;
            int powerCounter=0;

            for(int i =0;i<ms.size();i++){
                MachineState machine = ms.get(i);
                addPower(machine.getPower());
                addWorkedOn(machine.getWorkingOn());
                addState(machine.getStateCode());
                powerCounter++;
                if(i>0){

                    if(currentState !=machine.getStateCode() || i ==ms.size()){
                        timeLine.put(new TimeRange(startTime,(long)machine.getTimestamp()),new MachineStatus(currentState,currentWorkOn));
                        currentState =machine.getStateCode();
                        startTime=(long)machine.getTimestamp();
                        currentWorkOn = machine.getWorkingOn();
                    }

                    if(machine.getStateCode() ==Machine.WORK_CODE){
                            this.workingPower +=machine.getPower();

                            if(ms.get(i-1).getStateCode() == Machine.WORK_CODE ){
                                this.workingTime += machine.getTimestamp() -ms.get(i-1).getTimestamp();
                            }

                    }else if(machine.getStateCode() == Machine.IDLE_CODE){
                            idlePower +=machine.getPower();

                            if(ms.get(i-1).getStateCode() == Machine.IDLE_CODE){
                                this.idleTime += machine.getTimestamp() -ms.get(i-1).getTimestamp();
                            }

                    }else if(machine.getStateCode() >2 && ms.get(i-1).getStateCode()> 2){
                                errorTime +=machine.getTimestamp() -ms.get(i-1).getTimestamp();

                    }

                    actualTime +=machine.getTimestamp()-ms.get(i-1).getTimestamp();
                }else{
                    startTime = (long)machine.getTimestamp();
                    currentState = machine.getStateCode();
                    if(currentState ==2){
                        currentWorkOn =machine.getWorkingOn();

                    }


                    powerPeak = machine.getPower();
                    powerLow = machine.getPower();
                }

                if(powerPeak < machine.getPower()){
                    powerPeak = machine.getPower();
                }
                if(powerLow > machine.getPower()){
                    powerLow =machine.getPower();
                }


            }

        this.power = this.power /(float)actualTime;
        this.idlePower = this.idlePower/this.idleTime;
        this.workingPower = this.workingPower/this.workingTime;



        }


    public MachineTenMinutes(int id, double startTime, float power, int workingTime, int idleTime, int errorTime, List<Byte> stateCodes, List<Integer> workedOn) {
        this.id = id;
        this.startTime = startTime;
        this.power = power;
        this.workingTime = workingTime;
        this.idleTime = idleTime;
        this.errorTime = errorTime;
        this.stateCodes = stateCodes;
        this.workedOn = workedOn;
    }







    public MachineTenMinutes getCopy(){
            return new MachineTenMinutes(
                    this.id,this.startTime,this.power,this.workingTime,this.idleTime,
                    this.errorTime,this.stateCodes,this.workedOn
            );




        }

    @Override
    public int compareTo(MachineTenMinutes ms) {
        return this.startTime < ms.getStartTime() ? -1 : (this.getStartTime() == ms.getStartTime() ? 0 : 1);
    }

    private void init(){
        timeLine = new TreeMap<TimeRange,MachineStatus>();
        stateCodes = new ArrayList<Byte>();
        workedOn = new ArrayList<Integer>();

    }

}
