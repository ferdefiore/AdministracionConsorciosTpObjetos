package clases.mvc.modelo;

import clases.*;
import com.google.common.eventbus.EventBus;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AgregarDatosModel {
    private EventBus bus;
    private DbManager dbManager = DbManager.getDbManager();

    public AgregarDatosModel() {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
    }

    public void crearNuevoConsorcio(String nombre, String cuit, String direccion, String ciudad) {
        YearMonth ym = YearMonth.of(YearMonth.now().getYear(),YearMonth.now().getMonthValue());
        Liquidacion nuevaLiquidacion = new Liquidacion(ym,new ArrayList<Gasto>(),new Consorcio());
        Consorcio nuevoConsorcio = new Consorcio(nombre,cuit,direccion,ciudad,nuevaLiquidacion,new ArrayList<UnidadFuncional>());
        nuevaLiquidacion.setConsorcio(nuevoConsorcio);
        dbManager.guardarConsorcio(nuevoConsorcio);
    }

    public List<String> getListaConsorcios() {
        return dbManager.getListaNombresConsorcios();
    }

    public List<String> getListaPropietarios() {
        return dbManager.getListaDniNombrePropietario();
    }

    public void crearUnidadFuncional(String nombreConsorcioPerteneciente, String tipoUf, String dniPropietario, double coeficientePago, String pisoNum, double saldo) {
        Propietario propietario = dbManager.getPropietarioFromDni(dniPropietario);
        UnidadFuncional ufNueva = new UnidadFuncional(tipoUf,propietario,coeficientePago,pisoNum,saldo);
        dbManager.agregarUnidadFuncional(nombreConsorcioPerteneciente,ufNueva);

    }

    public void crearPropietarioNuevo(String dni, String nomYApe, String mail, String direccion, String telefono) {
        Propietario nuevoPropietario = new Propietario(dni,nomYApe,mail,direccion,telefono);
        dbManager.agregarPropietario(nuevoPropietario);
    }
}
