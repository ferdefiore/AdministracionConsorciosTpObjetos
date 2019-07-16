package clases.mvc.modelo;

import clases.clasesRelacionales.Propietario;
import clases.clasesRelacionales.UnidadFuncional;
import clases.filtro.*;
import clases.utils.DAOmanager;

import java.util.ArrayList;
import java.util.List;

public class PropietariosYSaldosModel {
    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getListaPropietarios() {
        List<Propietario> propietarios = daoManager.getListaPropietarios();
        List<String> ret = new ArrayList<>();
        for (Propietario p : propietarios) {
            ret.add(p.toString());
        }
        return ret;
    }

    public List<String> getListaPropietarios(String nomYApe, String dni) {
        List<String> personasString = this.getListaPropietarios();
        FiltroTexto filtroNombreApellido = new FiltroTexto(nomYApe);
        FiltroTexto filtroDni = new FiltroTexto(dni);
        FiltroOr filtroOr = new FiltroOr(filtroNombreApellido,filtroDni);
        List<String> retorno = new ArrayList<>();
        for (String s : personasString) {
            if (filtroOr.seCumple(s)) {
                retorno.add(s);
            }
        }
        return retorno;
    }

    public List<String> getListaUnidadesFuncionales() {
        List<UnidadFuncional> unidadesFuncionales = daoManager.getListaUnidadesFuncionales();
        List<String> retList = new ArrayList<>();
        for (UnidadFuncional uf : unidadesFuncionales) {
            retList.add(uf.toString());
        }
        return retList;
    }

    public List<String> getListaUnidadesFuncionales(float monto, String comparador) {
        List<UnidadFuncional> unidadesFuncionales = daoManager.getListaUnidadesFuncionales();
        List<String> retList = new ArrayList<>();
        FiltroSaldo filtroSaldo;
        switch (comparador) {
            case "=":
                filtroSaldo = new FiltroIgual(monto);
                break;
            case "<":
                filtroSaldo = new FiltroMenor(monto);
                break;
            default:
                filtroSaldo = new FIltroMayor(monto);
                break;
        }
        for (UnidadFuncional uf : unidadesFuncionales) {
            if (filtroSaldo.seCumple(uf.getSaldo())) {
                retList.add(uf.toString());
            }
        }
        return retList;
    }
}
