package clases.mvc.modelo;

import clases.Constantes;
import clases.DAOmanager;

import java.util.List;

public class AgregarGastoModel {

    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getNombresConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<Integer> getIdGastos(String nombreConsorcio) {
        return daoManager.getListaGastos(nombreConsorcio);
    }

    public List<Integer> getListaGastosParaConsorcio(String nombreConsorcio) {
        return daoManager.getListaGastos(nombreConsorcio);
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        if (monto == Constantes.discernibleGasto){
            daoManager.agregarNuevoGasto(nombreConsorcio,concepto);
        }else{
            daoManager.agregarNuevoGasto(nombreConsorcio,concepto,monto);
        }
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        if (monto == Constantes.discernibleGasto){
            daoManager.agregarAGasto(nombreConsorcio,idGastoSeleccionado,concepto);
        } else{
            daoManager.agregarAGasto(nombreConsorcio,idGastoSeleccionado,concepto,monto);
        }
    }
}
