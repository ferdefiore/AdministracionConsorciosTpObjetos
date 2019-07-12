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
        if (event.generarInforme) {
            Liquidacion liquidacionVieja = cerrarLiquidacionModel.cerrarLiquidacionGenerarInforme(event.nombreConsorcio);
            Printer.printLiquidacionCierre(liquidacionVieja," Informe Gastos Y Saldos ");
            List<UnidadFuncional> ufsAImprimir = cerrarLiquidacionModel.getListaUnidadesFuncionales(event.nombreConsorcio);
            Printer.printSaldosCierre(event.nombreConsorcio, liquidacionVieja.getPeriodo().toString(), ufsAImprimir);
        } else {
            cerrarLiquidacionModel.cerrarLiquidacion(event.nombreConsorcio);
        }
    }

    @Subscribe
    public void onTerminoView(String event) {
        if (event.equals(Constantes.terminarCerrarLiquidacion)) {
            EventBusFactory.unregisterAndGc(this,cerrarLiquidacionView,cerrarLiquidacionModel);
        }
    }
}
