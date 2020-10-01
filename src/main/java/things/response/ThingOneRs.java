package things.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import events.EventRs;
import interfaces.InterfaceRs;
import listeners.EventRsManageListener;
import things.AbstractThingClass;

import java.util.logging.Logger;

@JsonAutoDetect
public class ThingOneRs extends AbstractThingClass implements InterfaceRs {
    /*
     * Может быть либо 1, либо 0. Другого не дано
     * */
    private int isEnable1;
    private int isEnable2;
    private int isEnable3;
    private int isEnable4;

    /*
    * Нужно добавить, если нужно отправлять события об изменении данных
    * */

    private EventRsManageListener manageListener;

    public ThingOneRs() {
    }

    public void setManageListener(EventRsManageListener manageListener) {
        this.manageListener = manageListener;
    }

    public int getIsEnable1() {
        return isEnable1;
    }

    public void setIsEnable1(int isEnable1) {
        this.isEnable1 = isEnable1;
    }

    public int getIsEnable2() {
        return isEnable2;
    }

    public void setIsEnable2(int isEnable2) {
        this.isEnable2 = isEnable2;
    }

    public int getIsEnable3() {
        return isEnable3;
    }

    public void setIsEnable3(int isEnable3) {
        this.isEnable3 = isEnable3;
    }

    public int getIsEnable4() {
        return isEnable4;
    }

    public void setIsEnable4(int isEnable4) {
        this.isEnable4 = isEnable4;
    }

    @Override
    public String toString() {
        return "ThingOneRs{" +
                "isEnable1=" + isEnable1 +
                ", isEnable2=" + isEnable2 +
                ", isEnable3=" + isEnable3 +
                ", isEnable4=" + isEnable4 +
                '}';
    }

    @Override
    public void getObject(Object obj) {
        if (!(obj instanceof ThingOneRs)) {
            throw new ClassCastException("Передан неверный тип объекта");
        }
        if (manageListener == null) {
            Logger logger = Logger.getLogger("Logger2");
            logger.warning("Невозможно создать событие! Данные обновляться не будут");
        }
        ThingOneRs clone = (ThingOneRs) obj;
        if (this.isEnable1 != clone.isEnable1) {
            if (clone.isEnable1 == 0 || clone.isEnable1 == 1) {
                this.isEnable1 = clone.isEnable1;
                if (manageListener != null) {
                    manageListener.addEventRs(new EventRs(this, EventRs.ComponentEnum.PANEL1, this.isEnable1 == 0 ? false : true));
                }
            }
        }

        if (this.isEnable2 != clone.isEnable2) {
            if (clone.isEnable2 == 0 || clone.isEnable2 == 1) {
                this.isEnable2 = clone.isEnable2;
                if (manageListener != null) {
                    manageListener.addEventRs(new EventRs(this, EventRs.ComponentEnum.PANEL2, this.isEnable2 == 0 ? false : true));
                }
            }
        }

        if (this.isEnable3 != clone.isEnable3) {
            if (clone.isEnable3 == 0 || clone.isEnable3 == 1) {
                this.isEnable3 = clone.isEnable3;
                if (manageListener != null) {
                    manageListener.addEventRs(new EventRs(this, EventRs.ComponentEnum.PANEL3, this.isEnable3 == 0 ? false : true));
                }
            }
        }

        if (this.isEnable4 != clone.isEnable4) {
            if (clone.isEnable4 == 0 || clone.isEnable4 == 1) {
                this.isEnable4 = clone.isEnable4;
                if (manageListener != null) {
                    manageListener.addEventRs(new EventRs(this, EventRs.ComponentEnum.PANEL4, this.isEnable4 == 0 ? false : true));
                }
            }
        }
        if (manageListener != null) {
            manageListener.executedListener();
        }
    }
}
