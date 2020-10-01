package events;

import java.util.EventObject;

public class EventRs extends EventObject {

    private ComponentEnum component;
    private boolean enable;

    public ComponentEnum getComponent() {
        return component;
    }

    public boolean isEnable() {
        return enable;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventRs(Object source, ComponentEnum component, boolean enable) {
        super(source);
        this.component = component;
        this.enable = enable;
    }

    public enum ComponentEnum{
        PANEL1,
        PANEL2,
        PANEL3,
        PANEL4
    }

}
