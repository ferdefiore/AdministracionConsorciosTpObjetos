package clases.mvc.vista;

import clases.EventBusFactory;
import clases.Gasto;
import clases.UnidadFuncional;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LiquidacionVigenteView {
    private JList list1;
    private JPanel panel1;
    private JComboBox comboConsorcios;
    private JButton buscarButton;
    private JButton verSaldosButton;
    EventBus bus;
    private JFrame frame;

    public LiquidacionVigenteView(List<String> consorcios) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Liquidacion Vigente Gastos y Saldos parciales");
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this.panel1);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for (String nombre: consorcios) {
            comboConsorcios.addItem(nombre);
        }

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            bus.post(new SolicitudGastosLiquidacion((String)comboConsorcios.getSelectedItem()));
            }
        });
        verSaldosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bus.post(new SolicitudSaldosLiquidacion((String)comboConsorcios.getSelectedItem()));
            }
        });
    }

    public void poblarListaGasto(List<Gasto> gastos) {
        list1.setListData(new String[0]);
        String[] datos = new String[gastos.size()];
        for (int i = 0; i < gastos.size(); i++) {
            datos[i] = gastos.get(i).toString();
        }
        list1.setListData(datos);
    }

    public void poblarListaUf(List<UnidadFuncional> ufs) {
        list1.setListData(new String[0]);
        String[] datos = new String[ufs.size()+1];
        datos[0] = "Los saldos se actualizaran una ves que se cierre la liquidacion: ";
        for (int i = 1; i < ufs.size(); i++) {
            datos[i] = ufs.get(i).toString();
        }
        list1.setListData(datos);
    }

    public class SolicitudGastosLiquidacion {
        public String nombreConsorcio;
        public SolicitudGastosLiquidacion(String nombreConsorcio) {
            this.nombreConsorcio = nombreConsorcio;
        }
    }

    public class SolicitudSaldosLiquidacion{
        public String nombreConsorcio;

        public SolicitudSaldosLiquidacion(String nombreConsorcio) {
            this.nombreConsorcio = nombreConsorcio;
        }
    }
}
