package clases.mvc.controlador;

import clases.EventBusFactory;
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
    public void onPedirFiltroPersona(PropietariosYSaldosView.PedirFiltroPersona event){
        String nomYApe;
        String documento;
        if (event.nya.equals("")) nomYApe = "-1";
        else nomYApe = event.nya;
        if (event.dni.equals("")) documento = "-1";
        else documento = event.dni;
        view.poblarPropietarios(model.getListaPropietarios(nomYApe,documento));
    }

    @Subscribe
    public void onPedirFiltroUnidadFuncional(PropietariosYSaldosView.PedirFiltroUnidadFunciona event){
        view.poblarUnidadesFuncionales(model.getListaUnidadesFuncionales(event.monto,event.comparador));
    }
}
