package AB.Backend.ProducedParts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(path="/api/parts")
public class PartController {


    private final PartService partService;
    public PartController(PartService partService){
        this.partService = partService;
    }




    public List<Part> recentlyProduced(){
        return partService.getRecentlyProducedParts();
    }


}
