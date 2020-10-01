package things.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import things.AbstractThingClass;

@JsonAutoDetect
public class ThingOneRq extends AbstractThingClass {
    private volatile double countBut1;
    private volatile double countBut2;

    public ThingOneRq() {
    }

    public void setCountBut1(double countBut1) {
        this.countBut1 = countBut1;
    }

    public void setCountBut2(double countBut2) {
        this.countBut2 = countBut2;
    }

    public synchronized double getCountBut1() {
        return countBut1;
    }

    public synchronized double getCountBut2() {
        return countBut2;
    }

    public synchronized void addCountBtn1(){
        countBut1++;
    }

    public synchronized void addCountBtn2(){
        countBut2++;
    }

    @Override
    public String toString() {
        return "ThingOneRq{" +
                "countBut1=" + countBut1 +
                ", countBut2=" + countBut2 +
                '}';
    }
}
