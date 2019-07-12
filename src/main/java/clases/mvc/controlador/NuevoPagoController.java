package clases.mvc.controlador;

import clases.mvc.modelo.NuevoPagoModel;
import clases.mvc.vista.NuevoPagoView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import java.util.List;

public class NuevoPagoController {
    private NuevoPagoModel nuevoPagoModel;
    private NuevoPagoView nuevoPagoView;
    private EventBus bus;

    public NuevoPagoController() {
        try {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        this.nuevoPagoModel = new NuevoPagoModel();
        List<String> listaConsorcios = nuevoPagoModel.getListaConsorcios();
        List<Integer> listaUnidadesFuncionales = nuevoPagoModel.getListaUnidadesFuncionalesConsorcio(listaConsorcios.get(0));
        this.nuevoPagoView = new NuevoPagoView(listaConsorcios, listaUnidadesFuncionales);
        }catch (Exception exeption){
            EventBusFactory.unregisterAndGc(this,nuevoPagoView,nuevoPagoModel);
            JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Subscribe
    public void onSolicitudListaUf(NuevoPagoView.SolicitudListaUf event) {
        nuevoPagoView.poblarUnidadesFuncionales(nuevoPagoModel.getListaUnidadesFuncionalesConsorcio(event.nombreConsorcio));
    }

    @Subscribe
    public void onGenerarPago(NuevoPagoView.GenerarPago event) {
        nuevoPagoModel.generarPago(event.idUnidadFuncional, event.monto);
    }

    @Subscribe
    public void onTerminar(String event) {
        if (event.equals(Constantes.terminarAgregarPago)) {
            EventBusFactory.unregisterAndGc(this,nuevoPagoView,nuevoPagoModel);
        }
    }
}
