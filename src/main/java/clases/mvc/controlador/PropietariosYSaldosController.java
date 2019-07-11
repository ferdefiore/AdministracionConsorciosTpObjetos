package clases.mvc.controlador;

import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import clases.mvc.modelo.PropietariosYSaldosModel;
import clases.mvc.vista.PropietariosYSaldosView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class PropietariosYSaldosController {
    private PropietariosYSaldosView view;
    private PropietariosYSaldosModel model;
    private EventBus bus;

    public PropietariosYSaldosController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        model = new PropietariosYSaldosModel();
        view = new PropietariosYSaldosView(model.getListaPropietarios(), model.getListaUnidadesFuncionales());
        bus.register(model);
        bus.register(view);
    }

    @Subscribe
    public void onPedirFiltroPersona(PropietariosYSaldosView.PedirFiltroPersona event) {
        if (event.dni.equals(Constantes.stringVacio) && event.nya.equals(Constantes.stringVacio)) {
            view.poblarPropietarios(model.getListaPropietarios());
        } else {
            String nomYApe;
            String documento;
            if (event.nya.equals(Constantes.stringVacio)) nomYApe = Constantes.txtVacioDocumentoNyA;
            else nomYApe = event.nya;
            if (event.dni.equals(Constantes.stringVacio)) documento = Constantes.txtVacioDocumentoNyA;
            else documento = event.dni;
            view.poblarPropietarios(model.getListaPropietarios(nomYApe, documento));
        }
    }

    @Subscribe
    public void onPedirFiltroUnidadFuncional(PropietariosYSaldosView.PedirFiltroUnidadFunciona event) {
        view.poblarUnidadesFuncionales(model.getListaUnidadesFuncionales(event.monto, event.comparador));
    }

    @Subscribe
    public void onTerminarPropietariosYSaldos(String event) {
        if (event.equals(Constantes.terminarPropietariosYSaldos)) {
            bus.unregister(this);
            bus.unregister(view);
            bus.unregister(model);
            view = null;
            model = null;
            System.gc();
        }
    }
}
