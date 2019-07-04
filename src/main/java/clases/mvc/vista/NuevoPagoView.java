package clases.mvc.vista;

import clases.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class NuevoPagoView {
    private EventBus bus;
    private JFrame frame;
    private JComboBox comboConsorcios;
    private JComboBox comboUnidadesFuncionales;
    private JTextField textMonto;
    private JButton guardarPagoButton;
    private JPanel panel1;

    public NuevoPagoView(List<String> nombresConsorcios, List<Integer> listaUnidadesFuncionales) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Agregar Pago");
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocation(450,250);
        frame.setVisible(true);
        for (String nombre: nombresConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        for (Integer id: listaUnidadesFuncionales) {
            comboUnidadesFuncionales.addItem(id);
        }

        comboConsorcios.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            bus.post(new SolicitudListaUf((String)comboConsorcios.getSelectedItem()));
            }
        });

        guardarPagoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            bus.post(new GenerarPago((String)comboConsorcios.getSelectedItem(),(Integer)comboUnidadesFuncionales.getSelectedItem(),Double.valueOf(textMonto.getText())));
            frame.dispose();
            }
        });
    }

    public void poblarUnidadesFuncionales(List<Integer> listaUnidadesFuncionalesConsorcio) {
        comboUnidadesFuncionales.removeAllItems();
        for (Integer idGasto: listaUnidadesFuncionalesConsorcio) {
            comboUnidadesFuncionales.addItem(idGasto);
        }
        comboUnidadesFuncionales.updateUI();
    }

    public static class SolicitudListaUf{
        public String nombreConsorcio;

        public SolicitudListaUf(String nombre){
            nombreConsorcio = nombre;
        }
    }

    public static class GenerarPago{
        public String nombreConsorcio;
        public Integer idUnidadFuncional;
        public Double monto;

        public GenerarPago(String nombreConsorcio, Integer idUnidadFuncional, Double monto) {
            this.nombreConsorcio = nombreConsorcio;
            this.idUnidadFuncional = idUnidadFuncional;
            this.monto = monto;
        }
    }
}
