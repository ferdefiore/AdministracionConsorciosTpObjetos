package clases.mvc.modelo;

import clases.DAOmanager;
import clases.EventBusFactory;
import clases.Liquidacion;
import clases.Printer;
import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class LiquidacionesHistoricasModel {
    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getListaConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<Liquidacion> getListaHistoricas(String nombreConsorcio) {
        return daoManager.getHistoricas(nombreConsorcio);
    }

    public void imprimirLiquidacion(Integer idLiquidacion) throws IOException {
        Liquidacion liquidacion = daoManager.getLiquidacion(idLiquidacion);
        Printer.printLiquidacionCierre(liquidacion);
    }
}
