package listeners;

import events.EventRs;
import interfaces.EventRsListenerInterface;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class EventRsManageListener {
    private EventRsListenerInterface evListener;
    private List<EventRs> eventRsList = new LinkedList<>();
    private Object lock = new Object();

    public void setEvListener(EventRsListenerInterface evListener) {
        this.evListener = evListener;
    }

    public void addEventRs(EventRs ev) {
            eventRsList.add(ev);
    }

    public void executedListener() {
        if (evListener != null) {
                if (!eventRsList.isEmpty()) {
                    Iterator<EventRs> iterator = eventRsList.iterator();
                    while (iterator.hasNext()) {
                        EventRs eventRs = iterator.next();
                        evListener.actionPerformed(eventRs);
                        iterator.remove();
                    }
            }
        } else {
            Logger logger  = Logger.getLogger("LoggingJul.class.getName()");;
            logger.warning("Отсутствует Листенер! Метод не работает, определите Листенер");
        }
    }
}
