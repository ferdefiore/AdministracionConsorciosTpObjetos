package clases.mvc.modelo;

import clases.clasesRelacionales.LiquidacionesHistoricas;
import clases.utils.DAOmanager;

public class ModeloManejadorHistoricas {

    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    void inicializadorLiquidacionesHistoricas(){
        LiquidacionesHistoricas liquidacionesHistoricas = daoManager.getLiquidacionesHistoricas();
        if (null == liquidacionesHistoricas){
            liquidacionesHistoricas = new LiquidacionesHistoricas();
            daoManager.guardarLiquidacionHistorica(liquidacionesHistoricas);
        }
    }

}
