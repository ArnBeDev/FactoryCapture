package AB.Backend.DailyMachines;

import AB.Backend.FactoryStructure.Machine;
import AB.Backend.HourMachine.MachineHour;
import AB.Backend.Models.MachineStatus;
import AB.Backend.Models.TimeRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
@NoArgsConstructor
public class MachineDaily {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private int machineId;
    private float powerAVG;
    private float powerLow;
    private float powerPeak;

    private List<Integer> workedOn;

    private int idleTime;
    private int workingTime;
    private int errorTime;


    private double actualTime;
    private int timePerPart;

    private long startTime;

    @Transient
    private TreeMap<TimeRange, MachineStatus> timeLine;

    private long[] timeRanges;
    private int[] machineStates;

    public MachineDaily(List<MachineHour> machineHourList) {
        processHourMachines(machineHourList);
    }

    private void processHourMachines(List<MachineHour> machineHourList) {


        for (int i = 0; i < machineHourList.size(); i++) {

            MachineHour machineHour = machineHourList.get(i);
            addTimeLine(machineHour.getTimeLine());
            addWorkedOn(machineHour.getWorkedOn());

            this.idleTime += machineHour.getIdleTime();
            this.workingTime += machineHour.getWorkingTime();
            this.errorTime += machineHour.getErrorTime();
            this.actualTime += machineHour.getActualTime();
            this.powerAVG += machineHour.getPower();


            if (i == 0) {
                this.powerPeak = machineHour.getPowerPeak();
                this.powerLow = machineHour.getPowerLow();
            } else {
                if (this.powerPeak < machineHour.getPowerPeak()) {
                    this.powerPeak = machineHour.getPowerPeak();
                }
                if (this.powerLow > machineHour.getPowerLow()) {
                    this.powerLow = machineHour.getPowerLow();
                }


            }


        }

        this.timePerPart = (int) (workedOn.size() / actualTime);


    }

    private void addWorkedOn(List<Integer> ids) {
        for (Integer i : ids) {
            if (!workedOn.contains(i)) {
                workedOn.add(i);
            }
        }

    }

    private void addTimeLine(TreeMap<TimeRange, MachineStatus> hl) {
        for (Map.Entry<TimeRange, MachineStatus> tl : hl.entrySet()) {
            this.timeLine.put(tl.getKey(), tl.getValue());
        }

    }

    private void processTimeLine() {
        // find overlapping
    }


    public TreeMap<TimeRange, MachineStatus> getTimeLine() {
        if (timeLine == null) {
            return Machine.getTimeRangeMachineStatusTreeMap(this.timeRanges, this.machineStates);
        }
        return timeLine;
    }


    private void init() {
        this.timeLine = new TreeMap<>();
        this.idleTime = 0;
        this.workingTime = 0;
        this.errorTime = 0;
        this.actualTime = 0;
    }
}
