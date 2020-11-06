package things.response;

import com.fasterxml.jackson.annotation.*;
import interfaces.InterfaceRs;
import things.AbstractThingClass;

@JsonAutoDetect
public class RobotThingRs extends AbstractThingClass implements InterfaceRs {
    @JsonProperty("X1")
    public double X1;
    @JsonProperty("Y1")
    public double Y1;
    @JsonProperty("T1")
    public double T1;
    @JsonProperty("G1")
    public double G1;
    @JsonProperty("N")
    public double N;
    @JsonProperty(value = "L1", access = JsonProperty.Access.READ_WRITE,
             defaultValue = "L1")
    public double L1;

    public RobotThingRs() {
    }

    public RobotThingRs(@JsonProperty("X1") double x1,
                        @JsonProperty("Y1") double y1,
                        @JsonProperty("T1") double t1,
                        @JsonProperty("G1") double g1,
                        @JsonProperty("N") double n,
                        @JsonProperty("L1") double l1) {
        this.X1 = x1;
        this.Y1 = y1;
        this.T1 = t1;
        this.G1 = g1;
        this.N = n;
        this.L1 = l1;
    }

    @JsonGetter("X1")
    public double getX1() {
        return X1;
    }

    @JsonSetter("X1")
    public void setX1(@JsonProperty("X1") double x1) {
        this.X1 = x1;
    }

    @JsonGetter("Y1")
    public double getY1() {
        return Y1;
    }

    @JsonSetter("Y1")
    public void setY1(@JsonProperty("Y1") double y1) {
        this.Y1 = y1;
    }

    @JsonGetter("T1")
    public double getT1() {
        return T1;
    }

    @JsonSetter("T1")
    public void setT1( @JsonProperty("T1") double t1) {
        this.T1 = t1;
    }

    @JsonGetter("G1")
    public double getG1() {
        return G1;
    }

    @JsonSetter("G1")
    public void setG1(@JsonProperty("G1") double g1) {
        this.G1 = g1;
    }

    @JsonGetter("N1")
    public double getN() {
        return N;
    }

    @JsonSetter("N1")
    public void setN( @JsonProperty("N") double n) {
        this.N = n;
    }

    @JsonGetter("L1")
    public double getL1() {
        return L1;
    }

    @JsonSetter("L1")
    public void setL1(@JsonProperty("L1") double L1) { this.L1 = L1; }

    @Override
    public String toString() {
        return "RobotThingRs{" +
                "X1=" + X1 +
                ", Y1=" + Y1 +
                ", T1=" + T1 +
                ", G1=" + G1 +
                ", N=" + N +
                ", L1=" + L1 +
                '}';
    }

    @Override
    public void getObject(Object obj) {
        if (!(obj instanceof RobotThingRs)) {
            throw new ClassCastException("Передан неверный тип объекта");
        }
        RobotThingRs rr = (RobotThingRs) obj;
        this.X1 = rr.X1;
        this.Y1 = rr.Y1;
        this.G1 = rr.G1;
        this.L1 = rr.L1;
        this.N = rr.N;
        this.T1 = rr.T1;

    }
}
