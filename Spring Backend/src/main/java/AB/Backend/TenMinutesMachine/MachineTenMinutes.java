package AB.Backend.TenMinutesMachine;

import AB.Backend.FactoryStructure.Machine;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.*;

import static AB.Backend.FactoryStructure.Machine.getTimeRangeMachineStatusTreeMap;


@Data
@Document
@NoArgsConstructor
public class MachineTenMinutes implements Comparable<MachineTenMinutes> {


    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private int machineId;
    private long startTime;
    private float power;
    private int workingTime;
    private float workingPower;
    private int idleTime;
    private float idlePower;
    private int errorTime;

    private float powerPeak;
    private float powerLow;

    private double actualTime;

    @Transient
    private List<Byte> stateCodes;

    @Transient
    private List<Integer> workedOn;


    @Getter(AccessLevel.NONE)
    @Transient
    private TreeMap<TimeRange, MachineStatus> timeLine;

    // timeLine as Arrays for better database integration
    private long[] timeRange;// [i] = startTime,  [i+1] =endTime
    private int[] machineStates; // [i] = stateCode,  [i+1] =workOn

    private void addState(byte state) {
        if (stateCodes.contains(state)) {
            return;
        } else {
            stateCodes.add(state);
        }
    }

    private void addWorkedOn(int i) {
        if (workedOn.contains(i)) {
            return;
        } else {
            workedOn.add(i);
        }
    }

    private void addPower(float p) {
        this.power += p;
    }

    public MachineTenMinutes(int id, long startTime) {
        this.machineId = id;
        this.startTime = startTime;
    }

    public void addMachineStates(List<MachineState> machineStateList) {
        Collections.sort(machineStateList);
        init();

        int currentState = 0;
        int currentWorkOn = -1;
        long startTime = 0;
        int stateCounter = 0;

        for (int i = 0; i < machineStateList.size(); i++) {
            MachineState machine = machineStateList.get(i);
            addPower(machine.getPower());
            addWorkedOn(machine.getWorkingOn());
            addState(machine.getStateCode());
            stateCounter++; // counts seconds=states
            if (i > 0) {

                if (currentState != machine.getStateCode() || i == machineStateList.size()) {
                    // if statecode change -> update timeline
                    timeLine.put(new TimeRange(startTime, machine.getTimestamp()), new MachineStatus(currentState, currentWorkOn));
                    currentState = machine.getStateCode();
                    startTime = machine.getTimestamp();
                    currentWorkOn = machine.getWorkingOn();
                }

                if (machine.getStateCode() == Machine.WORK_CODE) {
                    this.workingPower += machine.getPower();

                    if (machineStateList.get(i - 1).getStateCode() == Machine.WORK_CODE) {
                        this.workingTime += machine.getTimestamp() - machineStateList.get(i - 1).getTimestamp();
                    }

                } else if (machine.getStateCode() == Machine.IDLE_CODE) {
                    idlePower += machine.getPower();

                    if (machineStateList.get(i - 1).getStateCode() == Machine.IDLE_CODE) {
                        this.idleTime += machine.getTimestamp() - machineStateList.get(i - 1).getTimestamp();
                    }

                } else if (machine.getStateCode() > 2 && machineStateList.get(i - 1).getStateCode() > 2) {
                    errorTime += machine.getTimestamp() - machineStateList.get(i - 1).getTimestamp();

                }

                actualTime += machine.getTimestamp() - machineStateList.get(i - 1).getTimestamp();
            } else {
                startTime = (long) machine.getTimestamp();
                currentState = machine.getStateCode();
                if (currentState == 2) {
                    currentWorkOn = machine.getWorkingOn();

                }


                powerPeak = machine.getPower();
                powerLow = machine.getPower();
            }

            if (powerPeak < machine.getPower()) {
                powerPeak = machine.getPower();
            }
            if (powerLow > machine.getPower()) {
                powerLow = machine.getPower();
            }


        }


        this.power = this.power / (float) stateCounter;
        this.idlePower = this.idlePower / (this.idleTime / 1000f);
        this.workingPower = this.workingPower / (this.workingTime / 1000f);

        this.setTimeLineAsArrays();

    }


    public MachineTenMinutes(int machineId, long startTime, float power, int workingTime, int idleTime, int errorTime, List<Byte> stateCodes, List<Integer> workedOn) {
        this.machineId = machineId;
        this.startTime = startTime;
        this.power = power;
        this.workingTime = workingTime;
        this.idleTime = idleTime;
        this.errorTime = errorTime;
        this.stateCodes = stateCodes;
        this.workedOn = workedOn;
    }


    public MachineTenMinutes getCopy() {
        return new MachineTenMinutes(
                this.machineId, this.startTime, this.power, this.workingTime, this.idleTime,
                this.errorTime, this.stateCodes, this.workedOn
        );

    }

    @Override
    public int compareTo(MachineTenMinutes ms) {
        return this.startTime < ms.getStartTime() ? -1 : (this.getStartTime() == ms.getStartTime() ? 0 : 1);
    }

    private void init() {
        timeLine = new TreeMap<TimeRange, MachineStatus>();
        stateCodes = new ArrayList<Byte>();
        workedOn = new ArrayList<Integer>();
    }

    public TreeMap<TimeRange, MachineStatus> getTimeLine() {
        return getTimeRangeMachineStatusTreeMap(timeRange, machineStates);
    }

    private void setTimeLineAsArrays() {
        this.timeRange = new long[timeLine.size()];
        this.machineStates = new int[timeRange.length];
        int index = 0;

        for (Map.Entry<TimeRange, MachineStatus> entry : timeLine.entrySet()) {
            timeRange[index] = entry.getKey().getStartTime();
            timeRange[index + 1] = entry.getKey().getEndTime();
            machineStates[index] = entry.getValue().getStateCode();
            machineStates[index + 1] = entry.getValue().getWorkOn();
            index++;
            index++;
        }
    }
}