package clases.mvc.modelo;

import clases.DbManager;
import clases.EventBusFactory;
import com.google.common.eventbus.EventBus;

import java.util.List;

public class NuevoPagoModel {
    private EventBus bus;
    DbManager dbManager = DbManager.getDbManager();

    public NuevoPagoModel() {
        this.bus = EventBusFactory.getEventBus();
        bus.register(this);
        this.dbManager = dbManager;
    }

    public List<String> getListaConsorcios() {
        return dbManager.getListaNombresConsorcios();
    }

    public List<Integer> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        return dbManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
    }

    public void generarPago(String nombreConsorcio, Integer idUnidadFuncional, Double monto) {
        dbManager.generarPago(nombreConsorcio,idUnidadFuncional,monto);
    }
}
