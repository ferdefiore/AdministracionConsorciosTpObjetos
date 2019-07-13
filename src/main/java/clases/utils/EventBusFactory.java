package clases.utils;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

public class EventBusFactory {

    //hold the instance of the event bus here
    private static EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static void unregisterAndGc(Object controller, Object view, Object model) {
        EventBus bus = EventBusFactory.getEventBus();
        try {
            bus.unregister(controller);
            if (view != null){
            bus.unregister(view);
            }
            if (model != null){
            bus.unregister(model);
            }
            view = null;
            model = null;
            System.gc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}