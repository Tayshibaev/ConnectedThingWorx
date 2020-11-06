package interfaces;

import events.EventRs;

import java.util.EventObject;

//интерфейс для выполнения события извне по запросу из другого объекта
public interface EventRsListenerInterface {
    void actionPerformed(EventObject e);
}
