package AB.Backend.MachineLive;

import AB.Backend.MachineLive.LiveMachine;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Setter
public class LiveMachineRepo {


    List<LiveMachine> machines;

    public List<LiveMachine> getLiveMachineData(){
            return machines;

    }

    public void updateMachine(int id,float power,long timeStamp,byte stateCode,int workingOn){
        for (LiveMachine machine: machines
             ) {

            if(id == machine.getId()){
                machine.update(id,power,stateCode,workingOn,timeStamp);
                return;

            }
        }

        LiveMachine lm = new LiveMachine(id,power,stateCode,workingOn,timeStamp);
        machines.add(lm);

    }


    public List<LiveMachine >getLiveMachineById(int id){
        for (LiveMachine m : machines
             ) {
            if(m.getId() == id){
                return List.of(m);
            }
        }
        return machines;
    }

    public LiveMachineRepo(){
        this.machines= new ArrayList<LiveMachine>();
    }
}
