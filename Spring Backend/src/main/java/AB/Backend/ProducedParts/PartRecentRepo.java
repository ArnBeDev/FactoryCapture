package AB.Backend.ProducedParts;

import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.ProductCycle;
import AB.Backend.Models.TimeRange;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.TreeMap;

@Repository
public class PartRecentRepo {


    List<Part> recentParts;
    List<MachineState> recentMachines;
TreeMap<Integer, ProductCycle> inProduction;
    public List<Part> getRecentPart(){
        return recentParts;
    }




}
