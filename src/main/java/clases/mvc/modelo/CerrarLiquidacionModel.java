package clases.mvc.modelo;

import clases.*;
import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class CerrarLiquidacionModel {

    DAOmanager daoManager = DAOmanager.getDAOmanager();

    public List<String> getNombresDeConsorcios() {
        return daoManager.getListaNombresConsorcios();
    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        daoManager.cerrarLiquidacion(nombreConsorcio);
    }

    public void cerrarLiquidacionGenerarInforme(String nombreConsorcio) throws IOException {
        Liquidacion liquidacionVieja = daoManager.cerrarLiquidacionGenerarInforme(nombreConsorcio);
        Printer.printLiquidacionCierre(liquidacionVieja);
        List<UnidadFuncional> ufs = daoManager.getListaUnidadesFuncionalesConsorcio(nombreConsorcio);
        Printer.printSaldosCierre(nombreConsorcio,liquidacionVieja.getPeriodo().toString(),ufs);
    }
}

