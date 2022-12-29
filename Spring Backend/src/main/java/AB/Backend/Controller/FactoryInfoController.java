package AB.Backend.Controller;

import AB.Backend.FactoryStructure.Unit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path ="api/factory/")
public class FactoryInfoController {


    @GetMapping(path= "{id}")
    public List<Unit> getUnits(@PathVariable String id){

        return List.of(new Unit());


    }


}
