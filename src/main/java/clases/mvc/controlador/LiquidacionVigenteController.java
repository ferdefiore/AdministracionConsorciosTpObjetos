package clases.mvc.controlador;

import clases.Constantes;
import clases.EventBusFactory;
import clases.Gasto;
import clases.UnidadFuncional;
import clases.mvc.modelo.LiquidacionVigenteModel;
import clases.mvc.vista.LiquidacionVigenteView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public class LiquidacionVigenteController {
    LiquidacionVigenteModel liquidacionVigenteModel;
    LiquidacionVigenteView liquidacionVigenteView;
    EventBus bus;

    public LiquidacionVigenteController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        liquidacionVigenteModel = new LiquidacionVigenteModel();
        List<String> listaConsorcios = liquidacionVigenteModel.getNombresDeConsorcios();
        liquidacionVigenteView = new LiquidacionVigenteView(listaConsorcios);
    }

    @Subscribe
    public void onSolicitudGastosLiquidacion(LiquidacionVigenteView.SolicitudGastosLiquidacion event){
        List<Gasto> gastos = liquidacionVigenteModel.getDatosLiquidacionVigente(event.nombreConsorcio);
        liquidacionVigenteView.poblarListaGasto(gastos);
    }

    @Subscribe
    public void onSolicitudSaldosLiquidacion(LiquidacionVigenteView.SolicitudSaldosLiquidacion event){
        List<UnidadFuncional> uf = liquidacionVigenteModel.getUnidadesFuncionales(event.nombreConsorcio);
        liquidacionVigenteView.poblarListaUf(uf);
    }

    @Subscribe
    public void onCerrarVentanaLiquidacionVigente(String event){
        if (event.equals(Constantes.terminarLiquidacionVigente)){
            bus.unregister(this);
            bus.unregister(liquidacionVigenteView);
            liquidacionVigenteModel= null;
            liquidacionVigenteView = null;
            System.gc();
        }
    }
}
