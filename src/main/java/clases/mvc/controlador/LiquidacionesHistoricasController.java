package clases.mvc.controlador;

import clases.Constantes;
import clases.EventBusFactory;
import clases.Liquidacion;
import clases.mvc.modelo.LiquidacionesHistoricasModel;
import clases.mvc.vista.LiquidacionesHistoricasView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LiquidacionesHistoricasController {
    private EventBus bus;
    private LiquidacionesHistoricasModel model;
    private LiquidacionesHistoricasView view;
    private List<Liquidacion> liquidaciones;

    public LiquidacionesHistoricasController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        model = new LiquidacionesHistoricasModel();
        List<String> nombresConsorcios = model.getListaConsorcios();
        List<String> periodos = new ArrayList<>();
        liquidaciones = model.getListaHistoricas(nombresConsorcios.get(Constantes.ceroInteger));
        for(Liquidacion lq : liquidaciones){
            periodos.add(lq.getPeriodo().toString());
        }
        view = new LiquidacionesHistoricasView(nombresConsorcios,periodos);
    }

    @Subscribe
    public void onSolicitudPeriodos(LiquidacionesHistoricasView.SolicitudPeriodos event){
        liquidaciones = model.getListaHistoricas(event.nombreConsorcio);
        List<String> periodos = new ArrayList<>();
        for(Liquidacion lq: liquidaciones){
            periodos.add(lq.getPeriodo().toString());
        }
        view.poblarLiquidacionesHistoricas(periodos);
    }

    @Subscribe
    public void onImprimirLiquidacion(LiquidacionesHistoricasView.ImprimirLiquidacion event) throws IOException {
        for (Liquidacion lq: liquidaciones){
            if (lq.getPeriodo().toString().equals(event.periodo)){
                model.imprimirLiquidacion(lq.getId_liquidacion());
                return;
            }
        }
    }

    @Subscribe
    public void onCerrarVentanasLiquidacionesHistoricas(String event){
        if (event.equals(Constantes.terminarLiquidacionesHistoricas)){
            bus.unregister(this);
            bus.unregister(view);
            view = null;
            model = null;
            System.gc();
        }
    }

}
