package clases.mvc.modelo;

import clases.utils.DAOmanager;
import clases.clasesRelacionales.UnidadFuncional;
import clases.filtro.*;

import java.util.ArrayList;
import java.util.List;

public class PropietariosYSaldosModel {
    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getListaPropietarios() {
        return daoManager.getListaPropietarios();
    }

    public List<String> getListaPropietarios(String nomYApe, String dni) {
        List<String> personasString = daoManager.getListaPropietarios();
        FiltroTexto filtroNombreApellido = new FiltroTexto(nomYApe);
        FiltroTexto filtroDni = new FiltroTexto(dni);
        List<String> retorno = new ArrayList<>();
        for (String s : personasString) {
            if ((filtroNombreApellido.seCumple(s) || filtroDni.seCumple(s))) {
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
