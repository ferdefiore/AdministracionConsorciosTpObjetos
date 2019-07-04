package clases.mvc.vista;

import clases.EventBusFactory;
import clases.Gasto;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class LiquidacionVigenteView {
    private JList list1;
    private JPanel panel1;
    private JComboBox comboConsorcios;
    private JButton buscarButton;
    EventBus bus;
    private JFrame frame;

    public LiquidacionVigenteView(List<String> consorcios) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Cierre de liquidacion");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocation(450,250);
        frame.setVisible(true);
        for (String nombre: consorcios) {
            comboConsorcios.addItem(nombre);
        }

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            bus.post(new SolicitudLiquidacion((String)comboConsorcios.getSelectedItem()));
            }
        });
    }

    public void poblarLista(List<Gasto> gastos) {
        list1.setListData(new String[0]);
        String[] datos = new String[gastos.size()];
        for (int i = 0; i < gastos.size(); i++) {
            datos[i] = gastos.get(i).toString();
        }
        list1.setListData(datos);
    }

    public class SolicitudLiquidacion{
        public String nombreConsorcio;

        public SolicitudLiquidacion(String nombreConsorcio) {
            this.nombreConsorcio = nombreConsorcio;
        }
    }
}
