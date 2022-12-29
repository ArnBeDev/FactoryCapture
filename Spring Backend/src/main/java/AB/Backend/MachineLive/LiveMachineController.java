package AB.Backend.MachineLive;

import AB.Backend.MachineLive.LiveMachine;
import AB.Backend.MachineLive.MachineStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/live")
public class LiveMachineController {

    private final MachineStateService machineStateService;
    @Autowired
    public LiveMachineController(MachineStateService machineStateService){
        this.machineStateService = machineStateService;
    }




    @GetMapping(path ="/")
    public List<LiveMachine> getLiveData(@RequestParam(required = false) String id){



        System.out.println("GET LIVE DATA");

        if(id ==null){
            System.out.println("ALLES");
            return machineStateService.getLiveData();
        }else {
            try{
                int i = Integer.parseInt(id);
                return machineStateService.getLiveDataById(i);
            }catch(Exception e){
                return machineStateService.getLiveData();
            }

        }


    }
    @GetMapping(path="/api/live")
    public String allData(){
        System.out.println("ALLL");
        return "Hallo";
    }


}



