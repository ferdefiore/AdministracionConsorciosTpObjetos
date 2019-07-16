package clases.mvc.modelo;

import clases.clasesRelacionales.Consorcio;
import clases.clasesRelacionales.Gasto;
import clases.clasesRelacionales.GastoCompuesto;
import clases.clasesRelacionales.GastoSimple;
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
            List<Gasto> gastos = daoManager.getGastosLiquidacionVigente(nombreConsorcio);
            //List<Gasto> gastos = daoManager.getConsorcio(nombreConsorcio).getLiquidacionVigente().getGastos();

            for (Gasto g : gastos) {
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
        Gasto nuevoGasto;
        Consorcio consorcio = daoManager.getConsorcio(nombreConsorcio);
        if (monto == Constantes.discernibleGasto) {
            nuevoGasto = new GastoCompuesto(concepto, new ArrayList<>());
        } else {
            nuevoGasto = new GastoSimple(concepto, monto);
        }
        consorcio.getLiquidacionVigente().agregarGasto(nuevoGasto);
        daoManager.actualizarConsorcio(consorcio);
    }

    public void agregarAGasto(Integer idGastoSeleccionado, String concepto, Float monto) {
        Gasto nuevoGasto;
        if (monto == Constantes.discernibleGasto) {
            nuevoGasto = new GastoCompuesto(concepto, new ArrayList<>());
        } else {
            nuevoGasto = new GastoSimple(concepto, monto);
        }
        GastoCompuesto compuesto = daoManager.getGastoCompuesto(idGastoSeleccionado);
        compuesto.agregarGastoACompuesto(nuevoGasto);
        daoManager.actualizarGasto(compuesto);
    }
}
