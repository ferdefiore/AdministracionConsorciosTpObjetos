package clases.mvc.controlador;

import clases.mvc.modelo.AgregarGastoModel;
import clases.mvc.vista.AgregarGastoView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.activation.MailcapCommandMap;
import javax.swing.*;
import java.util.List;

public class AgregarGastoController {
    private AgregarGastoView agregarGastoView;
    private AgregarGastoModel agregarGastoModel;
    private EventBus bus;

    public AgregarGastoController() {
        try {
            bus = EventBusFactory.getEventBus();
            bus.register(this);
            this.agregarGastoModel = new AgregarGastoModel();
            List<String> nombresConsorcios = agregarGastoModel.getNombresConsorcios();
            List<Integer> idGastos = agregarGastoModel.getIdGastos(nombresConsorcios.get(0));
            this.agregarGastoView = new AgregarGastoView(nombresConsorcios, idGastos);
        }catch (Exception exeption){
            EventBusFactory.unregisterAndGc(this,agregarGastoView,agregarGastoModel);
            JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Subscribe
    public void onSolicitudListaGastos(AgregarGastoView.SolicitudListaGastos event) {
        agregarGastoView.poblarGastos(agregarGastoModel.getListaGastosParaConsorcio(event.nombreConsorcio));
    }

    @Subscribe
    public void onAgregarNuevoGasto(AgregarGastoView.AgregarNuevoGasto event) {
        //bus.unregister(this);
        agregarGastoModel.agregarNuevoGasto(event.nombreConsorcio, event.concepto, event.monto);
    }

    @Subscribe
    public void onAgregarAGasto(AgregarGastoView.AgregarAGasto event) {
        //bus.unregister(this);
        agregarGastoModel.agregarAGasto(event.nombreConsorcio, event.idGastoSeleccionado, event.concepto, event.monto);
    }

    @Subscribe
    public void onTerminar(String event) {
        if (event.equals(Constantes.terminarAgregarGasto)) {
            EventBusFactory.unregisterAndGc(this,agregarGastoView,agregarGastoModel);
        }
    }
}
