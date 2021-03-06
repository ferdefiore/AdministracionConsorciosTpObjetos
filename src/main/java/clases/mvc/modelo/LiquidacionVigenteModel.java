package clases.mvc.modelo;

import clases.clasesRelacionales.Gasto;
import clases.clasesRelacionales.UnidadFuncional;
import clases.utils.DAOmanager;

import java.util.List;

public class LiquidacionVigenteModel {
    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getNombresDeConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<Gasto> getDatosLiquidacionVigente(String nombreConsorcio) {
        return daoManager.getGastosLiquidacionVigente(nombreConsorcio);
    }

    public List<UnidadFuncional> getUnidadesFuncionales(String nombreConsorcio) {
        return daoManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
    }
}
