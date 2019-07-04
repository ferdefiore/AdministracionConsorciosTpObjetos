package clases.mvc.controlador;

import clases.EventBusFactory;
import clases.Gasto;
import clases.mvc.modelo.CerrarLiquidacionModel;
import clases.mvc.modelo.LiquidacionVigenteModel;
import clases.mvc.vista.CerrarLiquidacionView;
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
    public void onSolicitudLiquidacion(LiquidacionVigenteView.SolicitudLiquidacion event){
        List<Gasto> gastos = liquidacionVigenteModel.getDatosLiquidacionVigente(event.nombreConsorcio);
        liquidacionVigenteView.poblarLista(gastos);
    }
}
