package clases.mvc.modelo;

import clases.DbManager;
import clases.EventBusFactory;
import clases.UnidadFuncional;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
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
        List<Integer> idsUf = new ArrayList<>();
        List<UnidadFuncional> ufs = dbManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
        for (UnidadFuncional uf: ufs) {
            idsUf.add(uf.getId());
        }
        return idsUf;
    }

    public void generarPago(String nombreConsorcio, Integer idUnidadFuncional, Double monto) {
        dbManager.generarPago(nombreConsorcio,idUnidadFuncional,monto);
    }
}
