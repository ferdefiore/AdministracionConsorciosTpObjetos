package clases.mvc.modelo;

import clases.clasesRelacionales.Gasto;
import clases.clasesRelacionales.GastoCompuesto;
import clases.utils.Constantes;
import clases.utils.DAOmanager;

import java.util.ArrayList;
import java.util.List;

public class AgregarGastoModel {

    private DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getNombresConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public List<String> getListaGastosParaConsorcio(String nombreConsorcio) {
        List<String> retorno = new ArrayList<>();
        List<GastoCompuesto> compuestos = new ArrayList<>();
        try {

            List<Gasto> gastos = daoManager.getConsorcio(nombreConsorcio).getLiquidacionVigente().getGastos();

            for (Gasto g:gastos){
                compuestos.addAll(g.devolverCompuestos());
            }
            for (GastoCompuesto gc : compuestos) {
                retorno.add(gc.getId() + " " + gc.getConcepto());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }


    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        if (monto == Constantes.discernibleGasto) {
            daoManager.agregarNuevoGasto(nombreConsorcio, concepto);
        } else {
            daoManager.agregarNuevoGasto(nombreConsorcio, concepto, monto);
        }
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        if (monto == Constantes.discernibleGasto) {
            daoManager.agregarAGasto(nombreConsorcio, idGastoSeleccionado, concepto);
        } else {
            daoManager.agregarAGasto(nombreConsorcio, idGastoSeleccionado, concepto, monto);
        }
    }
}
