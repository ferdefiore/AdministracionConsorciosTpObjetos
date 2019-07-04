package clases.mvc.controlador;

import clases.EventBusFactory;
import clases.mvc.modelo.AgregarGastoModel;
import clases.mvc.vista.AgregarGastoView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public class AgregarGastoController {
    private AgregarGastoView agregarGastoView;
    private AgregarGastoModel agregarGastoModel;
    private EventBus bus;

    public AgregarGastoController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        this.agregarGastoModel = new AgregarGastoModel();
        List<String> nombresConsorcios = agregarGastoModel.getNombresConsorcios();
        List<Integer> idGastos = agregarGastoModel.getIdGastos(nombresConsorcios.get(0));
        this.agregarGastoView = new AgregarGastoView(nombresConsorcios,idGastos);
    }

    @Subscribe
    public void onSolicitudListaGastos(AgregarGastoView.SolicitudListaGastos event){
        agregarGastoView.poblarGastos(agregarGastoModel.getListaGastosParaConsorcio(event.nombreConsorcio));
    }

    @Subscribe
    public void onAgregarNuevoGasto(AgregarGastoView.AgregarNuevoGasto event){
        agregarGastoModel.agregarNuevoGasto(event.nombreConsorcio,event.concepto,event.monto);
    }

    @Subscribe
    public void onAgregarAGasto(AgregarGastoView.AgregarAGasto event){
        agregarGastoModel.agregarAGasto(event.nombreConsorcio,event.idGastoSeleccionado,event.concepto,event.monto);
    }

    @Subscribe
    public  void onTerminar(String event){
        if (event.equals("Cerrame todo que ya termino los gastos papa")){
            System.out.println("Cerrame todo que ya termino los gastos papa");
            bus.unregister(this);
            bus.unregister(agregarGastoView);
            bus.unregister(agregarGastoModel);
            agregarGastoView = null;
            agregarGastoModel = null;
            System.gc();
        }
    }
}
