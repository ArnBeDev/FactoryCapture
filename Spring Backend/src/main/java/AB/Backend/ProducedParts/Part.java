package AB.Backend.ProducedParts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document
public class Part {

@Id
    private Integer id;
    @Field
    private long productionStart;
    @Field
    private long productionEnd;

@Field
    private int line;


@Transient
private List<Integer> machines;
    public Part(){
        this.id =null;

    }

    public Part(Integer id, long productionStart, long productionEnd,int line){
        this.id=id;
        this.productionStart=productionStart;
        this.productionEnd=productionEnd;
        this.line=line;
    }
}
