package AB.Backend.Models;

import lombok.Getter;

import lombok.Setter;

public class ProductCycle {

    private TimeRange timeRange;

    @Getter
    @Setter
    private int line;


    public ProductCycle(){
timeRange = new TimeRange();
    }


    public ProductCycle(int line){
        this.line =line;
        timeRange = new TimeRange();
    }

    public ProductCycle(int line, long firstSeen){
        this.line = line;
        timeRange = new TimeRange();
        timeRange.setStartTime(firstSeen);
    }
    public ProductCycle(int line, long firstSeen,long lastSeen){
        this.line = line;
        timeRange = new TimeRange();
        timeRange.setStartTime(firstSeen);
        timeRange.setEndTime(lastSeen);
    }

    public void setLastSeen(long l){
        timeRange.setEndTime(l);
    }


    public void setFirstSeen(long l){
        timeRange.setStartTime(l);
    }


    public long getLastSeen(){
        return timeRange.getEndTime();
    }

    public long getFirstSeen(){
        return timeRange.getStartTime();
    }


}
