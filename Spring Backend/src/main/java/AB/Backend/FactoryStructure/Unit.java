package AB.Backend.FactoryStructure;

import lombok.Setter;

import java.util.List;

@Setter
public class Unit {

    private List<Machine> consist;
    private List<Integer> has;

    private Unit nextUnit;
    private FactoryPart next;




}
