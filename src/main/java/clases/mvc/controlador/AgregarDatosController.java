package clases.mvc.controlador;

import clases.mvc.modelo.AgregarDatosModel;
import clases.mvc.vista.AgregarDatosView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;

public class AgregarDatosController {
    private EventBus bus;
    private AgregarDatosModel model;
    private AgregarDatosView view;

    public AgregarDatosController() {
        try {
            model = new AgregarDatosModel();
            view = new AgregarDatosView(model.getListaConsorcios(), model.getListaPropietarios());
            bus = EventBusFactory.getEventBus();
            bus.register(this);
        }catch (Exception exeption){
            EventBusFactory.unregisterAndGc(this,view,model);
            JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Subscribe
    public void onCrearConsorcioNuevo(AgregarDatosView.CrearConsorcioNuevo event) {
        model.crearNuevoConsorcio(event.nombre, event.cuit, event.direccion, event.ciudad);
        view.actualizarListaConsorciosUf(model.getListaConsorcios());
    }

    @Subscribe
    public void onCrearUnidadFuncionalNueva(AgregarDatosView.CrearUnidadFuncionalNueva event) {
        String[] dniPropietarioSplit = event.dniPropietario.split(Constantes.stringEspacio);
        model.crearUnidadFuncional(event.nombreConsorcioPerteneciente, event.tipoUf, dniPropietarioSplit[0], event.coeficientePago, event.pisoNum, event.saldo);
    }

    @Subscribe
    public void onCrearPropietarioNuevo(AgregarDatosView.CrearPropietarioNuevo event) {
        model.crearPropietarioNuevo(event.dni, event.nomYApe, event.mail, event.direccion, event.telefono);
        view.actualizarListaPropietarios(model.getListaPropietarios());
    }

    @Subscribe
    public void onTerminarDeAgregarDatos(String event) {
        if (event.equals(Constantes.terminarAgregarDatos)) {
            EventBusFactory.unregisterAndGc(this,view,model);
        }
    }
}
