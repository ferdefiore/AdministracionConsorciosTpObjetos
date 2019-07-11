package clases.mvc.vista;

import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class AgregarGastoView {
    private EventBus bus;
    private JFrame frame;
    private JPanel panel;
    private JComboBox comboConsorcios;
    private JComboBox comboGastos;
    private JTextField textMonto;
    private JTextField textConcepto;
    private JButton guardarGastoButton;
    private JCheckBox checkCompuesto;
    private JList listaDetalleGasto;

    public AgregarGastoView(List<String> listaNombresConsorcios, List<Integer> listaIdGastos) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloAgregarGastoView);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this.panel);
        for (String nombre : listaNombresConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        for (Integer idGasto : listaIdGastos) {
            comboGastos.addItem(idGasto);
        }
        frame.setVisible(true);

        guardarGastoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreConsorcio = (String) comboConsorcios.getSelectedItem();
                String idGasto = comboGastos.getSelectedItem().toString();
                Integer idGastoSeleccionado = Constantes.ceroInteger;
                if (!(idGasto.equals(Constantes.stringNuevoGasto))) {
                    idGastoSeleccionado = Integer.valueOf(comboGastos.getSelectedItem().toString());
                }
                String concepto = textConcepto.getText();
                Float monto = Constantes.ceroFloat;
                if (!(textMonto.getText().equals(Constantes.stringVacio))) {
                    monto = Float.valueOf(textMonto.getText());
                }
                if (comboGastos.getSelectedItem().equals(Constantes.stringNuevoGasto) && !checkCompuesto.isSelected()) {
                    bus.post(new AgregarNuevoGasto(nombreConsorcio, concepto, monto));
                } else if (comboGastos.getSelectedItem().equals(Constantes.stringNuevoGasto) && checkCompuesto.isSelected()) {
                    bus.post(new AgregarNuevoGasto(nombreConsorcio, concepto));
                } else if (!(comboGastos.getSelectedItem().equals(Constantes.stringNuevoGasto)) && !checkCompuesto.isSelected()) {
                    bus.post(new AgregarAGasto(nombreConsorcio, idGastoSeleccionado, concepto, monto));
                } else {
                    bus.post(new AgregarAGasto(nombreConsorcio, idGastoSeleccionado, concepto));
                }
                bus.post(Constantes.terminarAgregarGasto);
                frame.dispose();
            }
        });
        checkCompuesto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkCompuesto.isSelected()) {
                    textMonto.setText(Constantes.stringVacio);
                    textMonto.setEnabled(false);
                } else {
                    textMonto.setEnabled(true);
                }

            }
        });
        comboConsorcios.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bus.post(new SolicitudListaGastos((String) comboConsorcios.getSelectedItem()));
            }
        });
    }

    public void poblarGastos(List<Integer> gastos) {
        comboGastos.removeAllItems();
        comboGastos.addItem(Constantes.stringNuevoGasto);
        for (Integer idGasto : gastos) {
            comboGastos.addItem(idGasto);
        }
    }

    public static class SolicitudListaGastos {
        public String nombreConsorcio;
        public SolicitudListaGastos(String nombre) {
            nombreConsorcio = nombre;
        }
    }

    public static class AgregarNuevoGasto {
        public String nombreConsorcio;
        public String concepto;
        public Float monto;

        public AgregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
            this.nombreConsorcio = nombreConsorcio;
            this.concepto = concepto;
            this.monto = monto;
        }

        public AgregarNuevoGasto(String nombreConsorcio, String concepto) {
            this.nombreConsorcio = nombreConsorcio;
            this.concepto = concepto;
            this.monto = Constantes.discernibleGasto;
        }
    }

    public static class AgregarAGasto {
        public String nombreConsorcio;
        public String concepto;
        public Float monto;
        public Integer idGastoSeleccionado;

        public AgregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
            this.nombreConsorcio = nombreConsorcio;
            this.idGastoSeleccionado = idGastoSeleccionado;
            this.concepto = concepto;
            this.monto = monto;
        }

        public AgregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto) {
            this.nombreConsorcio = nombreConsorcio;
            this.idGastoSeleccionado = idGastoSeleccionado;
            this.concepto = concepto;
            this.monto = Constantes.discernibleGasto;
        }
    }
}
