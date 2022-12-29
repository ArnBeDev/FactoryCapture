package AB.Backend.FactoryStructure;

import AB.Backend.MachineLive.LiveMachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FactoryConstructionConfig {

    //Constructs Machines and Units
    @Autowired
    LiveMachineRepo liveMachineRepo;
@Bean
    CommandLineRunner cmd(){return args->{




        FactoryLine line1= new FactoryLine();
        FactoryPart p1= new FactoryPart();
        Machine m1 = new Machine(1);
        Machine m2 = new Machine(2);
        Machine m3 = new Machine(3);
        Machine m4 = new Machine(4);
        Machine m5 = new Machine(5);
        Machine m6 = new Machine(6);
        AssemblyLine al1 = new AssemblyLine(7);

        p1.setAl(al1);

        line1.setParts(List.of(p1));
        Unit u1= new Unit();
        u1.setConsist(List.of(m1,m2));

        Unit u2 = new Unit();
        u2.setConsist(List.of(m3));
        Unit u3 = new Unit();
        u3.setConsist(List.of(m4));

        Unit u4 = new Unit();
        u4.setConsist(List.of(m5,m6));

    p1.setUnits(List.of(u1,u2,u3,u4));


        m1.setParallelWith(2);
        m2.setParallelWith(1);

        m5.setParallelWith(6);
        m6.setParallelWith(5);


        Unit l1u1 = new Unit();
        l1u1.setHas(List.of(1,2));

        Unit l1u2 = new Unit();
        l1u2.setHas(List.of(3));
        Unit l1u3 = new Unit();
        l1u3.setHas(List.of(4));
        Unit l1u4 = new Unit();
        l1u4.setHas(List.of(5,6));
        Unit l1u5 = new Unit();

        Unit l1u6 = new Unit();





    };
    }

}
