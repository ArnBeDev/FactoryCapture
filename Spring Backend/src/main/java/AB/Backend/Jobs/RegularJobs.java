package AB.Backend.Jobs;

import AB.Backend.DailyMachines.MachineDaily;
import AB.Backend.DailyMachines.MachineDailyRepo;
import AB.Backend.DailyMachines.MachineDailyService;
import AB.Backend.HourMachine.MachineHour;
import AB.Backend.HourMachine.MachineHourRepo;
import AB.Backend.HourMachine.MachineHourService;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.MachineStateService;
import AB.Backend.TenMinutesMachine.MachineTenMinService;
import AB.Backend.TenMinutesMachine.MachineTenMinuteRepo;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import AB.Backend.MachineLive.MachineStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@EnableScheduling
@Configuration
public class RegularJobs {

    private final MachineStateRepository stateRepo;
    private final MachineTenMinuteRepo tenMinuteRepo;
    private final MachineHourRepo hourRepo;
    private final MachineDailyRepo dailyRepo;

    @Autowired
    public RegularJobs(MachineStateRepository stateRepo, MachineTenMinuteRepo tenMinuteRepo,
                       MachineHourRepo hourRepo, MachineDailyRepo dailyRepo)
    {
        this.stateRepo = stateRepo;
        this.tenMinuteRepo = tenMinuteRepo;
        this.hourRepo = hourRepo;
        this.dailyRepo = dailyRepo;

    }


    //EVERY 10 min , e.g: 22:30:30
    // only between 6 and 23 o clock

    @Scheduled(cron ="15 */10 6-23 * * ?")
    public void tenMinuteJob(){
        long startTime =0;
        long endTime =0;




        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);


        int endMinute = calendar.get(Calendar.MINUTE);
        int startMinute;
        if(endMinute == 0){
            startMinute =50;
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),calendar.get(Calendar.HOUR_OF_DAY)-1,startMinute,0);

        }else{
            startMinute = endMinute-10;
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),calendar.get(Calendar.HOUR_OF_DAY),startMinute,0);

        }

        System.out.println("starTime min:"+ calendar.get(Calendar.MINUTE) +" sek:"+calendar.get(Calendar.SECOND));

        startTime =calendar.getTimeInMillis();

        System.out.println("Calendar Year:" +calendar.YEAR);
        System.out.println("startTime after get milis"+ startTime);
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),calendar.get(Calendar.HOUR_OF_DAY),endMinute-1,59);
        System.out.println("endTime:min: " +calendar.get(Calendar.MINUTE)  +" sec:"+calendar.get(Calendar.SECOND));
        System.out.println("startTime after set"+ startTime);



        endTime =calendar.getTimeInMillis();
        processTenMinuteJob(startTime,endTime);

        // Delete older states

        System.out.println("TEN MINUTE JOB +"+ startTime  +" ende:" +endTime +"   dif -" +(endTime-startTime));




    }

    @Scheduled(cron =" 45 0 * * * ?")
    public void hourlyJob(){
        System.out.println("HOURLY JOB");
    }

    //um 00:01
    @Scheduled(cron = "0 1 0 * * *")
    public void dailyJob(){
        System.out.println("DAILY JoB");

    }


    public void processTenMinuteJob(long startTime, long endTime){
        List<MachineState> machineStatesList=  stateRepo.findAllByTimestampBetween(startTime,endTime);
        System.out.println("Size of found states: " +machineStatesList.size());



            while(machineStatesList.size()>0){

                int id = machineStatesList.get(0).getMachineId();

                MachineTenMinutes machineTenMinutes= new MachineTenMinutes(id,startTime);
                List<MachineState> machineStates = new ArrayList<>();

                for(int j = 0;j<machineStatesList.size();j++){
                    if(machineStatesList.get(j).getMachineId() == id){

                        machineStates.add(machineStatesList.get(j));

                        machineStatesList.remove(j);
                        j--;

                    }

                        machineTenMinutes.addMachineStates(machineStates);


                    tenMinuteRepo.save(machineTenMinutes);

                }




            }






    }

    private void processHourlyJob(long starTime,long endTime){
            List<MachineTenMinutes> allMachineTenMinutes = tenMinuteRepo.findAllByStartTimeBetween(starTime,endTime);

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
                hourRepo.save(machineHour);

            }
    }


    private long unixFromDate(Date d){

        return d.getTime();
    }




}
