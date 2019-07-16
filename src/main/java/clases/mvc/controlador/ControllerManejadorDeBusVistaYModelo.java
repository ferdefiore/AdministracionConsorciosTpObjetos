package clases.mvc.controlador;

import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

public class ControllerManejadorDeBusVistaYModelo {

    public void unregisterAndGc(Object controller, Object view, Object model) {
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
