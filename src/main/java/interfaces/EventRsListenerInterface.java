package interfaces;

import events.EventRs;
//интерфейс для выполнения события извне по запросу из другого объекта
public interface EventRsListenerInterface {
    void actionPerformed(EventRs e);
}
