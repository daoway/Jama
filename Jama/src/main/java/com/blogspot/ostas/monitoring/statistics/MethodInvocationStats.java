package com.blogspot.ostas.monitoring.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;

public class MethodInvocationStats implements Serializable {
    private AtomicLong hits;
    private AtomicLong latestExecTime;
    private AtomicLong totalExecTime;
    private AtomicLong minExecTime;
    private AtomicLong maxExecTime;

    public MethodInvocationStats() {
        hits = new AtomicLong(0);
        totalExecTime = new AtomicLong(0);
        latestExecTime = new AtomicLong(0);
        minExecTime = new AtomicLong(0);
        maxExecTime = new AtomicLong(0);
    }

    public AtomicLong getHits() {
        return hits;
    }

    public void setHits(AtomicLong hits) {
        this.hits = hits;
    }

    public AtomicLong getLatestExecTime() {
        return latestExecTime;
    }

    public void setLatestExecTime(AtomicLong latestExecTime) {
        this.latestExecTime = latestExecTime;
    }

    public AtomicLong getTotalExecTime() {
        return totalExecTime;
    }

    public void setTotalExecTime(AtomicLong totalExecTime) {
        this.totalExecTime = totalExecTime;
    }

    public AtomicLong getMinExecTime() {
        return minExecTime;
    }

    public void setMinExecTime(AtomicLong minExecTime) {
        this.minExecTime = minExecTime;
    }

    public AtomicLong getMaxExecTime() {
        return maxExecTime;
    }

    public void setMaxExecTime(AtomicLong maxExecTime) {
        this.maxExecTime = maxExecTime;
    }

    public Double getAvg(){
        double d = 0;
        if(totalExecTime!=null && hits !=null && hits.longValue()!=-1){
            d = (double) totalExecTime.longValue()/ hits.longValue();
        }
        return new BigDecimal(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        if(hits !=null && hits.longValue()==-1) return null;
        final StringBuilder sb = new StringBuilder();
        sb.append("hits=").append(hits);
        sb.append(", last=").append(latestExecTime);
        sb.append(", total=").append(totalExecTime);
        sb.append(", min=").append(minExecTime);
        sb.append(", max=").append(maxExecTime);
        sb.append(", avg=").append(getAvg());
        return sb.toString();
    }
}
