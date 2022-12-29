package AB.Backend.Jobs;

import AB.Backend.HourMachine.MachineHour;
import AB.Backend.HourMachine.MachineHourRepo;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.TenMinutesMachine.MachineTenMinuteRepo;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import AB.Backend.MachineLive.MachineStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegularJobs {


    private final MachineStateRepository mStateRepo;
    private final MachineTenMinuteRepo mTenRepo;
    private final MachineHourRepo mHourRepo;

    @Autowired
    public RegularJobs(MachineStateRepository m,MachineTenMinuteRepo mtenRepo,MachineHourRepo mHourRepo)
    {   this.mTenRepo =mtenRepo;
        this.mStateRepo = m;
        this.mHourRepo = mHourRepo;
    }
    @Scheduled()
    public void tenMinuteJob(){
        double startTime=1100d;
        double endTime =500d;

        processMachineTenMinute(startTime,endTime);





    }

    @Transactional
    public void processMachineTenMinute(double startTime, double endTime){
        List<MachineState> machineStatesList=  mStateRepo.findBetweenTime(startTime,endTime);

        for(int i = 0; i<machineStatesList.size();i++){
            int id = machineStatesList.get(i).getMachineId();

            MachineTenMinutes machineTenMinutesn= new MachineTenMinutes(id,startTime);
            List<MachineState> machineStates = new ArrayList<>();
            for(int j = i;j<machineStatesList.size();j++){
                if(machineStatesList.get(j).getMachineId() == id){

                    machineStates.add(machineStatesList.get(j));

                    machineStatesList.remove(j);
                    j--;

                }
                if(j ==machineStatesList.size()){
                    machineTenMinutesn.addMachineStates(machineStates);
                }

                mTenRepo.save(machineTenMinutesn);
                machineStatesList.remove(i);
                i--;

            }

        }




    }


    public void HourlyJob(){


    }

    private void processHourlyJob(double starTime,double endTime){
            List<MachineTenMinutes> allMachineTenMinutes = mTenRepo.findBetweenTime(starTime,endTime);

            for(int i =0; i<allMachineTenMinutes.size();i++){

                int id = allMachineTenMinutes.get(i).getMachineId();
                List<MachineTenMinutes> tenMinutesList = new ArrayList<MachineTenMinutes>();
                tenMinutesList.add(allMachineTenMinutes.get(i));


                for(int j =i+1;j<allMachineTenMinutes.size();j++ ){
                    if(allMachineTenMinutes.get(j).getMachineId() == id){
                        tenMinutesList.add(allMachineTenMinutes.get(j));
                        allMachineTenMinutes.remove(j);
                        j--;
                    }



                }
                allMachineTenMinutes.remove(i);
                i--;
                MachineHour machineHour = new MachineHour(tenMinutesList);
                mHourRepo.save(machineHour);

            }
    }


    private long unixFromDate(Date d){

        return d.getTime();
    }


}
