package clases.mvc.controlador;

import clases.clasesRelacionales.Liquidacion;
import clases.mvc.modelo.LiquidacionesHistoricasModel;
import clases.mvc.vista.LiquidacionesHistoricasView;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import clases.utils.Printer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LiquidacionesHistoricasController {
    private EventBus bus;
    private LiquidacionesHistoricasModel model;
    private LiquidacionesHistoricasView view;
    private List<Liquidacion> liquidaciones;

    public LiquidacionesHistoricasController() {
        try {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        model = new LiquidacionesHistoricasModel();
        List<String> nombresConsorcios = model.getListaConsorcios();
        List<String> periodos = new ArrayList<>();
        liquidaciones = model.getListaHistoricas(nombresConsorcios.get(Constantes.ceroInteger));
        for (Liquidacion lq : liquidaciones) {
            periodos.add(lq.getPeriodo().toString());
        }
        view = new LiquidacionesHistoricasView(nombresConsorcios, periodos);
        }catch (Exception exeption){
            EventBusFactory.unregisterAndGc(this,view,model);
            JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Subscribe
    public void onSolicitudPeriodos(LiquidacionesHistoricasView.SolicitudPeriodos event) {
        liquidaciones = model.getListaHistoricas(event.nombreConsorcio);
        List<String> periodos = new ArrayList<>();
        for (Liquidacion lq : liquidaciones) {
            periodos.add(lq.getPeriodo().toString());
        }
        view.poblarLiquidacionesHistoricas(periodos);
    }

    @Subscribe
    public void onImprimirLiquidacion(LiquidacionesHistoricasView.ImprimirLiquidacion event) throws IOException {
        for (Liquidacion lq : liquidaciones) {
            if (lq.getPeriodo().toString().equals(event.periodo)) {
                Printer.printLiquidacionCierre(model.imprimirLiquidacion(lq.getId_liquidacion()), " Informe Historico Gastos ");
                return;
            }
        }
    }

    @Subscribe
    public void onCerrarVentanasLiquidacionesHistoricas(String event) {
        if (event.equals(Constantes.terminarLiquidacionesHistoricas)) {
            EventBusFactory.unregisterAndGc(this, view, model);
        }
    }

}
