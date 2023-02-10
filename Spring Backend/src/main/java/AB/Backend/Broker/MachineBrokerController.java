package AB.Backend.Broker;

import AB.Backend.DailyMachines.MachineDaily;
import AB.Backend.DailyMachines.MachineDailyService;
import AB.Backend.HourMachine.MachineHour;
import AB.Backend.HourMachine.MachineHourService;
import AB.Backend.MachineLive.MachineState;
import AB.Backend.MachineLive.MachineStateService;
import AB.Backend.ProducedParts.Part;
import AB.Backend.ProducedParts.PartService;

import AB.Backend.TenMinutesMachine.MachineTenMinService;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sendmachine/")
public class MachineBrokerController
{

    private boolean debug;
    private final MachineStateService machineStateService;
    private final PartService partService;

    private final MachineTenMinService tenMinService; //only for debugging
    private final MachineHourService machineHourService;//only for debugging
    private final MachineDailyService machineDailyService;//only for debugging

    @Autowired
    public MachineBrokerController(MachineStateService machineStateService, PartService partService,
                                   MachineTenMinService mtService,MachineHourService mhService, MachineDailyService mdService){
    debug = true;
    this.machineStateService=machineStateService;
    this.partService = partService;
    this.tenMinService = mtService;
    this.machineHourService = mhService;
    this.machineDailyService = mdService;
    }

    //http://localhost:8080/sendmachine/1?s=2&p=85.43&w=13&t=240001.444
   // @PutMapping(path ="{id}")
    public void receiveData(@PathVariable int id,
                            @RequestParam byte s,
                            @RequestParam float p,
                            @RequestParam int w,
                            @RequestParam long t
                            ){
        System.out.println("PUT RECEIVED");
      //  machineStateService.addMachineState(id,2d,3f,(byte)4,5);
        machineStateService.addMachineState(id,t,p,s,w);



    }

    @PutMapping
    public void receiveStates(@RequestBody List<MachineState> machineStateList){
       // System.out.println("received machinedata, machines: " +machineStateList.size());

       // System.out.println(machineStateList.toString());
        machineStateService.addMachineStates(machineStateList);
        partService.passNewMachineStates(machineStateList);

        if(debug){
            testBuckets();
            debug = false;
        }

    }

    void testBuckets(){
        Part p = new Part(99999999,5,10,1);
        partService.testSaveInBucket(p);

        List<Byte> states = new ArrayList<Byte>();
        byte b1 =1;
        byte b2 = 3;

    //        states.add(b1,b2);
        MachineTenMinutes m10 = new MachineTenMinutes(99,44,555,
                33,155,5,List.of((byte)4,(byte)3),List.of(9999,19999));

  tenMinService.testSaveTenMinutes(m10);

        MachineHour mHour = new MachineHour(List.of(m10));
        mHour.setMachineId(101);
        machineHourService.testSaveHourMachine(mHour);

        MachineDaily mdaily = new MachineDaily(List.of(mHour));
        mdaily.setMachineId(222);
        machineDailyService.testSaveInBucket(mdaily);

    }


}
