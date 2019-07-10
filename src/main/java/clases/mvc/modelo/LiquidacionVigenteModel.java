package clases.mvc.modelo;

import clases.DAOmanager;
import clases.EventBusFactory;
import clases.Gasto;
import clases.UnidadFuncional;
import com.google.common.eventbus.EventBus;

import java.util.List;

public class LiquidacionVigenteModel {
    DAOmanager daoManager = DAOmanager.getDAOmanager();

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
