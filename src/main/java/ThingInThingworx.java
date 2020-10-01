import com.fasterxml.jackson.annotation.JsonAutoDetect;
import things.AbstractThingClass;

@JsonAutoDetect
public class ThingInThingworx extends AbstractThingClass {
    private double OneParam;
    private double lamp2;
    private double lamp1;

    public ThingInThingworx(double lamp1) {
        this.lamp1 = lamp1;
    }

    public double getOneParam() {
        return OneParam;
    }

    public double getLamp2() {
        return lamp2;
    }

    public double getLamp1() {
        return lamp1;
    }

    public void setOneParam(double oneParam) {
        OneParam = oneParam;
    }

    public void setLamp2(double lamp2) {
        this.lamp2 = lamp2;
    }

    public void setLamp1(double lamp1) {
        this.lamp1 = lamp1;
    }

    public ThingInThingworx() {
    }

    @Override
    public String toString() {
        return "ThingInThingworx{" +
                "OneParam=" + OneParam +
                ", lamp2=" + lamp2 +
                ", lamp1=" + lamp1 +
                '}';
    }
}
