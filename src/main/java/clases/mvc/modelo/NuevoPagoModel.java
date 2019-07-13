package clases.mvc.modelo;

import clases.clasesRelacionales.Pago;
import clases.clasesRelacionales.UnidadFuncional;
import clases.utils.DAOmanager;

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
        UnidadFuncional uf = daoManager.getUnidadFuncionalFromId(idUnidadFuncional);
        Pago nuevopago = new Pago(monto, uf);
        uf.modificarSaldo(monto);
        daoManager.guardarPagoYactualizarUnidadFuncional(nuevopago,uf);
    }
}
