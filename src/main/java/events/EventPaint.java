package events;

import java.util.EventObject;

public class EventPaint extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventPaint(Object source) {
        super(source);
    }

    public enum FlagEnum {
        TRUE,
        FALSE
    }
}
