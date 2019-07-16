package clases.mvc.controlador;

import clases.clasesRelacionales.Gasto;
import clases.clasesRelacionales.UnidadFuncional;
import clases.mvc.modelo.LiquidacionVigenteModel;
import clases.mvc.vista.LiquidacionVigenteView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public class LiquidacionVigenteController {
    private LiquidacionVigenteModel model;
    private LiquidacionVigenteView view;
    private EventBus bus;

    public LiquidacionVigenteController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        model = new LiquidacionVigenteModel();
        List<String> listaConsorcios = model.getNombresDeConsorcios();
        view = new LiquidacionVigenteView(listaConsorcios);
    }

    @Subscribe
    public void onSolicitudGastosLiquidacion(LiquidacionVigenteView.SolicitudGastosLiquidacion event) {
        List<Gasto> gastos = model.getDatosLiquidacionVigente(event.nombreConsorcio);
        view.poblarListaGasto(gastos);
    }

    @Subscribe
    public void onSolicitudSaldosLiquidacion(LiquidacionVigenteView.SolicitudSaldosLiquidacion event) {
        List<UnidadFuncional> uf = model.getUnidadesFuncionales(event.nombreConsorcio);
        view.poblarListaUf(uf);
    }

    @Subscribe
    public void onCerrarVentanaLiquidacionVigente(String event) {
        if (event.equals(Constantes.terminarLiquidacionVigente)) {
            EventBusFactory.unregisterAndGc(this, view, model);
        }
    }
}
