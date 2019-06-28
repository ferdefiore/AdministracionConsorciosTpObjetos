package clases.mvc.modelo;

import clases.DbManager;
import clases.EventBusFactory;
import com.google.common.eventbus.EventBus;

import java.util.List;

public class AgregarGastoModel {
    private EventBus bus;
    DbManager dbManager = DbManager.getDbManager();

    public AgregarGastoModel() {
    bus = EventBusFactory.getEventBus();
    bus.register(this);
    }

    public List<String> getNombresConsorcios() {
        return dbManager.getListaNombresConsorcios();
    }

    public List<Integer> getIdGastos(String nombreConsorcio) {
        return dbManager.getListaGastos(nombreConsorcio);
    }

    public List<Integer> getListaGastosParaConsorcio(String nombreConsorcio) {
        return dbManager.getListaGastos(nombreConsorcio);
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        if (monto == -1f){
            dbManager.agregarNuevoGasto(nombreConsorcio,concepto);
        }else{
            dbManager.agregarNuevoGasto(nombreConsorcio,concepto,monto);
        }
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        if (monto == -1){
            dbManager.agregarAGasto(nombreConsorcio,idGastoSeleccionado,concepto);
        } else{
            dbManager.agregarAGasto(nombreConsorcio,idGastoSeleccionado,concepto,monto);
        }
    }
}
