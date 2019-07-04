package clases.mvc.modelo;

import clases.*;
import com.google.common.eventbus.EventBus;

import java.io.*;
import java.util.List;

public class CerrarLiquidacionModel {
    private EventBus bus;
    DbManager dbManager = DbManager.getDbManager();

    public CerrarLiquidacionModel() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
    }

    public List<String> getNombresDeConsorcios() {
        return dbManager.getListaNombresConsorcios();
    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        dbManager.cerrarLiquidacion(nombreConsorcio);
    }

    public void cerrarLiquidacionGenerarInforme(String nombreConsorcio) throws IOException {
        Liquidacion liquidacion = dbManager.cerrarLiquidacionGenerarInforme(nombreConsorcio);
        Printer.printLiquidacionCierre(liquidacion);
    }
}

