package clases.mvc.controlador;

import clases.clasesRelacionales.Liquidacion;
import clases.clasesRelacionales.UnidadFuncional;
import clases.mvc.modelo.CerrarLiquidacionModel;
import clases.mvc.vista.CerrarLiquidacionView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import clases.utils.Printer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class CerrarLiquidacionController {
    private CerrarLiquidacionModel model;
    private CerrarLiquidacionView view;
    private EventBus bus;

    public CerrarLiquidacionController() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        model = new CerrarLiquidacionModel();
        List<String> listaConsorcios = model.getNombresDeConsorcios();
        view = new CerrarLiquidacionView(listaConsorcios);
    }

    @Subscribe
    public void onCerrarLiquidacion(CerrarLiquidacionView.CerrarLiquidacion event) throws IOException {
        if (event.generarInforme) {
            Liquidacion liquidacionVieja = model.cerrarYObtenerLiquidacion(event.nombreConsorcio);
            Printer.printLiquidacionCierre(liquidacionVieja," Informe Gastos Y Saldos ");
            List<UnidadFuncional> ufsAImprimir = model.getListaUnidadesFuncionales(event.nombreConsorcio);
            Printer.printSaldosCierre(event.nombreConsorcio, liquidacionVieja.getPeriodo().toString(), ufsAImprimir);
        } else {
            model.cerrarLiquidacion(event.nombreConsorcio);
        }
    }

    @Subscribe
    public void onTerminoView(String event) {
        if (event.equals(Constantes.terminarCerrarLiquidacion)) {
            EventBusFactory.unregisterAndGc(this, view, model);
        }
    }
}
