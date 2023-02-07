package AB.Backend.ProducedParts;

import AB.Backend.MachineLive.MachineState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PartService {



    private final PartRepo partRepo;
    private final PartRecentRepo recentRepo;

    private Set<Integer> inProduction;

@Autowired
    public PartService(PartRepo partRepo,PartRecentRepo partRecentRepo){
        this.partRepo=partRepo;
        this.recentRepo = partRecentRepo;
    }



    public List<Part> getRecentlyProducedParts(){
       return recentRepo.getRecentPart();

    }



    public void passCurrentProduction(List<MachineState> states){
        List<Part> recent = recentRepo.getRecentPart();

        for (MachineState s: states
             ) {


            inProduction.add(s.getWorkingOn());


        }

        for (Integer i: inProduction
             ) {
            if()
        }



    }











}
