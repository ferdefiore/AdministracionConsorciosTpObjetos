package clases.mvc.controlador;

import clases.EventBusFactory;
import clases.mvc.modelo.NuevoPagoModel;
import clases.mvc.vista.NuevoPagoView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public class NuevoPagoController {
    private NuevoPagoModel nuevoPagoModel;
    private NuevoPagoView nuevoPagoView;
    private EventBus bus;

    public NuevoPagoController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        this.nuevoPagoModel = new NuevoPagoModel();
        List<String> listaConsorcios = nuevoPagoModel.getListaConsorcios();
        List<Integer> listaUnidadesFuncionales = nuevoPagoModel.getListaUnidadesFuncionalesConsorcio(listaConsorcios.get(0));
        this.nuevoPagoView = new NuevoPagoView(listaConsorcios,listaUnidadesFuncionales);
    }
    @Subscribe
    public void onSolicitudListaUf(NuevoPagoView.SolicitudListaUf event){
        nuevoPagoView.poblarUnidadesFuncionales(nuevoPagoModel.getListaUnidadesFuncionalesConsorcio(event.nombreConsorcio));
    }

    @Subscribe
    public void onGenerarPago(NuevoPagoView.GenerarPago event){
        nuevoPagoModel.generarPago(event.nombreConsorcio,event.idUnidadFuncional,event.monto);
    }

    @Subscribe
    public  void onTerminar(String event){
        if (event.equals("Termino de agregar el pago")){
            bus.unregister(this);
            bus.unregister(nuevoPagoView);
            bus.unregister(nuevoPagoModel);
            nuevoPagoView = null;
            nuevoPagoModel = null;
            System.gc();
        }
    }
}
