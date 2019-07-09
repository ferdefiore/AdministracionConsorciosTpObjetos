package clases.mvc.modelo;

import clases.DbManager;
import clases.UnidadFuncional;
import clases.filtro.*;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PropietariosYSaldosModel {
    private DbManager dbManager = DbManager.getDbManager();
    private EventBus bus;

    public List<String> getListaPropietarios() {
        return dbManager.getListaPropietarios();
    }

    public List<String> getListaPropietarios(String nomYApe, String dni) {
        List<String> personasString =  dbManager.getListaPropietarios();
        FiltroTexto filtroNombreApellido = new FiltroTexto(nomYApe);
        FiltroTexto filtroDni = new FiltroTexto(dni);
        for (String s : personasString){
            if (!(filtroNombreApellido.seCumple(s) || filtroDni.seCumple(s))){
                personasString.remove(s);
            }
        }
        return personasString;
    }

    public List<String> getListaUnidadesFuncionales() {
        List<UnidadFuncional> unidadesFuncionales = dbManager.getListaUnidadesFuncionales();
        List<String> retList = new ArrayList<>();
        for (UnidadFuncional uf:unidadesFuncionales){
            retList.add(uf.toString());
        }
        return retList;
    }

    public List<String> getListaUnidadesFuncionales(float monto, String comparador) {
        List<UnidadFuncional> unidadesFuncionales = dbManager.getListaUnidadesFuncionales();
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
        for (UnidadFuncional uf:unidadesFuncionales){
            if (filtroSaldo.seCumple(uf.getSaldo())){
                retList.add(uf.toString());
            }
        }
        return retList;
    }
}
