package AB.Backend.Models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class TimeRange implements  Comparable<TimeRange>{

    long startTime;
    long endTime;

    public TimeRange(long startTime,long endTime){
        this.startTime=startTime;
        this.endTime= endTime;
    }




    @Override
    public int compareTo(TimeRange o) {
        if(this.startTime< o.getStartTime()){
            return -1;
        }else if(this.startTime > o.getStartTime()){
            return 1;
        }


        return 0;
    }
}
