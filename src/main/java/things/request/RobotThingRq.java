package things.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import things.AbstractThingClass;

@JsonAutoDetect
public class RobotThingRq extends AbstractThingClass {
    private volatile double m11;
    private volatile double l11;

    public RobotThingRq() {
    }

    public void setM11(double m11) {
        this.m11 = m11;
    }

    public void setL11(double l11) {
        this.l11 = l11;
    }

    public synchronized double getM11() {
        return m11;
    }

    public synchronized double getL11() {
        return l11;
    }

    public synchronized void addCountBtn1(){
        m11++;
    }

    public synchronized void addCountBtn2(){
        l11++;
    }

    @Override
    public String toString() {
        return "ThingOneRq{" +
                "m11=" + m11 +
                ", l11=" + l11 +
                '}';
    }
}
