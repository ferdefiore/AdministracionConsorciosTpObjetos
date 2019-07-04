package clases.mvc.modelo;

import clases.DbManager;
import clases.EventBusFactory;
import clases.Gasto;
import com.google.common.eventbus.EventBus;

import java.util.List;

public class LiquidacionVigenteModel {
    private EventBus bus;
    DbManager dbManager = DbManager.getDbManager();

    public LiquidacionVigenteModel() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
    }

    public List<String> getNombresDeConsorcios() {
        return dbManager.getListaNombresConsorcios();
    }

    public List<Gasto> getDatosLiquidacionVigente(String nombreConsorcio) {
        return dbManager.getGastosLiquidacionVigente(nombreConsorcio);
    }
}
