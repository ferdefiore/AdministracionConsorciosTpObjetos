package clases.mvc.modelo;

import clases.clasesRelacionales.Consorcio;
import clases.clasesRelacionales.Liquidacion;
import clases.clasesRelacionales.Propietario;
import clases.clasesRelacionales.UnidadFuncional;
import clases.utils.DAOmanager;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AgregarDatosModel {
    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public void crearNuevoConsorcio(String nombre, String cuit, String direccion, String ciudad) {
        YearMonth ym = YearMonth.of(YearMonth.now().getYear(), YearMonth.now().getMonthValue());
        Liquidacion nuevaLiquidacion = new Liquidacion(ym, new ArrayList<>(), new Consorcio());
        Consorcio nuevoConsorcio = new Consorcio(nombre, cuit, direccion, ciudad, nuevaLiquidacion, new ArrayList<>());
        nuevaLiquidacion.setConsorcio(nuevoConsorcio);
        daoManager.guardarConsorcio(nuevoConsorcio);
    }

    public List<String> getListaConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<String> getListaPropietarios() {
        return daoManager.getListaDniNombrePropietario();
    }

    public void crearUnidadFuncional(String nombreConsorcioPerteneciente, String tipoUf, String dniPropietario, double coeficientePago, String pisoNum, double saldo) {
        Propietario propietario = daoManager.getPropietarioFromDni(dniPropietario);
        UnidadFuncional ufNueva = new UnidadFuncional(tipoUf, propietario, coeficientePago, pisoNum, saldo);
        daoManager.agregarUnidadFuncional(nombreConsorcioPerteneciente, ufNueva);

    }

    public void crearPropietarioNuevo(String dni, String nomYApe, String mail, String direccion, String telefono) {
        Propietario nuevoPropietario = new Propietario(dni, nomYApe, mail, direccion, telefono);
        daoManager.agregarPropietario(nuevoPropietario);
    }
}
