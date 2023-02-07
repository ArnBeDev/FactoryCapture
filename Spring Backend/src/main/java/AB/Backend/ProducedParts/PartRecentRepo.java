package AB.Backend.ProducedParts;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartRecentRepo {


    List<Part> recentParts;


    public List<Part> getRecentPart(){
        return recentParts;
    }




}
