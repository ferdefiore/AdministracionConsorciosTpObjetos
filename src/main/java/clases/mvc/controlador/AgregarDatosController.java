package clases.mvc.controlador;

import clases.EventBusFactory;
import clases.mvc.modelo.AgregarDatosModel;
import clases.mvc.vista.AgregarDatosView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class AgregarDatosController {
    private EventBus bus;
    private AgregarDatosModel model;
    private AgregarDatosView view;

    public AgregarDatosController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        model = new AgregarDatosModel();
        view = new AgregarDatosView(model.getListaConsorcios(),model.getListaPropietarios());
    }
    @Subscribe
    public void onCrearConsorcioNuevo(AgregarDatosView.CrearConsorcioNuevo event) {
        model.crearNuevoConsorcio(event.nombre, event.cuit, event.direccion, event.ciudad);
        view.actualizarListaConsorciosUf(model.getListaConsorcios());
    }

    @Subscribe
    public void onCrearUnidadFuncionalNueva(AgregarDatosView.CrearUnidadFuncionalNueva event){
        String[] dniPropietarioSplit = event.dniPropietario.split(" ");
        System.out.println(dniPropietarioSplit[0]);
        model.crearUnidadFuncional(event.nombreConsorcioPerteneciente, event.tipoUf, dniPropietarioSplit[0], event.coeficientePago, event.pisoNum, event.saldo);
    }

    @Subscribe
    public void onCrearPropietarioNuevo(AgregarDatosView.CrearPropietarioNuevo event){
        model.crearPropietarioNuevo(event.dni,event.nomYApe,event.mail,event.direccion,event.telefono);
        view.actualizarListaPropietarios(model.getListaPropietarios());
    }
}
