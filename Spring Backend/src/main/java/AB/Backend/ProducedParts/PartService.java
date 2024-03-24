package AB.Backend.ProducedParts;

import AB.Backend.MachineLive.MachineState;
import AB.Backend.Models.ProductCycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartService {

    private final PartRepo partRepo;
    private final PartRecentRepo recentRepo;
    private int sizeOfRecentParts;

    @Autowired
    public PartService(PartRecentRepo partRecentRepo, PartRepo partRepo) {
        this.partRepo = partRepo;
        this.recentRepo = partRecentRepo;
        sizeOfRecentParts = 15;
    }

    public List<Part> getRecentlyProducedParts() {
        return recentRepo.getRecentParts();
    }

    public void passNewMachineStates(List<MachineState> machineStates) {
        List<Part> recentParts = recentRepo.getRecentParts();
        TreeMap<Integer, ProductCycle> inProduction = recentRepo.getInProduction();

        // if part not in inProdution -> add
        // else -> update lastSeen
        

        for (MachineState s : machineStates
        ) {

            if (s.getWorkingOn() > 0 && !inProduction.containsKey(s.getWorkingOn())) {

                int line;
                if (s.getMachineId() < 15) {
                    line = 1;
                } else {
                    line = 2;
                }
                inProduction.put(s.getWorkingOn(), new ProductCycle(line, s.getTimestamp(), s.getTimestamp()));

            } else {
                if (inProduction.get(s.getWorkingOn()) != null) {
                    inProduction.get(s.getWorkingOn()).setLastSeen(s.getTimestamp());
                }
            }

        }

        // if inProdction contains part that is not in states anymore -> create part and remove
        boolean found;

        // temp storage for ids that should be removed from inProduction
        List<Integer> removeIds = new ArrayList<>();

        for (Map.Entry<Integer, ProductCycle> entry : inProduction.entrySet()) {

            found = false;
            for (MachineState s : machineStates) {
                if (entry.getKey().equals(s.getWorkingOn())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Part p = new Part();
                p.setId(entry.getKey());
                p.setLine(entry.getValue().getLine());
                p.setProductionStart(entry.getValue().getFirstSeen());
                p.setProductionEnd((entry.getValue().getLastSeen()));

                // inProduction.remove(entry.getKey());

                // Treemap cant handle remove while iterating
                removeIds.add(entry.getKey());


                //TODO connect bucket
                partRepo.save(p);

                recentParts.add(p);
                while (recentParts.size() > sizeOfRecentParts) {
                    recentParts.remove(0);
                }
            }
        }

        for (Integer i : removeIds) {
            inProduction.remove(i);
        }
    }

    public void testSaveInBucket(Part p) {
        partRepo.save(p);
    }
}
