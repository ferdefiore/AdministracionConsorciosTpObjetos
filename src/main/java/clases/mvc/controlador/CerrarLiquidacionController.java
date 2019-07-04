package clases.mvc.controlador;

import clases.EventBusFactory;
import clases.mvc.modelo.CerrarLiquidacionModel;
import clases.mvc.vista.CerrarLiquidacionView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class CerrarLiquidacionController {
    CerrarLiquidacionModel cerrarLiquidacionModel;
    CerrarLiquidacionView cerrarLiquidacionView;
    EventBus bus;

    public CerrarLiquidacionController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        cerrarLiquidacionModel = new CerrarLiquidacionModel();
        List<String> listaConsorcios = cerrarLiquidacionModel.getNombresDeConsorcios();
        cerrarLiquidacionView = new CerrarLiquidacionView(listaConsorcios);
    }

    @Subscribe
    public void onCerrarLiquidacion(CerrarLiquidacionView.CerrarLiquidacion event) throws IOException {
        if (event.generarInforme){
            cerrarLiquidacionModel.cerrarLiquidacionGenerarInforme(event.nombreConsorcio);
        }else {
            cerrarLiquidacionModel.cerrarLiquidacion(event.nombreConsorcio);
        }
    }
}
