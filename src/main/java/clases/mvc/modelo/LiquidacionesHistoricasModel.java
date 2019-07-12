package clases.mvc.modelo;

import clases.clasesRelacionales.Liquidacion;
import clases.utils.DAOmanager;
import clases.utils.Printer;

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

    public Liquidacion imprimirLiquidacion(Integer idLiquidacion) throws IOException {
        return daoManager.getLiquidacion(idLiquidacion);
    }
}
