package clases.mvc.modelo;

import clases.DbManager;
import clases.EventBusFactory;
import clases.Liquidacion;
import clases.Printer;
import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class LiquidacionesHistoricasModel {
    private DbManager dbManager = DbManager.getDbManager();
    private EventBus bus;

    public LiquidacionesHistoricasModel() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
    }

    public List<String> getListaConsorcios() {
        return dbManager.getListaNombresConsorcios();
    }

    public List<Liquidacion> getListaHistoricas(String nombreConsorcio) {
        return dbManager.getHistoricas(nombreConsorcio);
    }

    public void imprimirLiquidacion(Integer idLiquidacion) throws IOException {
        Liquidacion liquidacion = dbManager.getLiquidacion(idLiquidacion);
        Printer.printLiquidacionCierre(liquidacion);
    }
}
