package clases.mvc.modelo;

import clases.clasesRelacionales.Consorcio;
import clases.clasesRelacionales.Liquidacion;
import clases.clasesRelacionales.LiquidacionesHistoricas;
import clases.clasesRelacionales.UnidadFuncional;
import clases.utils.DAOmanager;

import java.util.List;

public class CerrarLiquidacionModel {

    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getNombresDeConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }


    public List<UnidadFuncional> getListaUnidadesFuncionales(String nombreConsorcio) {
        return daoManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        this.cerrarYObtenerLiquidacion(nombreConsorcio);
    }

    public Liquidacion cerrarYObtenerLiquidacion(String nombreConsorcio){
        Consorcio consorcio = daoManager.getConsorcio(nombreConsorcio);
        Liquidacion vieja = consorcio.cerrarLiquidacion();
        LiquidacionesHistoricas liquidacionesHistoricas = daoManager.getLiquidacionesHistoricas();
        liquidacionesHistoricas.agregarHistorica(consorcio.getId(),vieja);
        daoManager.actualizarConsorcioYAgregarHistorica(consorcio,liquidacionesHistoricas);
        return vieja;
    }
}

