package things.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import things.AbstractThingClass;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@JsonAutoDetect
public class RobotThingRq extends AbstractThingClass {
    private volatile double m11;
    private volatile double m12;
    private volatile double m13;
    private volatile double m14;
    private volatile double m15;
    private volatile double m16;

    private List<Double> listtM = Arrays.asList(m11, m12, m13, m14, m15, m16);

    private volatile double l11;
    private volatile double l12;
    private volatile double l13;
    private volatile double l14;
    private volatile double l15;
    private volatile double l16;

    private List<Double> listtL = Arrays.asList(l11, l12, l13, l14, l15, l16);

    private volatile double t11;
    private volatile double t12;
    private volatile double t13;
    private volatile double t14;
    private volatile double t15;
    private volatile double t16;

    private List<Double> listtT = Arrays.asList(t11, t12, t13, t14, t15, t16);

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

    public synchronized double getM12() {
        return m12;
    }

    public void setM12(double m12) {
        this.m12 = m12;
    }

    public synchronized double getM13() {
        return m13;
    }

    public void setM13(double m13) {
        this.m13 = m13;
    }

    public synchronized double getM14() {
        return m14;
    }

    public void setM14(double m14) {
        this.m14 = m14;
    }

    public synchronized double getM15() {
        return m15;
    }

    public void setM15(double m15) {
        this.m15 = m15;
    }

    public synchronized double getM16() {
        return m16;
    }

    public void setM16(double m16) {
        this.m16 = m16;
    }

    public synchronized double getL12() {
        return l12;
    }

    public void setL12(double l12) {
        this.l12 = l12;
    }

    public synchronized double getL13() {
        return l13;
    }

    public void setL13(double l13) {
        this.l13 = l13;
    }

    public synchronized double getL14() {
        return l14;
    }

    public void setL14(double l14) {
        this.l14 = l14;
    }

    public synchronized double getL15() {
        return l15;
    }

    public void setL15(double l15) {
        this.l15 = l15;
    }

    public synchronized double getL16() {
        return l16;
    }

    public void setL16(double l16) {
        this.l16 = l16;
    }

    public synchronized double getT11() {
        return t11;
    }

    public void setT11(double t11) {
        this.t11 = t11;
    }

    public synchronized double getT12() {
        return t12;
    }

    public void setT12(double t12) {
        this.t12 = t12;
    }

    public synchronized double getT13() {
        return t13;
    }

    public void setT13(double t13) {
        this.t13 = t13;
    }

    public synchronized double getT14() {
        return t14;
    }

    public void setT14(double t14) {
        this.t14 = t14;
    }

    public synchronized double getT15() {
        return t15;
    }

    public void setT15(double t15) {
        this.t15 = t15;
    }

    public synchronized double getT16() {
        return t16;
    }

    public void setT16(double t16) {
        this.t16 = t16;
    }

    private void initT(List<Double> listL) {
        try {
            t11 = listL.get(0);
            t12 = listL.get(1);
            t13 = listL.get(2);
            t14 = listL.get(3);
            t15 = listL.get(4);
            t16 = listL.get(5);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Количество параметров Нагрузки 6, а измненилось только первые " + listL.size() + " из них");
        }
    }

    private void initM(List<Double> listL) {
        try {
            m11 = listL.get(0);
            m12 = listL.get(1);
            m13 = listL.get(2);
            m14 = listL.get(3);
            m15 = listL.get(4);
            m16 = listL.get(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Количество параметров Нагрузки 6, а измненилось только первые " + listL.size() + " из них");
        }
    }

    private void initL(List<Double> listL) {
        try {
            l11 = listL.get(0);
            l12 = listL.get(1);
            l13 = listL.get(2);
            l14 = listL.get(3);
            l15 = listL.get(4);
            l16 = listL.get(5);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Количество параметров Нагрузки 6, а измненилось только первые " + listL.size() + " из них");
        }
    }

    public void setAllParams(List<Double> listL, List<Double> listT, List<Double> listM) {
        initL(listL);
        initM(listM);
        initT(listT);
    }

    @Override
    public String toString() {
        return "RobotThingRq{" +
                "m11=" + m11 +
                ", m12=" + m12 +
                ", m13=" + m13 +
                ", m14=" + m14 +
                ", m15=" + m15 +
                ", m16=" + m16 +
                ", l11=" + l11 +
                ", l12=" + l12 +
                ", l13=" + l13 +
                ", l14=" + l14 +
                ", l15=" + l15 +
                ", l16=" + l16 +
                ", t11=" + t11 +
                ", t12=" + t12 +
                ", t13=" + t13 +
                ", t14=" + t14 +
                ", t15=" + t15 +
                ", t16=" + t16 +
                '}';
    }
}
