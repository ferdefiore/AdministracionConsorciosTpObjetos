package clases.mvc.modelo;

import clases.clasesRelacionales.Liquidacion;
import clases.clasesRelacionales.UnidadFuncional;
import clases.utils.DAOmanager;
import clases.utils.Printer;

import java.io.IOException;
import java.util.List;

public class CerrarLiquidacionModel {

    DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getNombresDeConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        daoManager.cerrarLiquidacion(nombreConsorcio);
    }

    public Liquidacion cerrarLiquidacionGenerarInforme(String nombreConsorcio) throws IOException {
        return daoManager.cerrarLiquidacionGenerarInforme(nombreConsorcio);
    }

    public List<UnidadFuncional> getListaUnidadesFuncionales(String nombreConsorcio){
        return daoManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
    }
}

