package AB.Backend.ProducedParts;

import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.ProductCycle;
import AB.Backend.Models.TimeRange;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Repository
public class PartRecentRepo {


    List<Part> recentParts;
    List<MachineState> recentMachines;
    TreeMap<Integer, ProductCycle> inProduction;

    public PartRecentRepo(){
        inProduction = new TreeMap<Integer, ProductCycle>();
        recentParts = new ArrayList<Part>();
    }

    public List<Part> getRecentParts(){
        return recentParts;
    }

    public TreeMap<Integer,ProductCycle> getInProduction(){
        return inProduction;
    }

}
