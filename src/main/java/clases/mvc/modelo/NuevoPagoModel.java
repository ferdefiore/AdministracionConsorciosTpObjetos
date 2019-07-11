package clases.mvc.modelo;

import clases.utils.DAOmanager;
import clases.clasesRelacionales.UnidadFuncional;

import java.util.ArrayList;
import java.util.List;

public class NuevoPagoModel {
    DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getListaConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<Integer> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        List<Integer> idsUf = new ArrayList<>();
        List<UnidadFuncional> ufs = daoManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
        for (UnidadFuncional uf : ufs) {
            idsUf.add(uf.getId());
        }
        return idsUf;
    }

    public void generarPago(Integer idUnidadFuncional, Double monto) {
        daoManager.generarPago(idUnidadFuncional, monto);
    }
}
