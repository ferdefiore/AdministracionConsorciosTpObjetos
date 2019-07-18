package clases.mvc.controlador;

import clases.mvc.modelo.AgregarGastoModel;
import clases.mvc.vista.AgregarGastoView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import java.util.List;

public class AgregarGastoController extends ControllerManejadorDeBusVistaYModelo {
    private AgregarGastoView view;
    private AgregarGastoModel model;
    private EventBus bus;

    public AgregarGastoController() {
        try {
            bus = EventBusFactory.getEventBus();
            bus.register(this);
            this.model = new AgregarGastoModel();
            List<String> nombresConsorcios = model.getNombresConsorcios();
            List<String> idGastos = model.getListaGastosParaConsorcio(nombresConsorcios.get(0));
            this.view = new AgregarGastoView(nombresConsorcios, idGastos);
        }catch (Exception exeption){
            unregisterAndGc(this, view, model);
            JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Subscribe
    public void onSolicitudListaGastos(AgregarGastoView.SolicitudListaGastos event) {
        view.poblarGastos(model.getListaGastosParaConsorcio(event.nombreConsorcio));
    }

    @Subscribe
    public void onAgregarNuevoGasto(AgregarGastoView.AgregarNuevoGasto event) {
        model.agregarNuevoGasto(event.nombreConsorcio, event.concepto, event.monto);
    }

    @Subscribe
    public void onAgregarAGasto(AgregarGastoView.AgregarAGasto event) {
        model.agregarAGasto(event.idGastoSeleccionado, event.concepto, event.monto);
    }

    @Subscribe
    public void onTerminar(String event) {
        if (event.equals(Constantes.terminarAgregarGasto)) {
            unregisterAndGc(this, view, model);
        }
    }
}
