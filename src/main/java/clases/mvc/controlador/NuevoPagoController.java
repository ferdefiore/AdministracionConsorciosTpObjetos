package clases.mvc.controlador;

import clases.mvc.modelo.NuevoPagoModel;
import clases.mvc.vista.NuevoPagoView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import java.util.List;

public class NuevoPagoController extends ControllerManejadorDeBusVistaYModelo {
    private NuevoPagoModel model;
    private NuevoPagoView view;
    private EventBus bus;

    public NuevoPagoController() {
        try {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        this.model = new NuevoPagoModel();
        List<String> listaConsorcios = model.getListaConsorcios();
        List<String> listaUnidadesFuncionales = model.getDatosUnidadesFuncionalesConsorcio(listaConsorcios.get(0));
        this.view = new NuevoPagoView(listaConsorcios, listaUnidadesFuncionales);
        }catch (Exception exeption){
            unregisterAndGc(this, view, model);
            JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Subscribe
    public void onSolicitudListaUf(NuevoPagoView.SolicitudListaUf event) {
        view.poblarUnidadesFuncionales(model.getDatosUnidadesFuncionalesConsorcio(event.nombreConsorcio));
    }

    @Subscribe
    public void onGenerarPago(NuevoPagoView.GenerarPago event) {
        model.generarPago(event.idUnidadFuncional, event.monto);
    }

    @Subscribe
    public void onTerminar(String event) {
        if (event.equals(Constantes.terminarAgregarPago)) {
            unregisterAndGc(this, view, model);
        }
    }
}
