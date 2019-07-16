package clases.mvc.modelo;

import clases.clasesRelacionales.Consorcio;
import clases.clasesRelacionales.Liquidacion;
import clases.clasesRelacionales.LiquidacionesHistoricas;
import clases.utils.DAOmanager;

import java.io.IOException;
import java.util.List;

public class LiquidacionesHistoricasModel {
    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getListaConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<Liquidacion> getListaHistoricas(String nombreConsorcio) {
        Consorcio consorcio = daoManager.getConsorcio(nombreConsorcio);
        LiquidacionesHistoricas liquidacionesHistoricas = daoManager.getLiquidacionesHistoricas();
        return liquidacionesHistoricas.getHashLiquidaciones(consorcio.getId());
    }

    public Liquidacion imprimirLiquidacion(Integer idLiquidacion) {
        return daoManager.getLiquidacion(idLiquidacion);
    }
}
